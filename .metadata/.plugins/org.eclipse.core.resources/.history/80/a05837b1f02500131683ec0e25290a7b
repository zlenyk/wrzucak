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

import test.Login.DatabaseManager;

/**
 * Servlet implementation class Friends
 */
@WebServlet("/Friends")
public class Friends extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Friends() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			generatePage(response,request);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			generatePage(response,request);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void generatePage(HttpServletResponse response,HttpServletRequest request) throws IOException, SQLException {
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		if(name == null){
			out.println("<html><head><title>Znajomi</title></head><body><h2>Znajomi</h2>");
			out.println("</body></html>");
			return;
		}
		ResultSet log = getLogin(name,surname);
		out.println("<html><head><link rel='Stylesheet' type='text/css' href='style.css' /><title>Bookshop</title></head><body><h2>Bookshop</h2>");
		out.println("<h2>TRENINGI - " + name + " " + surname + "</h2>");
		if(log.next()){
			try {
				generateTable(out,log.getString("login"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else{
			out.println("Podana osoba nie istnieje");
		}
		out.println("</body></html>");
	}
	private ResultSet getLogin(String name, String surname){
		return DatabaseManager.getLogin(name, surname);
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
