package test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import test.Login.DatabaseManager;

/**
 * Servlet implementation class Transaction
 */
@WebServlet("/Transaction")
public class Transaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String loginURL = "http://localhost:8080/Shop/Login";
	private static final String shopURL = "http://localhost:8080/Shop/List";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transaction() {
        super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		String login = session.getAttribute("login").toString();
		String password = session.getAttribute("password").toString();
		
		Login.checkLogin(response,session,login,password);
		
		String name = request.getParameter("name");
		String price = request.getParameter("price");
		generatePage(out,login,password,name,price);
	}

	private void generatePage(PrintWriter out, String login, String password, String name, String price) {
		out.println("<html><head><title>Bookshop</title></head><body><h2>Bookshop</h2>");
		out.println("<p>Hej " + login + "</p>");

		out.println("<p><form method='post' action='"+ loginURL +"'>");
			out.println("<input type='submit' value='Wyloguj' />");
			out.println("<input type='hidden' name = 'action' value= 'logout'/>");
		out.println("</form></p>");
	
		if(!DatabaseManager.checkStock(name)){
			out.println("<p>Przepraszamy, produkt niedostepny</p>");
		}
		else{
			out.println("<p>Dzi�kujemy za zakup produktu: " + name + " za " + price + ". Zach�camy do dalszych zakup�w.</p>");
			DatabaseManager.decreaseStock(name);
		}
		
		out.println("<p><form method='post' action='"+ shopURL +"'>");
			out.println("<input type='submit' value='Sklep' />");
		out.println("</form></p>");
	}
}
