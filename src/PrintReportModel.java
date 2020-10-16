package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class PrintReportModel {
	public int status = 0;
	public int deleteEmp(String ssn) throws SQLException {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		connection = OracleConnection.Connector();
		if(connection == null) System.exit(1);
		status = 0;
		try {
			String query = "delete from employee where ssn = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.clearParameters();
			preparedStatement.setString(1, ssn);
			status = preparedStatement.executeUpdate();
			if(status > 0) {
				status = -1;
			}
		}
		catch(SQLException e)
		{	
			status = e.getErrorCode();
			e.getMessage();
		}
		finally {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
		return status;
	}
	
	public int deleteEverything(String ssn) throws SQLException {
		status = 0;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		Connection connection = null;
		connection = OracleConnection.Connector();
		if(connection == null) System.exit(1);
		int status = 0;
		try {
			String query1 = "delete from dependent where essn = ?";
			preparedStatement1 = connection.prepareStatement(query1);
			preparedStatement1.clearParameters();
			preparedStatement1.setString(1, ssn);
			int dependent = preparedStatement1.executeUpdate();
			if(dependent < 0) {
				System.out.println("Error removing!");
			}
			else {
				status = -1;
			}
			
			String query2 = "delete from works_on where essn = ?";
			preparedStatement2 = connection.prepareStatement(query2);
			preparedStatement2.clearParameters();
			preparedStatement2.setString(1, ssn);
			int worksOn = preparedStatement2.executeUpdate();
			if(worksOn < 0) {
				System.out.println("Error removing");
			}
			else {
				status = -1;
			}
			
			String query3 = "delete from employee where ssn = ?";
			preparedStatement3 = connection.prepareStatement(query3);
			preparedStatement3.clearParameters();
			preparedStatement3.setString(1, ssn);
			int emp = preparedStatement3.executeUpdate();
			if(emp < 0) {
				System.out.println("Error removing!");
			}
			else {
				status = -1;
			}
		}
		catch(SQLException e)
		{	
			status = e.getErrorCode();
			e.printStackTrace();
			e.getMessage();
		}
		finally {
			if(preparedStatement1 != null) {
				preparedStatement1.close();
			}
			if(preparedStatement2 != null) {
				preparedStatement2.close();
			}
			if(preparedStatement3 != null) {
				preparedStatement3.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
		return status;
	}
}
