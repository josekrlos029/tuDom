package com.dataParser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.models.Parametro;

import android.util.Log;

public class JSONParser {
	  static InputStream is = null;
	  static JSONArray jObj = null;
	  static JSONObject jObj2 = null;
	  static String json = "";
	  // constructor
	  public JSONParser() {
	  }
	  public JSONArray  getJSONFromUrl(String url) {
	    // Making HTTP request
	    try {
	      // defaultHttpClient
	      DefaultHttpClient httpClient = new DefaultHttpClient();
	      HttpPost httpPost = new HttpPost(url);
	      HttpResponse httpResponse = httpClient.execute(httpPost);
	      HttpEntity httpEntity = httpResponse.getEntity();
	      is = httpEntity.getContent();
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    try {
	      BufferedReader reader = new BufferedReader(new InputStreamReader(
	          is, "UTF-8"), 8);
	      StringBuilder sb = new StringBuilder();
	      String line = null;
	      String NL = System.getProperty("line.separator");
	      while ((line = reader.readLine()) != null) {
	        sb.append(line);
	      }
	      
	      json = sb.toString();
	      
	      jObj = new JSONArray(json);
	      is.close();
	    } catch (Exception e) {
	      Log.e("Buffer Error", "Error converting result " + e.toString());
	    }
	    return jObj;
	    
	  }
	  
	  public JSONObject  getJSONFromUrl(String url, List<NameValuePair> nameValuePairs) {
		    // Making HTTP request
		    try {
		      // defaultHttpClient
		      DefaultHttpClient httpClient = new DefaultHttpClient();
		      HttpPost httpPost = new HttpPost(url);
		      httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		      HttpResponse httpResponse = httpClient.execute(httpPost);
		      HttpEntity httpEntity = httpResponse.getEntity();
		      is = httpEntity.getContent();
		    } catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
		    } catch (ClientProtocolException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    try {
		      BufferedReader reader = new BufferedReader(new InputStreamReader(
		          is, "UTF-8"), 8);
		      StringBuilder sb = new StringBuilder();
		      String line = null;
		      String NL = System.getProperty("line.separator");
		      while ((line = reader.readLine()) != null) {
		        sb.append(line);
		      }
		      
		      json = sb.toString();
		      
		      jObj2 = new JSONObject(json);
		      is.close();
		    } catch (Exception e) {
		      Log.e("Buffer Error", "Error converting result " + e.toString());
		    }
		    return jObj2;
		    
		  }
	  
	  public JSONArray  getJSONArrayFromUrl(String url, List<NameValuePair> nameValuePairs) {
		    // Making HTTP request
		    try {
		      // defaultHttpClient
		      DefaultHttpClient httpClient = new DefaultHttpClient();
		      HttpPost httpPost = new HttpPost(url);
		      httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		      HttpResponse httpResponse = httpClient.execute(httpPost);
		      HttpEntity httpEntity = httpResponse.getEntity();
		      is = httpEntity.getContent();
		    } catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
		    } catch (ClientProtocolException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    try {
		      BufferedReader reader = new BufferedReader(new InputStreamReader(
		          is, "UTF-8"), 8);
		      StringBuilder sb = new StringBuilder();
		      String line = null;
		      String NL = System.getProperty("line.separator");
		      while ((line = reader.readLine()) != null) {
		        sb.append(line);
		      }
		      
		      json = sb.toString();
		      
		      jObj = new JSONArray(json);
		      is.close();
		    } catch (Exception e) {
		      Log.e("Buffer Error", "Error converting result " + e.toString());
		    }
		    return jObj;
		    
		  }
	  
	
}
