package troyhigh.library.dbms.controller;

import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import troyhigh.library.dbms.database.BookSQLConn;
import troyhigh.library.dbms.model.Book;

public class BooksEditController {
	@FXML
	private TableView<Book> bookTable;
	@FXML
	private TableColumn<Book, String> titleCol, authorCol;
	
	@FXML
    private TextField name, author;
    @FXML
	private Label owner, id;
	    
    private ObservableList<Book> books = FXCollections.observableArrayList();
	private Book book;
    
    @FXML
	private Stage dialogStage;

    @FXML
    private void initialize() {
		updateTable();
    	
        bookTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setBook(newValue));
    }

    public void updateTable(){
    	Set<Book> temp = BookSQLConn.getAllBooks("select * from Books");
		books = FXCollections.observableArrayList();
    	for(Book book : temp){
			books.add(book);
		}
    	books.add(new Book());
    	
    	titleCol.setCellValueFactory(cellData -> cellData.getValue().getObservableN());
        authorCol.setCellValueFactory(cellData -> cellData.getValue().getObservableA()); 
    
        bookTable.setItems(books);
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setBook(Book newBook) {
    	if(newBook == null)
    		this.book = new Book();
    	else
    		this.book = newBook;
    	
        name.setText(book.getName());
        author.setText(book.getAuthor());
        owner.setText(book.getOwner() + "");
        id.setText(book.getID() + "");
    }

    @FXML
    private void saveChanges() {
    	if(inputIsValid()){
	        Book book = new Book(name.getText(), author.getText(), Integer.parseInt(id.getText()), Integer.parseInt(owner.getText()));
	        BookSQLConn.updateBook(book, Integer.parseInt(id.getText()));
	        updateTable();
    	}
    	else{
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.showAndWait();
    	}
    }

    @FXML
    private void delete() {
        BookSQLConn.removeBook(Integer.parseInt(id.getText()));
    
        updateTable();
    }
    
    @FXML
    private void newBook() {
        if(inputIsValid()) {
        	Book book = new Book(name.getText(), author.getText());
        	BookSQLConn.addBook(book);
        	updateTable();
        }
        else {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.showAndWait();
        }
    }
    
    private boolean inputIsValid() {
        if (name.getText() == null || name.getText().length() == 0) return false;
        if (author.getText() == null || author.getText().length() == 0) return false;
        return true;
    }
}