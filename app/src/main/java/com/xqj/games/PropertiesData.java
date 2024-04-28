package com.xqj.games;

import android.app.Activity;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Properties;

public class PropertiesData {
  public static final String CHECK_APK_URL = "CHECK_APK_URL";
  
  public static final String CHECK_RES_URL = "CHECK_RES_URL";
  
  public static final String HOST_URL = "HOST_URL";
  
  public static final String LOGIN_SERVER_IP = "LOGIN_SERVER_IP";
  
  public static final String LOGIN_SERVER_PORT = "LOGIN_SERVER_PORT";
  
  public static final String UNION_ID = "UNION_ID";
  
  private static PropertiesData mPropertiesData;
  
  public static String unionId;
  
  private Properties properties = null;
  
  public PropertiesData() {
    Activity activity = SuperTools.getInstance().getCurActivity();
    try {
      InputStream inputStream = activity.getAssets().open("config.properties");
      Properties properties = new Properties();
//      this();
      this.properties = properties;
      this.properties.load(inputStream);
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public static PropertiesData getInstance() {
    if (mPropertiesData == null)
      mPropertiesData = new PropertiesData(); 
    return mPropertiesData;
  }
  
  public static String getKey(String paramString) {
    try {
      return new String((getInstance()).properties.getProperty(paramString).getBytes("ISO-8859-1"), "UTF-8");
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      return (getInstance()).properties.getProperty(paramString);
    } 
  }
  
  public static void setString(String paramString1, String paramString2) {
    try {
      if ("UNION_ID".equals(paramString1)) {
        unionId = paramString2;
      } else {
        (getInstance()).properties.setProperty(paramString1, paramString2);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public boolean getBoolean(String paramString) {
    return Boolean.parseBoolean(getString(paramString));
  }
  
  public Integer getInteger(String paramString) {
    String str = this.properties.getProperty(paramString);
    return (paramString == null) ? null : Integer.valueOf(Integer.parseInt(str));
  }
  
  public String getString(String paramString) {
    try {
      boolean bool = "UNION_ID".equals(paramString);
      if (bool) {
        if (unionId == null) {
          String str = new String(properties.getProperty(paramString).getBytes("ISO-8859-1"), "UTF-8");
//          this(this.properties.getProperty(paramString).getBytes("ISO-8859-1"), "UTF-8");
          unionId = str;
        }
        return unionId;
      }
      return new String(properties.getProperty(paramString).getBytes("ISO-8859-1"), "UTF-8");
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      return this.properties.getProperty(paramString);
    }
  }
  
  public String getString(String paramString, Object... paramVarArgs) {
    try {
      String str = new String(properties.getProperty(paramString).getBytes("ISO-8859-1"), "UTF-8");
//      this(this.properties.getProperty(paramString).getBytes("ISO-8859-1"), "UTF-8");
      return MessageFormat.format(str, paramVarArgs);
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      return this.properties.getProperty(paramString);
    } 
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\com\xqj\games\PropertiesData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */