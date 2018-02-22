package troyhigh.library.dbms.controller;

import java.sql.Date;
import java.util.Set;
import java.text.SimpleDateFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import troyhigh.library.dbms.database.BookSQLConn;
import troyhigh.library.dbms.model.Book;

public class MyBooksEditController {
	@FXML
	private TableView<Book> bookTable;
	@FXML
	private TableColumn<Book, String> titleCol, authorCol;
	
	@FXML
    private Label name, author, overdue, id;
	
    @FXML
    private Stage dialogStage;
    
    private ObservableList<Book> books = FXCollections.observableArrayList();
    private Book book;
    private int owner;
    
    @FXML
    public void initialize(){
    	updateTable();
    	
        bookTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setBook(newValue));
    }
    
    public void updateTable() {
    	Set<Book> temp = BookSQLConn.getAllBooks("select * from Books where owner = " + owner);
    	books = FXCollections.observableArrayList();
    	for(Book book : temp){
    		books.add(book);
    	}
    	books.add(new Book());
    	
    	titleCol.setCellValueFactory(cellData -> cellData.getValue().getObservableN());
        authorCol.setCellValueFactory(cellData -> cellData.getValue().getObservableA()); 
    
        setBook(new Book());
        bookTable.setItems(books);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setBooks(Set<Book> books){
    	for(Book book : books)
    		this.books.add(book);
    	
    	updateTable();
    }
    
    public void setOwner(int owner){
    	this.owner = owner;
    }
    
    public void setBook(Book book) {
        this.book = book;
        
        name.setText(book.getName());
        author.setText(book.getAuthor());
        
        if(book.getFine() > 0)
        	overdue.setText("OVERDUE");
        else
        	overdue.setText("" + new SimpleDateFormat("yyyy/MM/dd").format(book.getDate()));
    
        id.setText(book.getID() + "");
    }

    @FXML
    private void checkIn() {
        book.setOwner(0);
        book.setCheckout(null);
        BookSQLConn.updateBook(book, book.getID());
        
        updateTable();
    }
    
    @FXML
    private void checkOut() {
    	book.setOwner(owner);
    	book.setCheckout(new Date((new java.util.Date()).getTime()));
        BookSQLConn.updateBook(book, book.getID());
        
        updateTable();
    }
}