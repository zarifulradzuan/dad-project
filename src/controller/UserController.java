package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DatabaseConnection;
import javafx.util.Pair;
import model.User;

public class UserController {
	public static double getBalance(String id) throws SQLException {
		String sql="SELECT sum(amount) AS CREDITDEBIT from transaction where transaction.status = 'Credit'\r\n" + 
				"AND transaction.idUser=?\r\n" + 
				"UNION\r\n" + 
				"SELECT sum(amount) from transaction where transaction.status = 'Debit'\r\n" + 
				"AND transaction.idUser=?\r\n" + 
				";";
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = null;
		try {
			conn = db.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, id);
		ResultSet rs = ps.executeQuery();
		double amount=0;
		rs.next();
		amount=rs.getDouble(1);
		rs.next();
		amount-=rs.getDouble(1);
		conn.close();
		return amount;
	}
	
	public ArrayList<String> getUserList(){
		ArrayList<String> userList = new ArrayList<String>();
		String sql = "SELECT username FROM user";
		
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = null;
		
		try {
			
			conn = db.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			
			while(rs.next()) {
			userList.add(rs.getString(1));
			}
			
			conn.close();
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
			System.out.println("error");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			System.out.println("error2");
		}
		
		
		
		return userList;
		
	}
}

