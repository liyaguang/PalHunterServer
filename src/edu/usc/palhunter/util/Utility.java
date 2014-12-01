package edu.usc.palhunter.util;

import java.io.InputStream;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

  public static String streamToString(InputStream is) {
    Scanner s = new Scanner(is).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }

  public static class RequestHelper {
    HttpServletRequest request = null;

    public RequestHelper(HttpServletRequest request) {
      this.request = request;
    }

    public String getString(String key) {
      String str = request.getParameter(key);
      if (str != null) {
        str = str.trim();
      } else {
        str = "";
      }
      return str;
    }

    public int getInt(String key) {
      int result = Integer.parseInt(getString(key));
      return result;
    }

    public double getDouble(String key) {
      double result = Double.parseDouble(getString(key));
      return result;
    }

    public JSONObject getJSONObject(String key) {
      JSONObject result = new JSONObject();
      try {
        result = new JSONObject(getString(key));
      } catch (JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      return result;
    }

    public JSONArray getJSONArray(String key) {
      JSONArray result = new JSONArray();
      try {
        result = new JSONArray(getString(key));
      } catch (JSONException exc) {
        exc.printStackTrace();
      }
      return result;
    }
  }
}