package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class scanDbModel {
	
	public ObservableList<String> Moressn = FXCollections.observableArrayList();
	public ObservableList<String> Lessssn = FXCollections.observableArrayList();
	
	public ObservableList<String> getMoreData() throws SQLException {
		
		Connection connection;
		connection = OracleConnection.Connector();
		if(connection == null) System.exit(1);
		
		PreparedStatement p = null;
		PreparedStatement p1 = null;
		PreparedStatement p2 = null;
		PreparedStatement p3 = null;
		ResultSet rs = null;
		ResultSet r1 = null;
		ResultSet r2 = null;
		ResultSet r3 = null;
		ObservableList<String> ssn = FXCollections.observableArrayList();
		ObservableList<Integer> dno = FXCollections.observableArrayList();
		ObservableList<Integer> projects = FXCollections.observableArrayList();
		ObservableList<Integer> projectNo = FXCollections.observableArrayList();
		ObservableList<String> moreThan = FXCollections.observableArrayList();
		ObservableList<String> moreNames = FXCollections.observableArrayList();
		
		try {
			String query = "select ssn, dno from employee order by dno";
			p = connection.prepareStatement(query);
			p.clearParameters();
			rs = p.executeQuery();
			int count = 0;
			while(rs.next()) {
				projectNo.clear();
				projects.clear();
				ssn.add(rs.getString(1));
				dno.add(rs.getInt(2));
				String q1 = "select pno from works_on where essn = ?";
				p1 = connection.prepareStatement(q1);
				p1.clearParameters();
				p1.setString(1, rs.getString(1));
				r1 = p1.executeQuery();
				while(r1.next()) {
					projects.add(r1.getInt(1));
				}
				
				String q2 = "select pnumber from project where dnum = ?";
				p2 = connection.prepareStatement(q2);
				p2.clearParameters();
				p2.setInt(1, rs.getInt(2));
				r2 = p2.executeQuery();
				while(r2.next()) {
					projectNo.add(r2.getInt(1));
				}
				
				count = 0;
				for(int i = 0; i<projects.size(); i++) {
					for(int j = 0; j<projectNo.size(); j++) {
						if(projects.get(i) == projectNo.get(j)) {
							count++;
						}
						else {
							continue;
						}
					}
				}
				if(count > 2) {
					TextField txt = new TextField();
					txt.setText(rs.getString(1));
					moreThan.add(rs.getString(1));
				}
			}
			
			Moressn = moreThan;
			
			for(int i = 0; i<moreThan.size(); i++) {
				String q3 = "select fname, minit, lname from employee where ssn = ?";
				p3 = connection.prepareStatement(q3);
				p3.clearParameters();
				p3.setString(1, moreThan.get(i));
				r3 = p3.executeQuery();
				StringBuilder strName = new StringBuilder();
				while(r3.next()) {
					strName.append(r3.getString(1)+" "+r3.getString(2)+" "+r3.getString(3));
					moreNames.add(strName.toString());
				}
			}	
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(p != null) {
				p.close();
			}
			if(p1 != null) {
				p1.close();
			}
			if(p2 != null) {
				p2.close();
			}
			if(p3 != null) {
				p3.close();
			}
			if(rs != null) {
				rs.close();
			}
			if(r1 != null) {
				r1.close();
			}
			if(r2 != null) {
				r2.close();
			}
			if(r3 != null) {
				r3.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
		
	return moreNames;
}
	public ObservableList<String> getLessData() throws SQLException {
		Connection connection;
		connection = OracleConnection.Connector();
		if(connection == null) System.exit(1);
		
		PreparedStatement p = null;
		PreparedStatement p1 = null;
		PreparedStatement p2 = null;
		PreparedStatement p3 = null;
		ResultSet rs = null;
		ResultSet r1 = null;
		ResultSet r2 = null;
		ResultSet r3 = null;
		
		ObservableList<String> ssn = FXCollections.observableArrayList();
		ObservableList<Integer> dno = FXCollections.observableArrayList();
		ObservableList<Integer> projects = FXCollections.observableArrayList();
		ObservableList<Integer> projectNo = FXCollections.observableArrayList();
		ObservableList<String> lessThan = FXCollections.observableArrayList();
		ObservableList<String> lessNames = FXCollections.observableArrayList();
		
		try {
			String query = "select ssn, dno from employee order by dno";
			p = connection.prepareStatement(query);
			p.clearParameters();
			rs = p.executeQuery();
			int count = 0;
			while(rs.next()) {
				projectNo.clear();
				projects.clear();
				ssn.add(rs.getString(1));
				dno.add(rs.getInt(2));
				String q1 = "select pno from works_on where essn = ?";
				p1 = connection.prepareStatement(q1);
				p1.clearParameters();
				p1.setString(1, rs.getString(1));
				r1 = p1.executeQuery();
				while(r1.next()) {
					projects.add(r1.getInt(1));
				}
				
				String q2 = "select pnumber from project where dnum = ?";
				p2 = connection.prepareStatement(q2);
				p2.clearParameters();
				p2.setInt(1, rs.getInt(2));
				r2 = p2.executeQuery();
				while(r2.next()) {
					projectNo.add(r2.getInt(1));
				}
				
				count = 0;
				for(int i = 0; i<projects.size(); i++) {
					for(int j = 0; j<projectNo.size(); j++) {
						if(projects.get(i) == projectNo.get(j)) {
							count++;
						}
						else {
							continue;
						}
					}
				}
				if(count < 1) {
					lessThan.add(rs.getString(1));
				}
			}
			Lessssn = lessThan;
			
			for(int i = 0; i<lessThan.size(); i++) {
				String q3 = "select fname, minit, lname from employee where ssn = ?";
				p3 = connection.prepareStatement(q3);
				p3.clearParameters();
				p3.setString(1, lessThan.get(i));
				r3 = p3.executeQuery();
				StringBuilder strName = new StringBuilder();
				while(r3.next()) {
					strName.append(r3.getString(1)+" "+r3.getString(2)+" "+r3.getString(3));
					lessNames.add(strName.toString());
				}
			}
		}
		catch(SQLException e) {
			e.getMessage();
		}
		finally {
			if(p != null) {
				p.close();
			}
			if(p1 != null) {
				p1.close();
			}
			if(p2 != null) {
				p2.close();
			}
			if(p3 != null) {
				p3.close();
			}
			if(rs != null) {
				rs.close();
			}
			if(r1 != null) {
				r1.close();
			}
			if(r2 != null) {
				r2.close();
			}
			if(r3 != null) {
				r3.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
		return lessNames;
	}

	public int removeMoreEmployee() throws SQLException {
		int code = 0;
		Connection connection = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		try {
			connection = OracleConnection.Connector();
			if(connection == null) System.exit(1);
			getMoreData();
			for(int i = 0; i<Moressn.size(); i++) 
			{
				String query1 = "delete from dependent where essn = ?";
				preparedStatement1 = connection.prepareStatement(query1);
				preparedStatement1.clearParameters();
				preparedStatement1.setString(1, Moressn.get(i));
				int dependent = preparedStatement1.executeUpdate();
				if(dependent < 0) {
					System.out.println("Error removing!");
				}
				
				String query2 = "delete from works_on where essn = ?";
				preparedStatement2 = connection.prepareStatement(query2);
				preparedStatement2.clearParameters();
				preparedStatement2.setString(1, Moressn.get(i));
				int worksOn = preparedStatement2.executeUpdate();
				if(worksOn < 0) {
					System.out.println("Error removing");
				}
				
				String query3 = "delete from employee where ssn = ?";
				preparedStatement3 = connection.prepareStatement(query3);
				preparedStatement3.clearParameters();
				preparedStatement3.setString(1, Moressn.get(i));
				int emp = preparedStatement3.executeUpdate();
				if(emp < 0) {
					System.out.println("Error removing!");
				}
			}
		}
		catch(SQLException e) {
			if (e.getErrorCode() == 2292) {
				code = e.getErrorCode();
			}
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
	return code;
}

	public int removeLessEmployee() throws SQLException{
		int code = 0;
		Connection connection = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		try {
			connection = OracleConnection.Connector();
			if(connection == null) System.exit(1);
			getLessData();
			for(int i = 0; i<Lessssn.size(); i++)
			{	
				String query1 = "delete from dependent where essn = ?";
				preparedStatement1 = connection.prepareStatement(query1);
				preparedStatement1.clearParameters();
				preparedStatement1.setString(1, Lessssn.get(i));
				int dependent = preparedStatement1.executeUpdate();
				if(dependent < 0) {
					System.out.println("Error removing!");
				}
				
				String query2 = "delete from works_on where essn = ?";
				preparedStatement2 = connection.prepareStatement(query2);
				preparedStatement2.clearParameters();
				preparedStatement2.setString(1, Lessssn.get(i));
				int worksOn = preparedStatement2.executeUpdate();
				if(worksOn < 0) {
					System.out.println("Error removing");
				}
				
				String query3 = "delete from employee where ssn = ?";
				preparedStatement3 = connection.prepareStatement(query3);
				preparedStatement3.clearParameters();
				preparedStatement3.setString(1, Lessssn.get(i));
				int emp = preparedStatement3.executeUpdate();
				if(emp < 0) {
					System.out.println("Error removing!");
				}
				
			}
		}
		catch(SQLException e) {
			int errorCode = e.getErrorCode();
			if (errorCode == 2292) {
				code = errorCode;
			}
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
		return code;
	}
}