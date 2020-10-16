package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeeProjectModel {
	
	public int success = 0;
	public int dept_number = 0;
	
	public ObservableList<Integer> checkProjects(String ssn) throws SQLException {
		Connection connection1 = null;
		connection1 = OracleConnection.Connector();
		if(connection1 == null) System.exit(1);
		ObservableList<Integer> project_numbers = FXCollections.observableArrayList();
		PreparedStatement preparedStatement = null;
		PreparedStatement p2 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			String q1 = "select dno from employee where ssn = ?";
			preparedStatement = connection1.prepareStatement(q1);
			preparedStatement.clearParameters();
			preparedStatement.setString(1, ssn);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				dept_number = rs.getInt(1);
			}
			
			
			String q2 = "select pnumber from project where dnum = ?";
			p2 = connection1.prepareStatement(q2);
			p2.clearParameters();
			p2.setInt(1, dept_number);
			rs1 = p2.executeQuery();
			while(rs1.next()) {
				project_numbers.add(rs1.getInt(1));
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}
		finally {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(p2 != null) {
				p2.close();
			}
			if(rs != null) {
				rs.close();
			}
			if(rs1 != null) {
				rs1.close();
			}
			if(connection1 != null) {
				connection1.close();
			}
		}
		return project_numbers;
	}
	
	public int addProject(ObservableList<Integer> projectNumbers, ObservableList<Integer> projectHours, String ssn) throws SQLException {
		success = 0;
		List<Integer> project = new ArrayList<Integer>();
		List<Integer> hours = new ArrayList<Integer>();
		PreparedStatement preparedStatement = null;
		Connection connection2 = null;
		connection2 = OracleConnection.Connector();
		if(connection2 == null) System.exit(1);
		for(int i = 0; i<projectNumbers.size(); i++) {
			project.add(projectNumbers.get(i));
		}
		for(int i = 0; i<projectHours.size(); i++) {
			hours.add(projectHours.get(i));
		}
		
		try {
			for(int i = 0; i<project.size(); i++) {
				String query = "insert into works_on(ESSN, PNO, HOURS) values (?,?,?)";
				preparedStatement = connection2.prepareStatement(query);
				preparedStatement.clearParameters();
				preparedStatement.setString(1, ssn);
				preparedStatement.setInt(2, project.get(i));
				preparedStatement.setInt(3, hours.get(i));
				success = preparedStatement.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.getMessage();
		}
		finally {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection2 != null) {
				connection2.close();	
			}
		}
		
		return success;
	}
	
	public void initialProjectLoad() throws SQLException {
		loadOne();
		loadTwo();
		loadThree();
		loadFour();
		loadFive();
		loadSix();
	}
	
	public void loadOne() throws SQLException {
		Connection connection = null;
		connection = OracleConnection.Connector();
		if(connection == null) System.exit(1);
		PreparedStatement p1 = null;
		try {
			String q1 = "INSERT INTO project VALUES ('ProductX', 1, 'Bellaire',  5)";
			p1 = connection.prepareStatement(q1);
			p1.clearParameters();
			p1.executeUpdate();
		}
		catch(SQLException e) {
			e.getMessage();
		}
		finally {
			if(connection != null) {
				connection.close();
			}
			if(p1 != null) {
				p1.close();
			}
		}
	}
	
	public void loadTwo() throws SQLException {
		Connection connection = null;
		connection = OracleConnection.Connector();
		if(connection == null) System.exit(1);
		PreparedStatement p2 = null;
		try {
			String q2 = "INSERT INTO project VALUES ('ProductY', 2, 'Sugarland', 5)";
			p2 = connection.prepareStatement(q2);
			p2.clearParameters();
			p2.executeUpdate();
		}
		catch(SQLException e) {
			e.getMessage();
		}
		finally {
			if(connection != null) {
				connection.close();
			}
			if(p2 != null) {
				p2.close();
			}
		}
	}
	
	public void loadThree() throws SQLException {
		Connection connection = null;
		connection = OracleConnection.Connector();
		if(connection == null) System.exit(1);
		PreparedStatement p3 = null;
		try {
			String q3 = "INSERT INTO project VALUES ('ProductZ', 3, 'Houston', 5)";
			p3 = connection.prepareStatement(q3);
			p3.clearParameters();
			p3.executeUpdate();
		}
		catch(SQLException e) {
			e.getMessage();
		}
		finally {
			if(connection != null) {
				connection.close();
			}
			if(p3 != null) {
				p3.close();
			}
		}
	}
	
	public void loadFour() throws SQLException {
		Connection connection = null;
		connection = OracleConnection.Connector();
		if(connection == null) System.exit(1);
		PreparedStatement p4 = null;
		try {
			String q4 = "INSERT INTO project VALUES ('Computerization', 10, 'Stafford', 4)";
			p4 = connection.prepareStatement(q4);
			p4.clearParameters();
			p4.executeUpdate();
		}
		catch(SQLException e) {
			e.getMessage();
		}
		finally {
			if(connection != null) {
				connection.close();
			}
			if(p4 != null) {
				p4.close();
			}
		}
	}
	
	public void loadFive() throws SQLException {
		Connection connection = null;
		connection = OracleConnection.Connector();
		if(connection == null) System.exit(1);
		PreparedStatement p5 = null;
		try {
			String q5 = "INSERT INTO project VALUES ('Reorganization', 20, 'Houston', 1)";
			p5 = connection.prepareStatement(q5);
			p5.clearParameters();
			p5.executeUpdate();
		}
		catch(SQLException e) {
			e.getMessage();
		}
		finally {
			if(connection != null) {
				connection.close();
			}
			if(p5 != null) {
				p5.close();
			}
		}
	}
	
	public void loadSix() throws SQLException {
		Connection connection = null;
		connection = OracleConnection.Connector();
		if(connection == null) System.exit(1);
		PreparedStatement p6 = null;
		try {
			String q6 = "INSERT INTO project VALUES ('Newbenefits', 30,  'Stafford', 4)";
			p6 = connection.prepareStatement(q6);
			p6.clearParameters();
			p6.executeUpdate();
		}
		catch(SQLException e) {
			e.getMessage();
		}
		finally {
			if(connection != null) {
				connection.close();
			}
			if(p6 != null) {
				p6.close();
			}
		}
	}
}









































