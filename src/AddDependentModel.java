package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDependentModel {
	
	public int checkSuccess = 0;
	
	public int addDependent(String dependentName, String sex, Date birthDate, String relation, String ssn) throws SQLException{
		checkSuccess = 0;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		connection = OracleConnection.Connector();
		if(connection == null) System.exit(1);
		try {
			String query = "insert into dependent(ESSN, DEPENDENT_NAME, SEX, BDATE, RELATIONSHIP) values (?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.clearParameters();
			preparedStatement.setString(1, ssn);
			preparedStatement.setString(2, dependentName);
			preparedStatement.setString(3, sex);
			preparedStatement.setDate(4, birthDate);
			preparedStatement.setString(5, relation);
			checkSuccess = preparedStatement.executeUpdate();
			if(checkSuccess > 0) {
				checkSuccess = 3;
			}
		}
		catch(SQLException e) {
			checkSuccess = e.getErrorCode();
			System.out.println(e.getMessage());
		}
		finally {
			if(preparedStatement != null) {
				preparedStatement.close();	
			}
			if(connection != null) {
				connection.close();	
			}
		}
		return checkSuccess;
	}
	
}
