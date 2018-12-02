package controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import database.MakeHttpRequest;

public class FriendController {
	public static ArrayList<Object[]> getFriends(String idUser) throws JSONException {
		ArrayList<Object[]> friends = new ArrayList<Object[]>(); 
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "getFriends"));
		params.add(new BasicNameValuePair("idUser", idUser));
		JSONArray jArr = MakeHttpRequest.makeRequest(params,"friend");
		
		for(int i = 0; i<jArr.length();i++)
			try {
				friends.add(new Object[] {	jArr.getJSONObject(i).getString("idUser"),
											jArr.getJSONObject(i).getString("username"),
											jArr.getJSONObject(i).getString("email")});
			} catch (JSONException e) {
				friends.add(new Object[] {	jArr.getJSONObject(i).getString("idUser"),
											"Friend not yet registered",
											jArr.getJSONObject(i).getString("email")});
			}
		
		return friends;
	}
	
	public static void addFriend(String idUser, String idFriend) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "addFriend"));
		params.add(new BasicNameValuePair("idUser", idUser));
		params.add(new BasicNameValuePair("idFriend", idFriend));
		MakeHttpRequest.makeRequest(params,"friend");
	}
}
