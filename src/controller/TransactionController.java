package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.Transaction;

public class TransactionController {
	public static ArrayList<Object[]> getTransaction(String id) throws SQLException {
		ArrayList<Object[]> transactions = new ArrayList<Object[]>();
		String sql = "select * from transaction where idUser = ? order by DATE;"; 
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = null;
		try {
			conn = db.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
			transactions.add(new Object[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)});
		conn.close();
		return transactions;
	}
	
	public static int addTransaction(Transaction transaction, String idUser) throws SQLException{
		String sql = "INSERT INTO `financial`.`transaction`\r\n" + 
				"(`title`,\r\n" + 
				"`description`,\r\n" + 
				"`amount`,\r\n" + 
				"`status`,\r\n" +
				"`date`,\r\n" + 
				"`idUser`)\r\n" + 
				"VALUES\r\n" + 
				"(?,\r\n" + 
				"?,\r\n" + 
				"?,\r\n" +
				"?,\r\n" +
				"curdate(),\r\n" + 
				"?);";
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = null;
		try {
			conn = db.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, transaction.getTitle());
		ps.setString(2, transaction.getDescription());
		ps.setDouble(3, transaction.getAmount());
		ps.setString(4, transaction.getStatus());
		ps.setString(5, idUser);
		int inserted = ps.executeUpdate();
		conn.close();
		return inserted;
	}
	
	public static int updateTransaction(Transaction transaction, String id) throws SQLException {
		String sql="UPDATE `financial`.`transaction`\r\n" + 
				"SET\r\n" + 
				"`title` = ?,\r\n" + 
				"`description` = ?,\r\n" + 
				"`amount` = ?,\r\n" +
				"`date` = ?,\r\n" + 
				"`status` = ?\r\n" + 
				"WHERE `idTransaction` = ?"
				+ "ORDER BY DATE;";
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = null;
		try {
			conn = db.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		PreparedStatement ps = conn.prepareStatement(sql);
		System.out.println(transaction.getAmount());
		ps.setString(1, transaction.getTitle());
		ps.setString(2, transaction.getDescription());
		ps.setDouble(3, transaction.getAmount());
		ps.setString(4, transaction.getDate());
		ps.setString(5, transaction.getStatus());
		ps.setString(6, transaction.getIdTransaction());
		int updated = ps.executeUpdate();
		conn.close();
		return updated;
	}
}
