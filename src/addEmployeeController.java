package application;

import java.sql.Date;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class addEmployeeController {
	
	public AddEmployeeModel addEmployeeModel = new AddEmployeeModel();	

	@FXML
	private TextField txtFirstName;

	@FXML
	private TextField txtMiddleInitial;

	@FXML
	private TextField txtLastName;

	@FXML
	private TextField txtSsn;

	@FXML
	private DatePicker txtDate;
	
	@FXML
	private TextField txtAddress;

	@FXML
	private RadioButton radioMale;
	private String radioButtonLabel = "M";
	
	@FXML
	private RadioButton radioFemale;
	
	@FXML
	private TextField txtSalary;

	@FXML
	private TextField txtSuperSsn;

	@FXML
	private TextField txtDeptNum;

	@FXML
	private TextField txtEmail;
	
	@FXML
	private Button btnNext;
	
	@FXML
	private Label lblCheckValue;
	
	public void radioMaleBtnAction(ActionEvent event) {
		radioButtonLabel = radioMale.getText();
	}
	public void radioFemaleBtnAction(ActionEvent event) {
		radioButtonLabel = radioFemale.getText();
	}

	public int field_check, invalidInput, checkLetter, checkNumber, mgrCheck = 0;
	ObservableList<String> invalidNames = FXCollections.observableArrayList();
	ObservableList<String> invalidNumbers = FXCollections.observableArrayList();
	
	public void checkNumber(TextField txtField) {
		String regexNumber = "^[0-9]*$";
		if(txtField.getText().trim().matches(regexNumber) == false || txtField.getText().trim().isEmpty()) {
			checkNumber = 1;
			invalidNumbers.add(txtField.getId().toString());
		}
	}
	
	public void checkText(TextField txtField) {
		String regexLetter = "[A-Za-z]+";     
	     if(txtField.getText().trim().matches(regexLetter) == false || txtField.getText().trim().isEmpty()) {
	    	 checkLetter = 1;
	    	 invalidNames.add(txtField.getId().toString());
	     }
	}
	
	public void checkAddress(TextField txtField) {
		String regexLetter = "[A-Za-z\\s\\,]+";
		if(txtField.getText().trim().matches(regexLetter) == false || txtField.getText().trim().isEmpty()) {
	    	 checkLetter = 1;
	    	 invalidNames.add(txtField.getId().toString());
	     }
	}
	
	private void showAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Add employee");
		alert.setHeaderText("SUCCESS!");
		alert.setContentText("Successfully added employee to the database!");
		
		alert.showAndWait();
	}
	
	public void nextButton(ActionEvent event) {
		invalidNames.clear();
		invalidNumbers.clear();
		field_check = 1;
		checkLetter = 0;
		checkNumber = 0;
		checkText(txtFirstName);
		checkText(txtMiddleInitial);
		checkText(txtLastName);
		checkAddress(txtAddress);
		checkNumber(txtSsn);
		checkNumber(txtSalary);
		checkNumber(txtDeptNum);
		checkNumber(txtSuperSsn);
		String ssn = txtSsn.getText().trim();
		if(checkLetter == 1) {
			lblCheckValue.setText("Enter valid values at following fields: "+invalidNames);
		}
		else if(checkNumber == 1) {
			lblCheckValue.setText("Enter valid values at following fields: "+invalidNumbers);
		}
		else if(txtDate.getValue() == null) {
			lblCheckValue.setText("Please select a birth date.");
		}
		else if(ssn.length() != 9) {
			lblCheckValue.setText("Enter valid ssn. i.e. a 9 digit number");
		}
		else if(checkLetter == 0 && checkNumber == 0) {
			try {
				LocalDate ld = txtDate.getValue();
				Date date = java.sql.Date.valueOf(ld);
				mgrCheck = addEmployeeModel.checkManager(txtSuperSsn.getText().trim());
				if(mgrCheck == 1) {
					invalidInput = addEmployeeModel.addEmployee(txtFirstName.getText().trim(), txtMiddleInitial.getText().trim(), txtLastName.getText().trim(), ssn, date, txtAddress.getText().trim(), radioButtonLabel, Long.parseLong(txtSalary.getText().trim()), txtSuperSsn.getText().trim(), Integer.parseInt(txtDeptNum.getText().trim()), txtEmail.getText().trim());
				}
				else {
					invalidInput = -3;
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				lblCheckValue.setText("Invalid field value. Please try again!");
				field_check = 0;
			}
			
			if(field_check == 1) {
				if(invalidInput == -9) {
					try {
						showAlert();
						Node source = (Node) event.getSource();
						Stage stage = (Stage) source.getScene().getWindow();
						stage.close();
						Stage primaryStage = new Stage();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/employeeProject.fxml"));
						primaryStage.initStyle(StageStyle.UNDECORATED);
						Parent root = (Parent) loader.load();
						EmployeeProjectController emp_contrl = loader.getController();
						emp_contrl.passSsn(txtSsn.getText().trim());
						emp_contrl.checkProjects(txtSsn.getText().trim());
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage.setScene(scene);
						primaryStage.show();
					}
					catch(Exception e) {
						e.printStackTrace();
						System.out.println("Error creating new stage");
					}			
				}
				else if(invalidInput > 0) {
					if(invalidInput == 1) {
						lblCheckValue.setText("Sorry, employee with same ssn exists!");
					}
					else if(invalidInput == 2291) {
						lblCheckValue.setText("Sorry, referential integrity constraint violated");
					}
					else {
						lblCheckValue.setText("Sorry, database rejected your insert");
					}
				}
				else if(invalidInput == -3) {
					lblCheckValue.setText(txtSuperSsn.getText()+" is not a manager.");
					txtSuperSsn.clear();
				}
				else {
					lblCheckValue.setText("Invalid Input!");
				}
			}	
		}
		
	}
	
}
