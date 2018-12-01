package controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.MakeHttpRequest;
import model.User;

public class UserController {
	public static User searchUser(String username, char[] passwordChar) throws Exception {
		String password = "";
		for(char a: passwordChar)
			password+=a;
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "searchUser"));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		JSONArray jArr = MakeHttpRequest.makeRequest(params, "user");
		if(jArr.length()!=0) {
			JSONObject user = jArr.getJSONObject(0);
			return new User(user.getString("idUser"),user.getString("username"),user.getString("password"), user.getString("email"));
		}
		else 
			return null;
	}
	
	public static boolean checkValid(String parameter,String toCheck) throws JSONException{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "checkValid"));
		if(parameter.equals("username"))
			params.add(new BasicNameValuePair("username", toCheck));
		else
			params.add(new BasicNameValuePair("email", toCheck));
		JSONArray jArr = MakeHttpRequest.makeRequest(params, "user");
		if(jArr.length()==0)
			return false;
		else
			return true;
	}
	
	public static void registerUser(String username, String password, String email) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "registerUser"));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("email", email));
		MakeHttpRequest.makeRequest(params, "user");
	}
	
	public static double getBalance(String id) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "getBalance"));
		params.add(new BasicNameValuePair("idUser", id));
		JSONArray jArr = MakeHttpRequest.makeRequest(params, "transaction");
		if(jArr.length()==0)
			return 0;
		double amount=0;
		try {
			amount=jArr.getJSONObject(0).getDouble("CREDITDEBIT");
			amount-=jArr.getJSONObject(1).getDouble("CREDITDEBIT");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return amount;
	}
}

