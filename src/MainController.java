package application;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class MainController implements Initializable {

	public MainModel mainModel = new MainModel();
	
	@FXML
	private Label lblLogin;
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(mainModel.isDbConnected()) {
			lblLogin.setText("Connected");
		}
		else {
			lblLogin.setText("Please check DB connection.");
		}
		
	}
	
	
	public int returnInt = 0;
	
	public void scanDB(ActionEvent event) throws SQLException {
		try {
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			Stage primaryStage1 = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/scanDbReport.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage1.setScene(scene);
			primaryStage1.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addEmployeeAction(ActionEvent event) throws Exception {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Add Employee");
			dialog.setHeaderText("Enter your SSN:");
			dialog.setContentText("SSN:");
			
			dialog.showAndWait();
			
			TextField input = dialog.getEditor();
			
			if(input.getText() != null && input.getText().toString().length() != 0) {
				try {
					if(mainModel.checkManager(input.getText().trim())) {
						lblLogin.setText("Success!");
						Node source = (Node) event.getSource();
						Stage stage = (Stage) source.getScene().getWindow();
						stage.close();
						Stage primaryStage1 = new Stage();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/addEmployee.fxml"));
						Parent root = (Parent) loader.load();
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage1.setScene(scene);
						primaryStage1.show();
					}
					else {
						lblLogin.setText("You are not allowed to add an Employee.");
					}
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			else {
				lblLogin.setText("Please Enter SSN");
			}
		}
	}
