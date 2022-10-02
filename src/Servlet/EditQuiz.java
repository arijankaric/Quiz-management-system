package Servlet;

import java.io.File;


import dao.AbstractDao;

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
import dao.QuizDao;
import dao.UserDao;
import service.QuizService;
import service.UserService;

/**
 * Servlet implementation class EditQuiz
 */
@WebServlet(name = "/EditQuiz", urlPatterns = "/admin/editQuiz")
@MultipartConfig
public class EditQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
	QuizService quizService = new QuizService(new QuizDao());
    UserService userService = new UserService(new UserDao());
	
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
     * @see HttpServlet#HttpServlet()
     */
    public EditQuiz() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("editQuiz doGet start");
		System.out.println(request.getAttribute("id")); // post
		System.out.println(request.getParameter("id")); // get


		request.getRequestDispatcher("editQuiz.jsp").include(request, response);
		System.out.println("editQuiz doGet end");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Edit start");

        String title = request.getParameter("title");
        System.out.println("Title: " + title);
        String description = request.getParameter("description");
        System.out.println("Description: " + description);
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("id: " + id);

        Part filePart = request.getPart("imageFile"); // Retrieves <input type="file" name="file">

        String imageUrl;
        if (filePart.getSubmittedFileName() != null) {
            System.out.println("filePart size: " + filePart.getSize());
            imageUrl = saveFile(filePart);
            quizService.updateQuiz(title, description, imageUrl, id);
        } else {
            Quiz quiz = quizService.getQuizById(id);
            System.out.println("Old imageUrl: " + quiz.getImageUrl());
            quizService.updateQuiz(title, description, quiz.getImageUrl(), id);
        }

        String resp = "1";

        response.setContentType("text/plain");
        response.getWriter().write(resp);
        System.out.println("Edit end");
	}

}
