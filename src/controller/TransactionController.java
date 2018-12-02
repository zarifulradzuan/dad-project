package controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import database.MakeHttpRequest;
import model.Transaction;

public class TransactionController {
	public static ArrayList<Object[]> getTransaction(String id) throws JSONException {
		ArrayList<Object[]> transactions = new ArrayList<Object[]>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "getTransaction"));
		params.add(new BasicNameValuePair("idUser", id));
		JSONArray jArr = MakeHttpRequest.makeRequest(params,"transaction");
		for(int i = 0; i<jArr.length();i++)
			try {
				transactions.add(new Object[] {	jArr.getJSONObject(i).getString("idTransaction"),
												jArr.getJSONObject(i).getString("title"),
												jArr.getJSONObject(i).getString("description"),
												String.format("%.2f", jArr.getJSONObject(i).getDouble("amount")),
												jArr.getJSONObject(i).getString("date"),
												jArr.getJSONObject(i).getString("status")});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return transactions;
	}
	
	public static void addTransaction(Transaction transaction, String idUser) throws JSONException{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "addTransaction"));
		params.add(new BasicNameValuePair("idUser", idUser));
		params.add(new BasicNameValuePair("title", transaction.getTitle()));
		params.add(new BasicNameValuePair("description", transaction.getDescription()));
		params.add(new BasicNameValuePair("amount", String.valueOf(transaction.getAmount())));
		params.add(new BasicNameValuePair("status", transaction.getStatus()));
		
		MakeHttpRequest.makeRequest(params, "transaction");
	}
	
	public static void removeTransaction(String id) throws JSONException  {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "deleteTransaction"));
		params.add(new BasicNameValuePair("idTransaction", id));
		MakeHttpRequest.makeRequest(params, "transaction");
	}
	
	public static void updateTransaction(Transaction transaction) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "updateTransaction"));
		params.add(new BasicNameValuePair("idTransaction", transaction.getIdTransaction()));
		params.add(new BasicNameValuePair("title", transaction.getTitle()));
		params.add(new BasicNameValuePair("description", transaction.getDescription()));
		params.add(new BasicNameValuePair("amount", String.valueOf(transaction.getAmount())));
		params.add(new BasicNameValuePair("status", transaction.getStatus()));
		params.add(new BasicNameValuePair("date", transaction.getDate()));
		MakeHttpRequest.makeRequest(params, "transaction");
	}
}
