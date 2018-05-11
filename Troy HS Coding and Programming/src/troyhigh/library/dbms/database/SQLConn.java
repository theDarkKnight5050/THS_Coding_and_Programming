package troyhigh.library.dbms.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SQLConn {
	private static final String myUrl = "jdbc:mysql://localhost:3306/THS_Library_DBMS";
	protected static Connection conn;
	private static String username = "", password = "";
	
	public static boolean establishConnection(){
		try {
			conn = DriverManager.getConnection(myUrl, username, password);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Successful Login");
			alert.setHeaderText("Access granted to user " + username);
			alert.showAndWait();
			return true;
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("MySQL Error");
			alert.setHeaderText("Failed to login to database. Try changing username and password.");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			
			return false;
		}
	}
	
	public static void setUsername(String name){ username = name; }
	public static void setPassword(String pass){ password = pass; }
	
	public static void close() throws SQLException{conn.close();}
}
