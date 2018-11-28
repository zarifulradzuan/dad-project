package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.Friend;
import model.User;

public class FriendController {
	public static ArrayList<Friend> getFriends(String idUser) throws SQLException, ClassNotFoundException {
		ArrayList<Friend> users = new ArrayList<Friend>();
		String sql = "SELECT B.username, B.email\r\n" + 
				"FROM\r\n" + 
				"	friend\r\n" + 
				"	LEFT JOIN user A ON A.idUser = friend.idUser\r\n" + 
				"    LEFT JOIN user B ON B.idUser = friend.idfriend\r\n" + 
				"WHERE A.idUser=?;"; 
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, idUser);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
			users.add(new Friend(rs.getString(1),rs.getString(2)));
		conn.close();
		return users;
	}
}
