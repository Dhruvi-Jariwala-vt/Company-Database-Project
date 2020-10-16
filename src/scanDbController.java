package application;


import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class scanDbController implements Initializable {
	
	@FXML
	public Label lblMore;
	
	@FXML
	public Label lblLess;

	@FXML
	public Button btnRemoveLess;
	
	@FXML
	public Button btnRemoveMore;
	
	@FXML
	public Label lblWarning;
	
	public Optional<ButtonType> option;
	
	public void alert() {
		 Alert alert = new Alert(AlertType.CONFIRMATION);
	     alert.setTitle("Delete Employees!");
	     alert.setHeaderText("Are you sure want to remove these employees from database?");
	     alert.setContentText("All the entries related to these employees will be removed!");
	     
	     option = alert.showAndWait();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> more = FXCollections.observableArrayList();
		ObservableList<String> less = FXCollections.observableArrayList();
		StringBuilder moreStr = new StringBuilder();
		StringBuilder lessStr = new StringBuilder();
		try {
			scanDbModel scdb = new scanDbModel();
			more = scdb.getMoreData();
			less = scdb.getLessData();
			for(int i = 0; i < more.size(); i++) {
				moreStr.append(more.get(i)).append("\n");
			}
			for(int i = 0; i < less.size(); i++) {
				lessStr.append(less.get(i)).append("\n");
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		lblMore.setText(moreStr.toString());
		lblLess.setText(lessStr.toString());
		if(more.size() == 0) {
			lblMore.setText("No such employee exists");
			btnRemoveMore.setDisable(true);
		}
		if(less.size() == 0) {
			lblLess.setText("No such employee exists");
			btnRemoveLess.setDisable(true);
		}
	}
	
	public scanDbModel removeObject = new scanDbModel();
	
	public void removeLessAction(ActionEvent event) throws SQLException {
		alert();
		  if(option.get() == null) {
			  lblWarning.setText("Not Selected");
		  }
		  else if(option.get() == ButtonType.OK) {
			  int code = removeObject.removeLessEmployee();
			  if(code == 2292) {
				  lblLess.setText("Referential Integrity constraint violation! Could not be removed!");
				  btnRemoveLess.setDisable(true);
			  }
			  else if(code == 0){
				  lblLess.setText("Successfully removed employees!");
				  btnRemoveLess.setDisable(true);
			  }
		  }
		  else if(option.get() == ButtonType.CANCEL) {
			  lblWarning.setText("Cancelled!!");
		  }
		  else{
			  lblWarning.setText("-");
		  }
	}
	
	public void removeMoreAction(ActionEvent event) throws SQLException {
		alert();
		  if(option.get() == null) {
			  lblWarning.setText("Not Selected");
		  }
		  else if(option.get() == ButtonType.OK) {
			  int code = removeObject.removeMoreEmployee();
			  if(code == 2292) {
				  lblMore.setText(lblMore.getText()+"\nReferential Integrity constraint violation! Could not be removed!");
				  btnRemoveMore.setDisable(true);
			  }
			  else if(code == 0){
				  lblMore.setText("Successfully removed employees!");
				  btnRemoveMore.setDisable(true);
			  }
		  }
		  else if(option.get() == ButtonType.CANCEL) {
			  lblWarning.setText("Cancelled!!");
		  }
		  else{
			  lblWarning.setText("-");
		  }
	}
	
	
	
}
