package application;

import java.sql.*;

public class MainModel {
	
	
	public boolean isDbConnected() {
		Connection connection = null;
		connection = OracleConnection.Connector();
		if(connection == null) System.exit(1);
		try {
			return !connection.isClosed();
		}
		catch(Exception e) {
			e.getMessage();
			return false;
		}
	}
	
	public boolean checkManager(String ssn) throws SQLException {
		Connection connection = null;
		connection = OracleConnection.Connector();
		if(connection == null) System.exit(1);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "Select mgrssn from department where mgrssn = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, ssn);
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
		finally {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(resultSet != null) {
				resultSet.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
	}
}
