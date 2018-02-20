package troyhigh.library.dbms.controller;

import java.sql.Date;
import java.util.Set;
import java.text.SimpleDateFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import troyhigh.library.dbms.database.SQLConn;
import troyhigh.library.dbms.model.Book;

public class MyBooksEditController {
	private TableView<Book> bookTable;
	private TableColumn<Book, String> titleCol;
	private TableColumn<Book, String> authorCol;
	
    private Label name;
    private Label author;
    private Label overdue;
	
    @FXML
    private Stage dialogStage;
    
    private ObservableList<Book> books = FXCollections.observableArrayList();
    private Book book;


    @FXML
    private void initialize() {
		SQLConn.establishConnection();
			
    	titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("NAME"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("AUTHOR")); 
    
        setBook(null);
        bookTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setBook(newValue));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setBooks(Set<Book> books){
    	for(Book book : books)
    		this.books.add(book);
    }
    
    public void setBook(Book book) {
        this.book = book;
        
        name.setText(book.getName());
        author.setText(book.getAuthor());
        
        java.util.Date temp = new java.util.Date();
        Date date = new Date(temp.getTime());
        if(date.compareTo(book.getDate()) > 0)
        	overdue.setText("OVERDUE");
        else
        	overdue.setText("Due: " + new SimpleDateFormat("yyyy/MM/dd").format(book.getDate()));
    }

    @FXML
    private void checkIn() {
        book.setOwner(0);
        book.setCheckout(null);
    }
    
}