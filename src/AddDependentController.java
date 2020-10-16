package application;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddDependentController {
	
	public AddDependentModel addDependentModel = new AddDependentModel();
	
	public String empSsn;
	
	public void passSsn(String ssn) {
		empSsn = ssn;
	}
	
	
	@FXML
	private TextField txtDependentName;
	
	@FXML
	private RadioButton radioMale;
	private String radioButtonLabel = "M";
	
	@FXML
	private RadioButton radioFemale;
	
	@FXML
	private DatePicker birthDate;
	
	@FXML
	private TextField txtRelation;
	
	@FXML
	private Label lblCheckInput;
	
	public void maleButtonAction(ActionEvent Event) {
		radioButtonLabel = radioMale.getText();
	}
	
	public void femaleButtonAction(ActionEvent event) {
		radioButtonLabel = radioFemale.getText();
	}
	
	public int checkTextField = 0;
	public int checkSuccess;
	public void checkText(TextField textField, TextField textField2) {
		String regex="[A-Za-z\\s]+";      
	     if(textField.getText().matches(regex) == false || textField2.getText().matches(regex) == false) {
	    	 checkTextField = 1;
	     }
	}
	
	private void showAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Add dependent");
		alert.setHeaderText("SUCCESS!");
		alert.setContentText("Successfully added dependent to the database!");
		alert.showAndWait();
	}
	
	public void checkDate(DatePicker dp) {
		if(dp.getValue() == null) {
			checkTextField = 2;
		}
	}
	
	public void addDependent(ActionEvent event) throws SQLException{
		checkTextField = 0;
		checkDate(birthDate);
		checkText(txtDependentName, txtRelation);
		if(checkTextField == 0) {
			LocalDate ld = birthDate.getValue();
			Date date = java.sql.Date.valueOf(ld);
			checkSuccess = addDependentModel.addDependent(txtDependentName.getText(), radioButtonLabel, date, txtRelation.getText(), empSsn);
				try {
					if(checkSuccess == 1400) {
						lblCheckInput.setText("Null values cannot be inserted! May be employee ssn not found!");
					}
					else if(checkSuccess == 3) {
						lblCheckInput.setText("");
						showAlert();
						Node source = (Node) event.getSource();
						Stage stage = (Stage) source.getScene().getWindow();
						stage.close();
						Stage primaryStage = new Stage();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/printReport.fxml"));
						Parent root = (Parent) loader.load();
						PrintReportController printReportController = loader.getController();
						printReportController.getSsn(empSsn);
						printReportController.getData();
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage.setScene(scene);
						primaryStage.show();
					}
					else if(checkSuccess == 1) {
						lblCheckInput.setText("Please choose different name! Unique constraint violated");
					}
					else {
						lblCheckInput.setText("Error adding the dependent.");
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println("Empty field values!");
				}
			}
			else if(checkTextField == 1) {
				lblCheckInput.setText("Please enter valid name or relation!");
			}
			else {
				lblCheckInput.setText("Please select birth date!");
			}
		}
	
	public void dependentbutton(ActionEvent event) throws SQLException {
		addDependent(event);
	}
	
	public void addAnotherbutton(ActionEvent event) throws SQLException {
		checkTextField = 0;
		checkDate(birthDate);
		checkText(txtDependentName, txtRelation);
		if(checkTextField == 0) {
				LocalDate ld = birthDate.getValue();
				Date date = java.sql.Date.valueOf(ld);
				checkSuccess = addDependentModel.addDependent(txtDependentName.getText(), radioButtonLabel, date, txtRelation.getText(), empSsn);	
				try {
					if(checkSuccess == 1400) {
						lblCheckInput.setText("Null values cannot be inserted! May be employee ssn not found!");
					}
					else if(checkSuccess == 3) {
						showAlert();
						Node source = (Node) event.getSource();
						Stage stage = (Stage) source.getScene().getWindow();
						stage.close();
						Stage primaryStage = new Stage();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/addDependent.fxml"));
						Parent root = (Parent) loader.load();
						AddDependentController add_dependent_controller = loader.getController();
						add_dependent_controller.passSsn(empSsn);
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage.setScene(scene);
						primaryStage.show();
					}
					else if(checkSuccess == 1) {
						lblCheckInput.setText("Please choose different name! Unique constraint violated");
					}
					else if(checkTextField == 1) {
						lblCheckInput.setText("Please enter valid name or relation!");
					}
					else {
						lblCheckInput.setText("Please select birth date!");
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println("Empty field values!");
				}
			}
		else if(checkTextField == 1) {
			lblCheckInput.setText("Please enter valid name or relation!");
		}
		else {
			lblCheckInput.setText("Please select birth date!");
		}
	}
}
