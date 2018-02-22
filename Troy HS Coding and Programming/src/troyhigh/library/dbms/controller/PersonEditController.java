package troyhigh.library.dbms.controller;

import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import troyhigh.library.dbms.Main;
import troyhigh.library.dbms.database.MemberSQLConn;
import troyhigh.library.dbms.model.Book;
import troyhigh.library.dbms.model.Member;

public class PersonEditController {
	@FXML
	private TableView<Member> memberTable;
	@FXML
	private TableColumn<Member, String> fNameCol, lNameCol;
	
	@FXML
    private TextField firstNameField, lastNameField;
    @FXML
    private RadioButton teacherField;
    @FXML
    private Label myBooksField, idField;

    @FXML
    private Stage dialogStage;
    
    private ObservableList<Member> members = FXCollections.observableArrayList();
    private Member member;
    
    private Main main;
    
    @FXML
    private void initialize() {
        updateTable();
        
        memberTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setPerson(newValue));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMain(Main main){ this.main = main; }
    
    public void setPerson(Member person) {
    	if(person == null)
    		this.member = new Member();
    	else 
	        this.member = person;
	    firstNameField.setText(member.getFName());
	    lastNameField.setText(member.getLName());
        if(member.getIsTeacher()) teacherField.setSelected(true);        
	       
	    String myBooks = "";
        for(Book book : member.getMyBooks()){
	       	myBooks += ", " + book.getName();
	    }
	    if(myBooks.equals("")) myBooksField.setText("");
	    else myBooksField.setText(myBooks.substring(1));
	        
	    idField.setText(member.getID() + "");
    }

    @FXML
    private void saveChanges() {
        Member member = new Member(firstNameField.getText(), lastNameField.getText(), teacherField.isSelected());
        if(isInputValid()){
        	MemberSQLConn.updateMember(member, Integer.parseInt(idField.getText()));
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
        MemberSQLConn.removeMember(Integer.parseInt(idField.getText()));
        
        updateTable();
    }
    
    @FXML
    private void newUser() {
    	Member member = new Member(firstNameField.getText(), lastNameField.getText(), teacherField.isSelected());
        if(isInputValid()) {
        	MemberSQLConn.addMember(member);
        	updateTable();
        }
        else{
        	Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.showAndWait();
        }        
    }

    public void handleEditMyBooks(){
    	main.openEditMyBooks(member);
    }

    public void updateTable(){ 
    	Set<Member> temp = MemberSQLConn.getAllMembers("select * from Members");
    	members = FXCollections.observableArrayList();
    	for(Member member : temp){
    		members.add(member);
    	}
    	members.add(new Member());
    	
    	fNameCol.setCellValueFactory(cellData -> cellData.getValue().getObservableF());
        lNameCol.setCellValueFactory(cellData -> cellData.getValue().getObservableL()); 
    
        setPerson(new Member());        
        memberTable.setItems(members);
    }
    
    private boolean isInputValid() {
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) return false;
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) return false;
        return true;
    }
}