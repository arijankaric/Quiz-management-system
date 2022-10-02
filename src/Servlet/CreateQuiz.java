package Servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import Model.Quiz;
import Model.User;
import dao.QuizDao;
import dao.UserDao;
import service.QuizService;
import service.UserService;

/**
 * Servlet implementation class CreateQuiz
 */
@WebServlet(name = "/CreateQuiz", urlPatterns = "/admin/createQuiz")
@MultipartConfig
public class CreateQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
	QuizService quizService = new QuizService(new QuizDao());
    UserService userService = new UserService(new UserDao());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuiz() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private String saveFile(Part filePart) throws IOException {

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        System.out.println("fileName: " + fileName);
        InputStream fileContent = filePart.getInputStream();

        String root = getServletContext().getRealPath("");
        String pathFromRoot = "imgs/" + fileName;
        String fullPath = root + pathFromRoot;
        System.out.println(fullPath);
        File targetFile = new File(fullPath);

        OutputStream outputStream = new FileOutputStream(targetFile);
        IOUtils.copy(fileContent, outputStream);
        outputStream.close();

//        pathFromRoot = "../" + pathFromRoot;
        System.out.println("imageUrl to DB: " + pathFromRoot);

        return pathFromRoot;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Add start");

//      String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
      String title = request.getParameter("title");
      System.out.println("Title: " + title);
      String description = request.getParameter("description");
      System.out.println("Description: " + description);
      String username = request.getParameter("username");
      System.out.println("Username: " + username);

      Part filePart = request.getPart("imageFile");
      System.out.println("filePart size: " + filePart.getSize());
      String imageUrl = saveFile(filePart);
      
      User user = userService.findByUsername(username);
      System.out.println("numOfQuizzes: " + quizService.getNumberOfQuizzes());
      quizService.createQuiz(new Quiz(title, description, imageUrl, user));

      String resp = "1";

      response.setContentType("text/plain");
      response.getWriter().write(resp);
      System.out.println("Add end");
	}

}
