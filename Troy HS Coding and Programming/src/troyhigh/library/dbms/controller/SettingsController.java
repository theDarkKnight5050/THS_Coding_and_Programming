package troyhigh.library.dbms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import troyhigh.library.dbms.database.SQLConn;
import troyhigh.library.dbms.model.Member;

public class SettingsController{
	private String username, password;
	private int maxTeacherCheckout, maxStudentCheckout;
	
	@FXML
	private TextField newName, newPass;
	@FXML
	private TextField newMaxTeacher, newMaxStudent;
	
	@FXML
	private Stage dialogStage;
	
	@FXML
	private void initialize(){
		
	}
	
	public void setDialogStage(Stage stage){
		this.dialogStage = stage;
	}
	
	public String getUsername(){ return username; }
	public String getPassword(){ return  password; }
	public int getMaxTeacherCheckout(){ return maxTeacherCheckout; }
	public int getMaxStudentCheckout(){ return maxStudentCheckout; }
	
	@FXML
	public void setUserInfo(){
		SQLConn.setUsername(newName.getText());
		SQLConn.setPassword(newPass.getText());
		SQLConn.establishConnection();
		
		newName.setText("");
		newPass.setText("");
	}
	
	@FXML
	public void setMemberInfo(){
		try{
			Member.setMaxTeacherCheckout(Integer.parseInt(newMaxTeacher.getText()));
			Member.setMaxStudentCheckout(Integer.parseInt(newMaxStudent.getText()));
			
			newMaxTeacher.setText("");
			newMaxStudent.setText("");
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Succesful Update");
			alert.setHeaderText("Succesfully updated values for max teacher and student checkout days");
			alert.showAndWait();
		} catch(Exception e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Input Error");
			alert.setHeaderText("Failed to update member info. Try changing value to a number");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
}
