package troyhigh.library.dbms.controller;

import java.io.IOException;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import troyhigh.library.dbms.database.MemberSQLConn;
import troyhigh.library.dbms.database.SQLConn;
import troyhigh.library.dbms.model.Book;
import troyhigh.library.dbms.model.Member;

public class PersonEditController {
	private TableView<Member> memberTable;
	private TableColumn<Member, String> fNameCol;
	private TableColumn<Member, String> lNameCol;
	
    private TextField firstNameField;
    private TextField lastNameField;
    private RadioButton teacherField;
    private Label myBooksField;
    private Label idField;

    private Stage dialogStage;
    
    private ObservableList<Member> members = FXCollections.observableArrayList();
    private Member member;
    private String username;
    private String password;
    
    @FXML
    private void initialize() {
    	SQLConn.establishConnection();
    	
    	Set<Member> temp = MemberSQLConn.getAllMembers("select * from Members");
    	for(Member member : temp){
    		members.add(member);
    	}
    	
    	fNameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("fName"));
        lNameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("lName")); 
    
        setPerson(null);
        memberTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setPerson(newValue));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPerson(Member person) {
        this.member = person;

        firstNameField.setText(person.getFName());
        lastNameField.setText(person.getLName());
        if(person.getIsTeacher()) teacherField.setSelected(true);        
       
        String myBooks = "";
        for(Book book : person.getMyBooks()){
        	myBooks += ", " + book.getName();
        }
        if(myBooks.equals("")) myBooksField.setText("");
        else myBooksField.setText(myBooks.substring(0));
        
        idField.setText(person.getID() + "");
    }

    @FXML
    private void saveChanges() {
        Member member = new Member(firstNameField.getText(), lastNameField.getText(), teacherField.isSelected());
        MemberSQLConn.updateMember(member, Integer.parseInt(idField.getText()));
    }

    @FXML
    private void delete() {
        MemberSQLConn.removeMember(Integer.parseInt(idField.getText()));
    }
    
    @FXML
    private void newUser() {
    	Member member = new Member(firstNameField.getText(), lastNameField.getText(), teacherField.isSelected());
        MemberSQLConn.addMember(member);
    }

    public void handleEditMyBooks(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PersonEditController.class.getResource("view/MyBooksEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit " + member.getLName() + "'s Books");
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            MyBooksEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setBooks(member.getMyBooks());
            
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setUsername(String username){ this.username = username; }
    public void setPassword(String password){ this.password = password; }

//    private boolean isInputValid() {
//        String errorMessage = "";
//
//        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
//            errorMessage += "No valid first name!\n"; 
//        }
//        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
//            errorMessage += "No valid last name!\n"; 
//        }
//
//        if (errorMessage.length() == 0) {
//            return true;
//        } 
//        else {
//            Alert alert = new Alert(AlertType.ERROR);
//            alert.initOwner(dialogStage);
//            alert.setTitle("Invalid Fields");
//            alert.setHeaderText("Please correct invalid fields");
//            alert.setContentText(errorMessage);
//
//            alert.showAndWait();
//
//            return false;
//        }
//    }
}