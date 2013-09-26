package cont;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

	private static final String dataBaseURL = "jdbc:postgresql://127.0.0.1:5432/postgres";
	private static final String dataBaseLogin = "postgres";
	private static final String dataBasePassword = "postgres";
	private static final String driverURL = "org.postgresql.Driver";

	public static boolean checkLogin(String login, String password) {
		ResultSet rs = executeQuery(selectQuery(login, password));
		try {
			if (!rs.next()) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean loginUsed(String login) {
		ResultSet rs = executeQuery(loginUsedQuery(login));
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean register(String login, String password) {
		if(loginUsed(login)) return false;
		executeQuery(insertQuery(login, password));
		return true;
	}

	public static ResultSet listProducts() {
		return executeQuery(productsQuery());
	}


	public static ResultSet executeQuery(String query) {
		Connection dataBase = null;
		try {
			Class.forName(driverURL).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			dataBase = DriverManager.getConnection(dataBaseURL,
					dataBaseLogin, dataBasePassword);
			Statement stat = dataBase.createStatement();
			return stat.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String productsQuery() {
		return "SELECT * FROM products;";
	}

	private static String insertQuery(String login, String password) {
		return "INSERT INTO users(login,password) VALUES ('"
				+ login
				+ "','"
				+ password
				+ "');";
	}

	private static String selectQuery(String login, String password) {
		return "SELECT * FROM users WHERE login ='" + login
				+ "' AND password = '" + password + "';";
	}

	public static String productsStockQuery(String name) {
		return "SELECT * FROM products WHERE name ='" + name
				+ "' AND stock > 0;";
	}

	private static String loginUsedQuery(String login) {
		return "SELECT * FROM users WHERE login ='" + login + "';";
	}
}
