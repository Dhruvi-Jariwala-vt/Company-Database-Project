package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AskDependentController {

	@FXML
	private CheckBox yesCheckBox;
	public void yesCheckBoxAction(ActionEvent event) {
		
	}
	
	@FXML
	private CheckBox noCheckBox;
	public void noCheckBoxAction(ActionEvent event) {
		
	}
	
	String empSsn;
	
	public void passSsn(String ssn) {
		empSsn = ssn;
	}
	
	public int flag = 0;
	
	public void makeReport() {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/PrintReport.fxml"));
			Parent root = (Parent) loader.load();
			PrintReportController printReportController = loader.getController();
			printReportController.getSsn(empSsn);
			printReportController.getData();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void askDependentAction(ActionEvent event) {
		if(yesCheckBox.isSelected() && noCheckBox.isSelected()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please select any one option!");
			alert.show();
		}
		else if(yesCheckBox.isSelected()) {
			try {
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
			catch(Exception e) {
				System.out.println(e);
			}
		}
		else if(noCheckBox.isSelected()) {
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			makeReport();
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Neither option selected! Default option No is being selected.");
			alert.showAndWait();
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			makeReport();
		}
		
	}
	
}
