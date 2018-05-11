package troyhigh.library.dbms.controller;

import java.sql.Date;
import java.util.Set;
import java.text.SimpleDateFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import troyhigh.library.dbms.database.BookSQLConn;
import troyhigh.library.dbms.model.Book;
import troyhigh.library.dbms.model.Member;

public class MyBooksEditController {
	@FXML
	private TableView<Book> bookTable;
	@FXML
	private TableColumn<Book, String> titleCol, authorCol;
	
	@FXML
    private Label name, author, overdue, id, ownerL;
	
    @FXML
    private Stage dialogStage;
    
    @FXML
    private Button inButton, outButton;
    
    private ObservableList<Book> books = FXCollections.observableArrayList();
    private Book book;
    private Member owner;
    
    @FXML
    public void initialize(){
    	updateTable(new Book());
    	
        bookTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setBook(newValue));
    }
    
    public void updateTable(Book b) {
    	Set<Book> temp = BookSQLConn.getAllBooks("select * from Books");
    	books = FXCollections.observableArrayList();
    	for(Book book : temp){
    		books.add(book);
    	}
    	books.add(new Book());
    	
    	titleCol.setCellValueFactory(cellData -> cellData.getValue().getObservableN());
        authorCol.setCellValueFactory(cellData -> cellData.getValue().getObservableA()); 
    
        setBook(b);
        bookTable.setItems(books);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

//    public void setBooks(Set<Book> books){
//    	for(Book book : books)
//    		this.books.add(book);
//    	
//    	updateTable(new Book());
//    }
    
    public void setBook(Book book) {
        this.book = book;
        
        name.setText(book.getName());
        author.setText(book.getAuthor());
        
        String date = "" + new SimpleDateFormat("yyyy/MM/dd").format(book.getDate());
    	if(date.equals("1969/12/31"))
    		overdue.setText("Available");
    	else{
    		overdue.setText(date);
        	if(book.getFine() > 0) overdue.setText("OVERDUE");
    	}
        
        if(owner != null && owner.getID() == book.getOwner()){ 
        	if(owner.getID() == book.getOwner()) ownerL.setText(owner.getLName());
        	
        	outButton.setDisable(true);
        	inButton.setDisable(false);
        }
        else{
        	ownerL.setText("");
        	
        	outButton.setDisable(false);
        	inButton.setDisable(true);
        }
    
        id.setText(book.getID() + "");
    }

    public void setOwner(Member m){
    	owner = m;
    }
    
    @FXML
    private void checkIn() {
        book.setOwner(0);
        book.setCheckout(null);
        BookSQLConn.updateBook(book, book.getID());
        
        setBook(book);
    }
    
    @FXML
    private void checkOut() {
    	book.setOwner(owner.getID());
    	book.setCheckout(new Date((new java.util.Date()).getTime()));
        BookSQLConn.updateBook(book, book.getID());
        
        setBook(book);
    }
}