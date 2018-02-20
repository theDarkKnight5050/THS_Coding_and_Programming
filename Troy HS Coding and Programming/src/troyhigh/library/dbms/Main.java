package troyhigh.library.dbms;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import troyhigh.library.dbms.controller.BooksEditController;
import troyhigh.library.dbms.controller.PersonEditController;
import troyhigh.library.dbms.controller.SettingsController;
import troyhigh.library.dbms.model.Member;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {	
	private BorderPane rootLayout;
	private Stage primaryStage;
	
	private String username, password;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("THS DBMS");
		
		initRootLayout();
	}
	
	public void initRootLayout(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainView.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void openSettingsPage(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/SettingsPage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("THS Library Books Database");
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            SettingsController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            
            dialogStage.showAndWait();
            
            username = controller.getUsername();
            password = controller.getPassword();
            
            Member.setMaxStudentCheckout(controller.getMaxStudentCheckout());
            Member.setMaxTeacherCheckout(controller.getMaxTeacherCheckout());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@FXML
	private void openPersonDialogue(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/PersonEditDialogue.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("THS Library Members Database");
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PersonEditController controller = loader.getController();
            controller.setUsername(username);
            controller.setPassword(password);
            controller.setDialogStage(dialogStage);
            
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@FXML
	private void openBookDialogue(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/BooksEditDialogue.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("THS Library Books Database");
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            BooksEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@FXML
	private void openPrintDialogue(){
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
