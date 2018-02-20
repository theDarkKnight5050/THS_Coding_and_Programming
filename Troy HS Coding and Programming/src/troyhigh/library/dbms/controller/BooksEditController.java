package troyhigh.library.dbms.controller;

import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import troyhigh.library.dbms.database.BookSQLConn;
import troyhigh.library.dbms.database.SQLConn;
import troyhigh.library.dbms.model.Book;

public class BooksEditController {
	private TableView<Book> bookTable;
	private TableColumn<Book, String> titleCol;
	private TableColumn<Book, String> authorCol;
	
    private TextField name;
    private TextField author;
    private Label owner;
    private Label id;
	    
    private ObservableList<Book> books = FXCollections.observableArrayList();
	
    @FXML
	private Stage dialogStage;

    @FXML
    private void initialize() {
    	SQLConn.establishConnection();
    	
		Set<Book> temp = BookSQLConn.getAllBooks("select * from books");
		for(Book book : temp){
			books.add(book);
		}
    	
    	titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("NAME"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("AUTHOR")); 
    
        setBook(null);
        bookTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setBook(newValue));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setBook(Book book) {
        name.setText(book.getName());
        author.setText(book.getAuthor());
        owner.setText(book.getOwner() + "");
        id.setText(book.getID() + "");
    }

    @FXML
    private void saveChanges() {
        Book book = new Book(name.getText(), author.getText(), Integer.parseInt(id.getText()), Integer.parseInt(owner.getText()));
        BookSQLConn.updateBook(book, Integer.parseInt(id.getText()));
    }

    @FXML
    private void delete() {
        BookSQLConn.removeBook(Integer.parseInt(id.getText()));
    }
    
    @FXML
    private void newBook() {
    	Book book = new Book(name.getText(), author.getText());
        BookSQLConn.addBook(book);
    }
}