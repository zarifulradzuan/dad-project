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

public class OweController {

	public OweController() {
		// TODO Auto-generated constructor stub
		
	}
	
	
	public void addOwe(String name, String amount, String user) {
		Thread sr = new Thread(new SendRequest(name, amount, user));
		sr.start();
	}
	
	public boolean addOwed() {
		
		return false;
	}

	public class SendRequest implements Runnable {
		private String name;
		private String amount;
		private String user;
		
		public SendRequest(String name, String amount, String user) {
			this.name = name;
			this.amount = amount;
			this.user = user;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("borrowerName", user));
			params.add(new BasicNameValuePair("lenderName", name));
			params.add(new BasicNameValuePair("amount", amount));
			System.out.println(name + user + amount); 
			String strUrl = "http://localhost/Dad-project/Owe.php";
			
			
			
						
			boolean result = makeHttpRequest(strUrl, "POST", params);
			
			// if result true make a noti box
		}

		private boolean makeHttpRequest(String url, String string, List<NameValuePair> params) {
			// TODO Auto-generated method stub
			
			
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + httpPost.getEntity());
				System.out.println("Response Code : " + httpResponse.getStatusLine().getStatusCode());
				
				if(httpResponse.getStatusLine().getStatusCode() == 200) {
					return true;
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return false;
			
		}
		
	}
}
