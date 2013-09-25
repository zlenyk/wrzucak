package test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import test.Login.DatabaseManager;

/**
 * Servlet implementation class TakePart
 */
@WebServlet("/TakePart")
public class TakePart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TakePart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action(request,response);
	}
	
	void action(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession();
		String login = session.getAttribute("login").toString();
		String event = request.getParameter("name");
		String action = request.getParameter("action");
		if(action.equals("Take")){System.out.println("TAKE");
			DatabaseManager.takePart(login, event);
			response.sendRedirect("http://localhost:8080/Shop/Events");
		}
		else{
			DatabaseManager.notTakePart(login, event);
			response.sendRedirect("http://localhost:8080/Shop/MyEvents");
		}
	}
}
