package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmployeeProjectController implements Initializable{

	public EmployeeProjectModel employeeProjectModel = new EmployeeProjectModel();

	public String empSsn;
	public void passSsn(String ssn) {
		empSsn = ssn;
	}
	
	public int check = 0;
	public int projectCondition = 0;
	ObservableList<Integer> checkBoxList1 = FXCollections.observableArrayList();
	ObservableList<Integer> hourList1 = FXCollections.observableArrayList();
	
	public void defAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("DEFAULT PROJECTS");
        alert.setContentText("Default projects are assigned.");
 
        alert.showAndWait();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblInput.setText("Default Projects Selected!!");
		defAlert();
		try {
			employeeProjectModel.initialProjectLoad();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void checkProjects(String ssn) throws SQLException {
		ObservableList<Integer> pno = FXCollections.observableArrayList();
		pno = employeeProjectModel.checkProjects(ssn);
		for(int i = 0; i < pno.size(); i++) {
			if(pno.get(i) == 1) {
				if(!(chkBox1.isSelected()))
					chkBox1.setSelected(true);
			}
			if(pno.get(i) == 2) {
				if(!(chkBox2.isSelected()))
					chkBox2.setSelected(true);
			}
			if(pno.get(i) == 3) {
				if(!(chkBox3.isSelected()))
					chkBox3.setSelected(true);
			}
			if(pno.get(i) == 10) {
				if(!(chkBox4.isSelected()))
					chkBox4.setSelected(true);
			}
			if(pno.get(i) == 20) {
				if(!(chkBox5.isSelected()))
					chkBox5.setSelected(true);
			}
			if(pno.get(i) == 30) {
				if(!(chkBox6.isSelected()))
					chkBox6.setSelected(true);
			}
		}
		lblProjects.setText(pno.toString());
	}
	
	public int checkConstraint(ObservableList<Integer> checkbox) throws SQLException {
		projectCondition = 0;
		ObservableList<Integer> pnumbers = FXCollections.observableArrayList();
		pnumbers = employeeProjectModel.checkProjects(empSsn);
		int count = 0;
		for(int i = 0; i<pnumbers.size(); i++) {
			for(int j = 0; j<checkbox.size(); j++) {
				if(checkbox.get(j) == pnumbers.get(i)) {
					count++;
				}
			}
		}
		if(count > 2) {
			lblInput.setText("Sorry, you are not allowed to choose more than 2 projects in that department");
			projectCondition = 1;
			checkBoxList1.clear();
			hourList1.clear();
		}
		else if(count < 1) {
			lblInput.setText("Please select at least one of the projects listed");
			projectCondition = 1;
			checkBoxList1.clear();
			hourList1.clear();
		}
		else {
			projectCondition = 0;
			lblInput.setText(" ");
		}
		return projectCondition;
	}
	
	public void addhours(TextField txtField, CheckBox checkBox) {
		try {
			if((!(txtField.getText().isEmpty())) && (checkBox.isSelected())) {
				if(Integer.parseInt(txtField.getText().trim()) >= 0) {
					hourList1.add(Integer.parseInt(txtField.getText().trim()));
					checkBoxList1.add(Integer.parseInt(checkBox.getText()));
				}
				else {
					check = 1;
					lblInput.setText("Please enter a positive number!");
					txtField.clear();
				}
			}
			else if((!(txtField.getText().isEmpty())) && !(checkBox.isSelected())) {
				check = 1;
				lblInput.setText("Please make sure to check the checkbox.");
			}
			else {
				txtField.clear();
				uncheckBox(checkBox);
			}
		}
		catch(Exception e) {
			check = 1;
			lblInput.setText("Please enter valid number!");
		}
	}
	
	public int returnHourTotal(ObservableList<Integer> hours) {
		int sum = 0;
		List<Integer> hoursArray = new ArrayList<Integer>();
		for(int i = 0; i<hours.size(); i++) {
			hoursArray.add(hours.get(i));
		}
		for(int j = 0; j<hoursArray.size(); j++) {
			sum = sum + hoursArray.get(j);
		}
		return sum;
	}
	
	public void callAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("Cannot assign more than 40 hours. Please decrease the assigned hours!");
		alert.show();
	}
	
	public void uncheckBox(CheckBox checkBox) {
		if(checkBox.isSelected()) {
			checkBox.setSelected(false);
		}
		else {
			//System.out.println("Nothing to unchecked!");
		}
	}
	
	@FXML
	private Label lblProjects;
	
	@FXML
	private Label lblInput;
	
	@FXML
	private TextField txtProductX;
	
	@FXML
	private CheckBox chkBox1;
	public void chkBox1Action(ActionEvent event) {
		if(chkBox1.isSelected()) {
			//System.out.println("Handling select event");
		}
		else {
			uncheckBox(chkBox1);
			txtProductX.clear();
		}
	}
	
	@FXML
	private TextField txtProductY;
	
	@FXML
	private CheckBox chkBox2;
	public void chkBox2Action(ActionEvent event) {
		if(chkBox2.isSelected()) {
			//System.out.println("Handling select event");
		}
		else {
			uncheckBox(chkBox2);
			txtProductY.clear();
		}
	}
	
	@FXML
	private TextField txtProductZ;
	
	@FXML
	private CheckBox chkBox3;
	public void chkBox3Action(ActionEvent event) {
		if(chkBox3.isSelected()) {
			//System.out.println("Handling select event");
		}
		else {
			uncheckBox(chkBox3);
			txtProductZ.clear();
		}
	}
	
	@FXML
	private TextField txtComputerization;

	@FXML
	private CheckBox chkBox4;
	public void chkBox4Action(ActionEvent event) {
		if(chkBox4.isSelected()) {
			//System.out.println("Handling select event");
		}
		else {
			uncheckBox(chkBox4);
			txtComputerization.clear();
		}
	}
	
	@FXML
	private TextField txtReorganization;
	
	@FXML
	private CheckBox chkBox5;
	public void chkBox5Action(ActionEvent event) {
		if(chkBox5.isSelected()) {
			//System.out.println("Handling select event");
		}
		else {
			uncheckBox(chkBox5);
			txtReorganization.clear();
		}
	}
	
	@FXML
	private TextField txtNewbenefits;
	
	@FXML
	private CheckBox chkBox6;
	public void chkBox6Action(ActionEvent event) {
		if(chkBox6.isSelected()) {
			//System.out.println("Handling select event");
		}
		else {
			uncheckBox(chkBox6);
			txtNewbenefits.clear();
		}
	}
	
	private void showAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Add Projects");
		alert.setHeaderText("SUCCESS!!");
		alert.setContentText("Successfully added Projects/Hours to the database!");
		
		alert.showAndWait();
	}

	public int checkSuccess = 0;
	
	public void addproject(ActionEvent event) throws SQLException {
		check = 0;
		int def_projects = 0;
//		addCheckBox(chkBox1);
//		addCheckBox(chkBox2);
//		addCheckBox(chkBox3);
//		addCheckBox(chkBox4);
//		addCheckBox(chkBox5);
//		addCheckBox(chkBox6);
		addhours(txtProductX, chkBox1);
		addhours(txtProductY, chkBox2);
		addhours(txtProductZ, chkBox3);
		addhours(txtComputerization, chkBox4);
		addhours(txtReorganization, chkBox5);
		addhours(txtNewbenefits, chkBox6);
		if(check == 0) {
			def_projects = checkConstraint(checkBoxList1);
		}
		else {
			hourList1.clear();
			checkBoxList1.clear();
		}
			
		if(check == 0 && def_projects == 0) {
			if(returnHourTotal(hourList1) <= 40) {
				try {
					checkSuccess = employeeProjectModel.addProject(checkBoxList1, hourList1, empSsn);
					if(checkSuccess > 0) {
						showAlert();
						Node source = (Node) event.getSource();
						Stage stage = (Stage) source.getScene().getWindow();
						stage.close();
						Stage primaryStage = new Stage();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/askDependent.fxml"));
						Parent root = (Parent) loader.load();
						AskDependentController dependent_control = loader.getController();
						dependent_control.passSsn(empSsn);
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage.setScene(scene);
						primaryStage.show();
					}
					else {
						lblInput.setText("Please, assign projects!");
					}
				}
				catch(Exception e) {
					lblInput.setText("Please enter valid number!");
				}
			}
			else {
				callAlert();
				hourList1.clear();
				checkBoxList1.clear();
			}
		}
		else {
//			System.out.println("Handled!!");
		}
	}
}
