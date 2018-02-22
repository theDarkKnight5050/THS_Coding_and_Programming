package troyhigh.library.dbms.database;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import troyhigh.library.dbms.model.Book;

public class BookSQLConn extends SQLConn{
	
	public static Set<Book> getAllBooks(String query){
		Set<Book> books = new HashSet<>();
		
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				String name = rs.getString("Name");
				String author = rs.getString("Author");
				int ID = rs.getInt("ID");
				int owner = rs.getInt("Owner");
				books.add(new Book(name, author, ID, owner));
			}
		}
		catch(Exception e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("MySQL Error");
			alert.setHeaderText("Update login info in settings tab");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		
		return books;
	}
	
	public static boolean addBook(Book book){
		String query = "insert into Books (Name, Author, ID)" + " values (?, ?, ?)";
		
		try{
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, book.getName());
			preparedStmt.setString(2, book.getAuthor());
			preparedStmt.setInt(3, book.getID());
			preparedStmt.execute();
			
			return true;
		}
		catch(Exception e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("MySQL Error");
			alert.setHeaderText("Update login info in settings tab");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			
			return false;
		}
	}
	
	public static boolean updateBook(Book book, int ID){
		String query = "update Books set Name = ?, Author = ?, checkOut = ?, owner = ? where ID = ?";
		
		try{
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, book.getName());
			preparedStmt.setString(2, book.getAuthor());
			preparedStmt.setDate(3, book.getDate());
			preparedStmt.setObject(4, book.getOwner());
			preparedStmt.setObject(5, ID);
			preparedStmt.executeUpdate();
			
			return true;
		}
		catch(Exception e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("MySQL Error");
			alert.setHeaderText("Update login info in settings tab");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			
			return false;
		}
	}
	
	public static boolean removeBook(int ID){
		try{
			String query = "delete from Books where ID = " + ID;
			Statement st = conn.createStatement();
			st.executeUpdate(query);
			
			return true;
		}
		catch(Exception e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("MySQL Error");
			alert.setHeaderText("Update login info in settings tab");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			
			return false;
		}
	}
	
}
