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
 * Servlet implementation class List
 */
@WebServlet("/List")
public class List extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String loginURL = "http://localhost:8080/Shop/Login";
	private static final String transactionURL = "http://localhost:8080/Shop/Transaction";  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public List() {
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
	private void generatePage(HttpServletResponse response,HttpServletRequest request) throws IOException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String login = session.getAttribute("login").toString();
		String password = session.getAttribute("password").toString();
		
		Login.checkLogin(response, session, login, password);
		
		out.println("<html><head><title>Bookshop</title></head><body><h2>Bookshop</h2>");
		out.println("<p>Witaj " + login + "</p>");
		
		out.println("<p><form method='post' action='"+ loginURL +"'>");
			out.println("<input type='submit' value='Wyloguj' />");
			out.println("<input type='hidden' name = 'action' value= 'logout'/>");
		out.println("</form></p>");
		
		out.println("<form method='post' action='" + transactionURL + "'>");
		try {
			generateTable(out);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		out.println("<p><input type='submit' name='zamow' value="
		         + "'zamow' />");
		out.println("</form>");
	}
	
	private void generateTable(PrintWriter out) throws SQLException{
		out.println("<table border = '1'>");
			out.println("<tr>");
				out.println("<th></th>");
				out.println("<th>Product</th>");
				out.println("<th>Price</th>");
			out.println("</tr>");
		ResultSet set = listProducts();
		while(set.next()){
			out.println("<tr>");
				generateProduct(out,set.getString("name"),set.getInt("price"));
			out.println("</tr>");
		}
		out.println("</table>");
	}
	
	private void generateProduct(PrintWriter out,String name,int price){
		out.println("<td>");
		out.println("<form method='post' action='" + transactionURL + "'>");
		out.println("<input type='submit' value= 'zamow'/>"); 
		out.println("<input type='hidden' name = 'name' value= '"+ name +"'/>"); 
		out.println("<input type='hidden' name = 'price' value= '"+ price +"'/>"); 
		out.println("</form></td>");
		out.println("<td>"+ name + "</td>");
		out.println("<td>"+ price + " $" + "</td>");
	}
	
	public static ResultSet listProducts(){
		return DatabaseManager.listProducts();
	}
}
