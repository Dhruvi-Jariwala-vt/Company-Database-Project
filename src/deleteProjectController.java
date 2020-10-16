package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class deleteProjectController {
	
	public deleteProjectModel DeleteProjectModel = new deleteProjectModel();
	
	public void uncheckBox(CheckBox checkBox) {
		checkBox.setSelected(false);
	}
	
	public void checkSelected(CheckBox checkBox, int no) {
		if(checkBox.isSelected()) {
			projectNumbers.add(no);
		}
	}
	
	public ObservableList<Integer> projectNumbers = FXCollections.observableArrayList();
	
	@FXML
	public CheckBox checkBox1;
	public void checkBox1Action(ActionEvent event) {
		if(checkBox1.isSelected()) {
		}
		else {
			uncheckBox(checkBox1);
		}
	}
	
	@FXML
	public CheckBox checkBox2;
	public void checkBox2Action(ActionEvent event) {
		if(checkBox2.isSelected()) {		
		}
		else {
			uncheckBox(checkBox2);	
		}
	}
	
	@FXML
	public CheckBox checkBox3;
	public void checkBox3Action(ActionEvent event) {
		if(checkBox3.isSelected()) {	
		}
		else {
			uncheckBox(checkBox3);	
		}
	}
	
	@FXML
	public CheckBox checkBox10;
	public void checkBox10Action(ActionEvent event) {
		if(checkBox10.isSelected()) {	
		}
		else {
			uncheckBox(checkBox10);
		}
	}
	
	@FXML
	public CheckBox checkBox20;
	public void checkBox20Action(ActionEvent event) {
		if(checkBox20.isSelected()) {	
		}
		else {
			uncheckBox(checkBox20);
		}
	}
	
	@FXML
	public CheckBox checkBox30;
	public void checkBox30Action(ActionEvent event) {
		if(checkBox30.isSelected()) {
		}
		else {
			uncheckBox(checkBox30);
		}
	}
	
	@FXML
	public Button btnRemove;
	
	@FXML
	public Button btnSuggest;
	
	@FXML
	public Label lblStatus;
	
	public void alert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Referential Integrity Constraint Violation!");
		alert.setHeaderText(null);
		alert.setContentText("The project you are trying to delete has been assigned to multiple employees. Upon deletion of project will result in deletion of all those employees!");
		alert.showAndWait();
	}
	
	public void alertUnsuccess() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Constraint Violation!");
		alert.setHeaderText(null);
		alert.setContentText("Reject Operation. The project you are trying to delete has been assigned to multiple employees.");
		alert.showAndWait();
	}
	
	public void alertSuggestion() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("SUGGESTION!");
		alert.setHeaderText(null);
		alert.setContentText("Suggestion: First set the employee working on that project to some other project in his department! If the project being deleted is the only project in the department restrict the operation. If not, it would be safe to delete the project now.");
		alert.showAndWait();
	}
	
	
	public void removeProjectAction(ActionEvent event) {
		try {
			projectNumbers.clear();
			checkSelected(checkBox1, 1);
			checkSelected(checkBox2, 2);
			checkSelected(checkBox3, 3);
			checkSelected(checkBox10, 10);
			checkSelected(checkBox20, 20);
			checkSelected(checkBox30, 30);
			int status = DeleteProjectModel.removeProject(projectNumbers);
			if(status == -3) {
				lblStatus.setText("Deletion successfull");
			}
			else if(status == 1) {
				alertUnsuccess();
				lblStatus.setText("Deletion Unsuccessfull");
				
			}
			else if(status == 2292) {
				alertUnsuccess();
				lblStatus.setText("Suggestion: First set the employee working on that project to some other project in his department! If the project being deleted is the only project in the department restrict the operation. If not, it would be safe to delete the project now.");
			}
			else if(status == -1) {
				lblStatus.setText("Click default button to set referencing tuple values to DEFAULT value i.e. 1");
				lblStatus.setText(lblStatus.getText()+"\nNote that the referencing tuples department number will be changed ");
				alert();
			}
			else {
				alertUnsuccess();
				lblStatus.setText("Deletetion Unsuccessfull");
			}
		}
		catch(Exception e) {
			lblStatus.setText("Deletetion Unsuccessfull");
		}
		
		
	}
	
	public void SuggestAction(ActionEvent event) {
		alertSuggestion();
		
	}
}
