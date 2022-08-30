package Servlet;

import dao.ResultDao;
import dao.UserDao;
import Model.Result;
import Model.User;
import service.ResultService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "/ExportCSVServlet", urlPatterns = "/admin/export-results")
public class ExportCSVServlet extends HttpServlet {
    ResultService resultService = new ResultService(new ResultDao());
    UserService userService = new UserService(new UserDao());

    public ExportCSVServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userIdString = request.getParameter("userId");
        User user = userService.findByUserId(Integer.parseInt(userIdString));
        String csvFilePath = "ResultsOf" + user.getUsername() + ".csv";

        try {
            List<Result> results = resultService.getResultsOfAllQuizzesOfUser(Integer.parseInt(userIdString));
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
            // write header line containing column names
            fileWriter.write("quiz_id,quiz_title,player_name,player_surname,player_email,score");

            for (Result result : results) {
                int quizId = result.getQuiz().getId();
                String quizTitle = result.getQuiz().getTitle();
                String userName = result.getUserName();
                String userSurname = result.getUserSurname();
                String userEmail = result.getUserEmail();
                int score = result.getScore();

                String line = String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\",%d",
                        quizId, quizTitle, userName, userSurname, userEmail, score);

                fileWriter.newLine();
                fileWriter.write(line);
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }

        resultService.openCSV(csvFilePath);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
