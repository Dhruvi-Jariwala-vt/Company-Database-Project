package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class PrintReportController{
	PrintReportModel printReportModel = new PrintReportModel();
	public String emp_ssn;
	
	public void getSsn(String ssn) {
		emp_ssn = ssn;
	}
	
	@FXML
	private Label lblName;
	
	@FXML
	private Label lblSsn;
	
	@FXML
	private Label lblBdate;
	
	@FXML
	private Label lblAddress;
	
	@FXML
	private Label lblSex;
	
	@FXML
	private Label lblSalary;
	
	@FXML
	private Label lblMgrSsn;
	
	@FXML
	private Label lblDnumber;
	
	@FXML
	private Label lblEmail;
	
	@FXML
	private Label lblPnumber;
	
	@FXML
	private Label lblHours;
	
	@FXML
	private Label lblDept;
	
	@FXML
	private Label lblrelation;
	
	@FXML
	private Label lblStatus;
	
	@FXML
	private Label lbl1, lbl2, lbl3, lbl10, lbl20, lbl30;
	
	@FXML
	private Button btnExtraCredits;
	
	@FXML
	private Button btnDelete;
	
	@FXML
	private Button btnPrint;
	
	public Optional<ButtonType> option;
	public void alert() {
		 Alert alert = new Alert(AlertType.CONFIRMATION);
	     alert.setTitle("CASCADE ON DELETE!");
	     alert.setHeaderText("Are you sure?");
	     alert.setContentText("All the entries related to these employees will be removed!");
	     
	     option = alert.showAndWait();
	}
	
	public void printAction(ActionEvent event) {
		PrinterJob job = PrinterJob.createPrinterJob();
		Node node = (Node) event.getSource();
		if(job != null && job.showPrintDialog(node.getScene().getWindow())) {
			boolean success = job.printPage(node);
			if(success) {
				job.endJob();
			}
		}
	}
	
	public void deleteAction(ActionEvent event) {
		try {
			int status = printReportModel.deleteEmp(emp_ssn);
			if(status == -1) {
				lblStatus.setText("Deletion Successfull");
				btnDelete.setDisable(true);
			}
			else if(status > 0) {
				if(status == 2292) {
					lblStatus.setText("Integrity Constraint Violated!");
					alert();
					  if(option.get() == null) {
						  lblStatus.setText("Not Selected");
					  }
					  else if(option.get() == ButtonType.OK) {
						 status = printReportModel.deleteEverything(emp_ssn);
						 if(status > 0) {
							 lblStatus.setText("Deletion Unsuccessful");
						 }
						 else if(status == -1) {
							lblStatus.setText("Deletion Successfull");
							btnDelete.setDisable(true);
						 }
						 else {
							 lblStatus.setText("Something's wrong");
						 }
					  }
					  else if(option.get() == ButtonType.CANCEL) {
						  lblStatus.setText("Cancelled!!");
					  }
					  else{
						  lblStatus.setText("-");
					  }
				}
			}
			else {
				lblStatus.setText("Something's wrong");
			}
		}
		catch(SQLException e) {
			e.getMessage();
		}
	}
	
	public void extraCreditsAction(ActionEvent event){
		try {
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/deleteProject.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void getData() throws SQLException {
		Connection connection = null;
		connection = OracleConnection.Connector();
		if(connection == null) {
			System.out.println("Connection failed!");
			System.exit(1);
		}
		PreparedStatement p = null;
		PreparedStatement pc = null;
		ResultSet rs = null;
		ResultSet rsmanager = null;
		try {
			String managerSsn = "";
			String query = "select * from employee where ssn = ?";
			p = connection.prepareStatement(query);
			p.clearParameters();
			p.setString(1, emp_ssn);
			rs = p.executeQuery();
			while(rs.next()) {
				lblName.setText(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
				lblSsn.setText(rs.getString(4));
				lblBdate.setText(rs.getString(5));
				lblAddress.setText(rs.getString(6));;
				lblSex.setText(rs.getString(7));;
				lblSalary.setText(rs.getString(8));
				managerSsn = rs.getString(9);
				lblDnumber.setText(Integer.toString(rs.getInt(10)));
				lblEmail.setText(rs.getString(11));
			}
			
			String query3 = "select fname, lname from employee where ssn = ?";
			pc = connection.prepareStatement(query3);
			pc.clearParameters();
			pc.setString(1, managerSsn);
			rsmanager = pc.executeQuery();
			while(rsmanager.next()) {
				lblMgrSsn.setText(rsmanager.getString(1)+" "+rsmanager.getString(2));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(p != null) {
				p.close();
			}
			if(pc != null) {
				pc.close();
			}
			if(rs != null) {
				rs.close();
			}
			if(rsmanager != null) {
				rsmanager.close();
			}
		}
		
		ResultSet rsproject = null;
		PreparedStatement p1 = null;
		try {
			String query1 = "select pno, hours from works_on where essn = ?";
			p1 = connection.prepareStatement(query1);
			p1.clearParameters();
			p1.setString(1, emp_ssn);
			rsproject = p1.executeQuery();
			if(rsproject != null) {
				while(rsproject.next()) {
					if(Integer.parseInt(rsproject.getString(1)) == 1) {
						lbl1.setText(rsproject.getString(2));
					}
					else if(Integer.parseInt(rsproject.getString(1)) == 2) {
						lbl2.setText(rsproject.getString(2));
					}
					else if(Integer.parseInt(rsproject.getString(1)) == 3) {
						lbl3.setText(rsproject.getString(2));
					}
					else if(Integer.parseInt(rsproject.getString(1)) == 10) {
						lbl10.setText(rsproject.getString(2));
					}
					else if(Integer.parseInt(rsproject.getString(1)) == 20) {
						lbl20.setText(rsproject.getString(2));
					}
					else if(Integer.parseInt(rsproject.getString(1)) == 30) {
						lbl30.setText(rsproject.getString(2));
					}
					else {
						System.out.println("Not assigned!");
					}
				}
			}
			else {
				System.out.println("no projects assigned!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(p1 != null) {
				p1.close();
			}
			if(rsproject != null) {
				rsproject.close();
			}
		}
		
		
		ResultSet rsdept = null;
		PreparedStatement p2 = null;
		try {
			String query2 = "select dependent_name, relationship from dependent where essn = ?";
			p2 = connection.prepareStatement(query2);
			p2.clearParameters();
			p2.setString(1, emp_ssn);
			rsdept = p2.executeQuery();
			StringBuilder str = new StringBuilder();
			StringBuilder relation = new StringBuilder();
			if(rsdept != null) {
				while(rsdept.next()) {
					str.append(rsdept.getString(1)).append("\n");
					relation.append(rsdept.getString(2)).append("\n");
				}
				String names = str.toString();
				String relation1 = relation.toString();
				lblDept.setText(names);
				lblrelation.setText(relation1);
			}
			else {
				System.out.println("No dependents!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(p2 != null) {
				p2.close();
			}
			if(rsdept != null) {
				rsdept.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
		
	}
	
}
