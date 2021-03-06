package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import test.Login.DatabaseManager;

/**
 * Servlet implementation class MyEvents
 */
@WebServlet("/MyEvents")
public class MyEvents extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyEvents() {
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
		HttpSession session = request.getSession();
		String login = session.getAttribute("login").toString();
		PrintWriter out = response.getWriter();
		out.println("<html><head><link rel='Stylesheet' type='text/css' href='style.css' /><title>Moje NADCHODZACE ZAWODY</title></head><body class = 'back'><h2>Moje NADCHODZACE ZAWODY</h2>");
		try {
			generateTable(out,login);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.println("<form method='post' action='http://localhost:8080/Shop/addEvent.html'><input class = 'button' type='submit' value='dodaj zawody'></form></body></html>");
	}
	private void generateTable(PrintWriter out,String login) throws SQLException{
		out.println("<div class='box'><table>");
			out.println("<tr>");
				out.println("<th>Title</th>");
				out.println("<th>Place</th>");
				out.println("<th>Date</th>");
				out.println("<th>Length</th>");
				out.println("<th>People</th>");
				out.println("<th>Posted by</th>");
				out.println("<th></th>");
			out.println("</tr>");
			
		ResultSet set = DatabaseManager.listMyEvents(login);
		
		while(set.next()){
			out.println("<tr>");
				generateProduct(out,set.getString("description"),set.getString("place"),set.getDate("day").toString(),set.getInt("length"),set.getInt("count"),set.getString("login"));
			out.println("</tr>");
		}
		out.println("</table></div>");
	}
	private void generateProduct(PrintWriter out,String title,String place,String day,int length,int count,String login){
		out.println("<td>"+ title + "</td>");
		out.println("<td>"+ place + "</td>");
		out.println("<td>"+ day + "</td>");
		out.println("<td>"+ length + " km" + "</td>");
		out.println("<td>"+ count + "</td>");
		out.println("<td>"+ login + "</td>");
		out.println("<td><form action='http://localhost:8080/Shop/TakePart'>");
		out.println("<input type='hidden' name = 'action' value='notTake'>");
		out.println("<input type='hidden' name = 'name' value='"+title+"'>");
		out.println("<input class = 'button' type='submit' value='Not Take Part'>");
		out.println("</form></td>");
	}

}
