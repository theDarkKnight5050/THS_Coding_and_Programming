package troyhigh.library.dbms.database;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import troyhigh.library.dbms.model.Book;

public class BookSQLConn extends SQLConn{
	
	public static Set<Book> getAllBooks(String query){
		Set<Book> books = new HashSet<>();
		establishConnection();
		
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				String fName = rs.getString("First_Name");
				String lName = rs.getString("Last_Name");
				int ID = rs.getInt("ID");
				int owner = rs.getInt("Owner");
				books.add(new Book(fName, lName, ID, owner));
			}
		}
		catch(Exception e){
			System.err.println("Failed to recieve database data");
			e.printStackTrace();
		}
		
		return books;
	}
	
	public static boolean addBook(Book book){
		String query = "insert into Books (Title, Author, ID)" + " values (?. ?, ?, ?)";
		establishConnection();
		
		try{
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, book.getName());
			preparedStmt.setString(2, book.getAuthor());
			preparedStmt.setInt(3, book.getID());
			preparedStmt.execute();
			return true;
		}
		catch(Exception e){
			System.err.println("Failed to update database data");
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean updateBook(Book book, int ID){
		String query = "update Books set Name = ? Author = ? checkOut = ? owner = ? where ID = ?";
		establishConnection();
		
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
			System.err.println("Failed to update database data");
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean removeBook(int ID){
		establishConnection();

		try{
			String query = "delete from Books where ID = " + ID;
			Statement st = conn.createStatement();
			st.executeQuery(query);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
}
