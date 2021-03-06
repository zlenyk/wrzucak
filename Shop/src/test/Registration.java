package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.Login.DatabaseManager;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String mainURL = "http://localhost:8080/Shop/";
	private static final String registrationURL = "http://localhost:8080/Shop/registration.html";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			generatePage(response,request);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void generatePage(HttpServletResponse response,HttpServletRequest request) throws IOException, InterruptedException {
		String password = request.getParameter("password");
		String login = request.getParameter("login");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String address = request.getParameter("address");
		
		if(!DatabaseManager.loginUsed(login)){
			DatabaseManager.register(login, password, name, surname, address);
			System.out.println("LOGIN - " + login +" PASSWORD - " + password);
			response.sendRedirect(mainURL);
		}
		else{
			System.out.println("nieudana");
			response.sendRedirect(registrationURL);
		}
	}

}
