package troyhigh.library.dbms.controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import troyhigh.library.dbms.database.BookSQLConn;
import troyhigh.library.dbms.model.Book;

public class PrintController {
	@FXML
	private Stage dialogStage;
	
	public void setDialogStage(Stage stage){
		this.dialogStage = stage;
	}
	
	@FXML
	public void printFines(){
		Set<Book> books = BookSQLConn.getAllBooks("select * from Books");
		try {
			PrintWriter writer = new PrintWriter("Fines.txt", "UTF-8");
			
			for(Book book : books){
				if (book.getFine() > 0){
					NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
					
					writer.println("Title: " + book.getName() + " Author: " + book.getAuthor());
					writer.println("Owner: " + book.getOwner() + " FINE: " + currencyFormatter.format(0.1 * book.getFine()));
					writer.println("");
				} 
			}
			writer.close();
			
			Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succesful Print");
            alert.setHeaderText("All fines sent to file called \"Fines.txt\"");
            alert.showAndWait();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Print Failure");
            alert.setHeaderText("Printing lead to an unknown error. Follow stack trace for more info");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
		}
	}
	
	@FXML
	public void printCheckout(){
		Set<Book> books = BookSQLConn.getAllBooks("select * from Books");
		try {
			PrintWriter writer = new PrintWriter("CheckedOut.txt", "UTF-8");
			
			for(Book book : books){
				writer.println("Title: " + book.getName() + " Author: " + book.getAuthor());
				writer.println("Owner: " + book.getOwner() + " Days Left: " + (int)book.getFine());
				writer.println("");
			}
			writer.close();
			
			Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succesful Print");
            alert.setHeaderText("All checked out books sent to file called \"CheckedOut.txt\"");
            alert.showAndWait();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Print Failure");
            alert.setHeaderText("Printing lead to an unknown error. Follow stack trace for more info");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
		}
	}
}
