package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.collections.ObservableList;

public class deleteProjectModel {
	Connection connection = null;
	Connection connection1 = null;
	Connection connection2 = null;
	
	public int removeProject(ObservableList<Integer> pnumber) throws SQLException {
		int status = 0;
		PreparedStatement p = null;
		connection = OracleConnection.Connector();
		if(connection == null) System.exit(1);
		for(int i = 0; i<pnumber.size(); i++) {
			try {
				String query = "delete from project where pnumber = ?";
				p = connection.prepareStatement(query);
				p.clearParameters();
				p.setInt(1, pnumber.get(i));
				status = p.executeUpdate();
				if(status > 0) {
					status = -3;
				}
			}
			catch(SQLException e) {
				status = e.getErrorCode();
				e.getMessage();
			}
			finally {
				if(p != null) {
					p.close();
				}
				if(connection != null) {
					connection.close();
				}
			}
		}
		return status;
	}
}
