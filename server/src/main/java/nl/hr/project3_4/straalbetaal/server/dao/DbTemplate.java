package nl.hr.project3_4.straalbetaal.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DbTemplate {

	protected Connection getConnection() {
		Connection con = null;
		String host = "jdbc:mysql://localhost:3306/straalbetaal";
		String uName = "root";
		String uPass = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(host, uName, uPass);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Error in the DbTemplate - getConnection!");
		}

		return con;
	}

	protected void closeResources(Connection con, Statement stmt, ResultSet rs) {
		try {
			if(con != null)
				con.close();
			if(stmt != null)
				stmt.close();
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in the DbTemplate - closeResources!");
		}
	}
	protected void closeResources(Connection con, PreparedStatement stmt, ResultSet rs) {
		try {
			if(con != null)
				con.close();
			if(stmt != null)
				stmt.close();
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in the DbTemplate - closeResources!");			
		}
	}

}
