package test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Events
 */
@WebServlet("/Events")
public class Events extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Events() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		generatePage(response,request);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		generatePage(response,request);
	}

	private void generatePage(HttpServletResponse response,HttpServletRequest request) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<html><head><link rel='Stylesheet' type='text/css' href='style.css' /><title>Bookshop</title></head><body class = 'back'><h2>Bookshop</h2>");
		out.println("<p>ZAWODY</p>");
		out.println("<form method='post' action='http://localhost:8080/Shop/addEvent.html'><input type='submit' value='dodaj trening'></form></body></html>");
	}

}