package com.xqj.games;

import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class SdkFunc {
  public static String baseUrl = "http://43.136.63.204:30008/pay/xqh5order/xq?rmb=%s&name=%s&payment=%s&sid=%s&md5=%s";
  
  public static void main(String[] paramArrayOfString) {}
  
  public static void pay(String paramString) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    String[] arrayOfString = paramString.split("\\?")[1].split("&");
    int i = arrayOfString.length;
    for (byte b = 0; b < i; b++) {
      String[] arrayOfString1 = arrayOfString[b].split("=");
      hashMap.put(arrayOfString1[0], arrayOfString1[1]);
    } 
    System.out.println("callpay");
    String str4 = (String)hashMap.get("pmoney");
    String str3 = (String)hashMap.get("uname");
    String str1 = (String)hashMap.get("payment");
    String str5 = (String)hashMap.get("serverId");
    String str2 = (String)hashMap.get("md5");
    str1 = String.format(baseUrl, new Object[] { str4, str3, str1, str5, str2 });
    JSONObject jSONObject = SuperTools.getInstance().postHttpJson(str1);
    try {
      if (jSONObject.getInt("rt") == 1)
        SuperTools.getInstance().openURL(jSONObject.getString("url")); 
    } catch (JSONException jSONException) {
      jSONException.printStackTrace();
    } 
  }
  
  public static void sdklogin() {}
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\com\xqj\games\SdkFunc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */