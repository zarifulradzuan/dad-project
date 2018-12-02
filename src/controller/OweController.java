package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import database.MakeHttpRequest;

public class OweController {	
	public static void addRecord(String borrower, String lender, String amount, String detail) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "addRecord"));
		params.add(new BasicNameValuePair("idBorrower", borrower));
		params.add(new BasicNameValuePair("idLender", lender));
		params.add(new BasicNameValuePair("amount", amount));
		params.add(new BasicNameValuePair("detail", detail));
		MakeHttpRequest.makeRequest(params, "owe");
	}
	
	public static void deleteRecord(String idOwe) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "deleteRecord"));
		params.add(new BasicNameValuePair("idOwe", idOwe));
		MakeHttpRequest.makeRequest(params, "owe");
	}
	
	public static ArrayList<Object[]> getRecord(String idUser, String function) throws JSONException {
		ArrayList<Object[]> record = new ArrayList<Object[]>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", function));
		params.add(new BasicNameValuePair("idUser", idUser));
		JSONArray jArr = MakeHttpRequest.makeRequest(params, "owe");
		if(jArr.length()!=0)
			for(int i = 0; i<jArr.length();i++)
				record.add(new Object[] {	jArr.getJSONObject(i).getInt("idOwe"),
										jArr.getJSONObject(i).getInt("idOtherParty"),
										jArr.getJSONObject(i).getString("detail"),
										jArr.getJSONObject(i).getString("status"),
										jArr.getJSONObject(i).getDouble("amount"),
										jArr.getJSONObject(i).getString("date")});
		return record;
	}
	
	public static void updateRecord(String idOwe, String idOtherParty,String detail, String status, double amount, String date, String function) throws JSONException {
		ArrayList<Object[]> record = new ArrayList<Object[]>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", function));
		params.add(new BasicNameValuePair("idOwe", idOwe));
		params.add(new BasicNameValuePair("idOtherParty", idOtherParty));
		params.add(new BasicNameValuePair("detail", detail));
		params.add(new BasicNameValuePair("status", status));
		params.add(new BasicNameValuePair("amount", String.valueOf(amount)));
		params.add(new BasicNameValuePair("date", date));
		
		MakeHttpRequest.makeRequest(params, "owe");
	}
}
