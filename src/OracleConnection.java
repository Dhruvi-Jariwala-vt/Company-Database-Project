package application;

import java.sql.*;

public class OracleConnection {
	
	private static String connStr = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
	
	public static Connection Connector() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(connStr,"djariwal","adefoalr");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
