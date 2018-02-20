package troyhigh.library.dbms.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConn {
	private static final String myUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3222210";
	protected static Connection conn;
	private static String username = "", password = "";
	
	public static boolean establishConnection(){
		try {
			conn = DriverManager.getConnection(myUrl, username, password);
			System.out.println("Successful Connection!");
			return true;
		} catch (SQLException e) {
			System.err.println("Failed to connect to database. Check your username and password");
			e.printStackTrace();
			return false;
		}
	}
	
	public static void setUsername(String name){ username = name; }
	public static void setPassword(String pass){ password = pass; }
	
	public static void close() throws SQLException{conn.close();}
}
