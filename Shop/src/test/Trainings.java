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
 * Servlet implementation class Trainings
 */
@WebServlet("/Trainings")
public class Trainings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Trainings() {
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
		HttpSession session = request.getSession();
		String login = session.getAttribute("login").toString();
		
		out.println("<html><head><link rel='Stylesheet' type='text/css' href='style.css' /></head><body class = 'back'>");
		out.println("<h2>TRENINGI</h2>");
		try {
			generateTable(out,login);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.println("<form method='post' action='http://localhost:8080/Shop/addTraining.html'><input class = 'button' type='submit' value='dodaj trening'></form></body></html>");
	}
	
	private void generateTable(PrintWriter out,String login) throws SQLException{
		out.println("<div class='box'><table>");
			out.println("<tr>");
				out.println("<th>Date</th>");
				out.println("<th>Length</th>");
				out.println("<th>Time</th>");
			out.println("</tr>");
		ResultSet set = listTrainings(login);
		while(set.next()){
			out.println("<tr>");
				generateProduct(out,set.getDate("day").toString(),set.getString("time"),set.getInt("length"));
			out.println("</tr>");
		}
		out.println("</table></div>");
	}
	private void generateProduct(PrintWriter out,String day,String time,int length){
		out.println("<td>"+ day + "</td>");
		out.println("<td>"+ length + " km" + "</td>");
		out.println("<td>"+ time + "</td>");
	}
	private static ResultSet listTrainings(String login){
		return DatabaseManager.listTrainings(login);
	}
}
