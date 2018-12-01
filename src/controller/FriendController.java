package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Friend;

public class FriendController {
	public static ArrayList<Friend> getFriends(String idUser) throws SQLException, ClassNotFoundException {
		ArrayList<Friend> users = new ArrayList<Friend>();
		String sql = "SELECT B.username, B.email\r\n" + 
				"FROM\r\n" + 
				"	friend\r\n" + 
				"	LEFT JOIN user A ON A.idUser = friend.idUser\r\n" + 
				"    LEFT JOIN user B ON B.idUser = friend.idfriend\r\n" + 
				"WHERE A.idUser=?;"; 
		return users;
	}
}
