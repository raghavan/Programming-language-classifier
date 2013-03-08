package dbhandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private static DBConnect instance = null;
	private static Connection conn;

	private DBConnect() {
		String dbDriver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/programclassify";
		String username = "root";
		String password = "root";
		try {
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException cnfErr) {
			cnfErr.printStackTrace();
		} catch (SQLException err) {
			err.printStackTrace();
		}
	}

	public static DBConnect getInstance() {
		if (instance == null)
			return new DBConnect();
		else
			return instance;
	}

	public static Connection getConnection() {
		return getInstance().conn;
	}
}
