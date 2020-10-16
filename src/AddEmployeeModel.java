package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddEmployeeModel {
	
	public int invalidInput = 0;
	public int mgrCheck = 0;
	
	public int addEmployee(String firstName, String middleInitial, String lastName, String ssn, Date birthDate, String address, String sex, long salary, String superSsn, int dnumber, String email) throws SQLException{
		PreparedStatement preparedStatement = null;
		Connection connection1 = null;
		connection1 = OracleConnection.Connector();
		if(connection1 == null) System.exit(1);
		try {
			invalidInput = 0;
			int result = 0;
			String query = "insert into employee(FNAME, MINIT, LNAME, SSN, BDATE, ADDRESS, SEX, SALARY, SUPERSSN, DNO, EMAIL) values (?,?,?,?,?,?,?,?,?,?,?)";
			preparedStatement = connection1.prepareStatement(query);
			preparedStatement.clearParameters();
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, middleInitial);
			preparedStatement.setString(3, lastName);
			preparedStatement.setString(4, ssn);
			preparedStatement.setDate(5, birthDate);
			preparedStatement.setString(6, address);
			preparedStatement.setString(7, sex);
			preparedStatement.setLong(8, salary);
			preparedStatement.setString(9, superSsn);
			preparedStatement.setInt(10, dnumber);
			preparedStatement.setString(11, email);
			result = preparedStatement.executeUpdate();
			if(result > 0) {
				invalidInput = -9;
			}
		}
		catch(SQLException e) {
			e.getMessage();
			invalidInput = e.getErrorCode();
		}
		finally {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection1 != null) {
				connection1.close();	
			}
		}
		return invalidInput;
	}
	
	public int checkManager(String superSsn) throws SQLException {
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		Connection connection2 = null;
		connection2 = OracleConnection.Connector();
		if(connection2 == null) System.exit(1);
		try {
			String query = "select mgrssn from department";
			preparedStatement = connection2.prepareStatement(query);
			preparedStatement.clearParameters();
			rs = preparedStatement.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					if(rs.getString(1).equals(superSsn)) {
						mgrCheck = 1;
					}
				}
			}
		}
		catch(SQLException e) {
			e.getMessage();
		}
		finally {
			if(rs != null) {
				rs.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection2 != null) {
				connection2.close();	
			}
		}
		return mgrCheck;
	}
	
}
