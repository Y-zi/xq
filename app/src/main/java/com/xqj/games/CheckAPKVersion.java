package com.xqj.games;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckAPKVersion implements Runnable, RMConstDefine {
  public static String url = "";
  
  private Activity mActivity = null;
  
  private OnMyCallBack mOnMyCallBack = null;
  
  public CheckAPKVersion(Activity paramActivity, OnMyCallBack paramOnMyCallBack) {
    this.mActivity = paramActivity;
    this.mOnMyCallBack = paramOnMyCallBack;
  }
  
  private void check() {
    String str3 = PropertiesData.getInstance().getString("HOST_URL");
    String str1 = SuperTools.getInstance().getVersionCode();
    String str2 = PropertiesData.getInstance().getString("UNION_ID");
    str2 = PropertiesData.getInstance().getString("CHECK_APK_URL", new Object[] { str3, str1, str2 });
    int k = -1;
    int i = k;
    int j = k;
    try {
      Log.d("apkurl", str2);
      i = k;
      j = k;
      HttpGet httpGet = new HttpGet(str2);
      i = k;
      j = k;
//      this(str2);
      i = k;
      j = k;
      DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
      i = k;
      j = k;
//      this();
      i = k;
      j = k;
      HttpResponse httpResponse = defaultHttpClient.execute((HttpUriRequest)httpGet);
      i = k;
      j = k;
      k = httpResponse.getStatusLine().getStatusCode();
      i = k;
      if (k == 200) {
        i = k;
        j = k;
        InputStream inputStream = httpResponse.getEntity().getContent();
        i = k;
        j = k;
        try {
          parse(inputStream);
          return;
        } catch (Exception exception) {
          i = k;
          j = k;
          exception.printStackTrace();
          i = k;
        } 
      } 
    } catch (ClientProtocolException clientProtocolException) {
      clientProtocolException.printStackTrace();
      i = j;
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    SuperTools superTools = SuperTools.getInstance();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("网络连接异常，请联系管理员 err:");
    stringBuilder.append(i);
    superTools.showErrorDialog("网络", stringBuilder.toString());
  }
  
  private void compare(JSONObject paramJSONObject) {
    try {
      boolean bool = paramJSONObject.getBoolean("isUpdate");
      if (!bool) {
        this.mOnMyCallBack.onReceive("yes", 0, paramJSONObject);
        return;
      } 
      url = paramJSONObject.getString("gameIp");
      if (bool) {
        Activity activity = this.mActivity;
        Runnable runnable = new Runnable() {
//            final CheckAPKVersion this$0;
            
            public void run() {
              PropertiesData.getInstance().getString("UNION_ID");
              SCDialog.create(CheckAPKVersion.this.mActivity, "版 本 更 新", "请点击确认更新游戏", "确认", "", new OnMyClickedListener() {
//                    final CheckAPKVersion.null this$1;
                    
                    public void onNegative(JSONObject param2JSONObject) {
                      Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(CheckAPKVersion.url));
                      MainActivity.mActivity.startActivity(intent);
                      (new Thread(new Runnable() {
//                            final CheckAPKVersion.null.null this$2;
                            
                            public void run() {
                              try {
                                Thread.sleep(1000L);
                              } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                              } 
                              System.exit(0);
                            }
                          })).start();
                    }
                    
                    public void onPositive(JSONObject param2JSONObject) {}
                  }).show();
            }
          };
//        super(this);
        activity.runOnUiThread(runnable);
      } 
    } catch (JSONException jSONException) {
      jSONException.printStackTrace();
    } 
  }
  
  private void parse(InputStream paramInputStream) throws Exception {
    byte[] arrayOfByte = new byte[1024];
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    while (true) {
      int i = paramInputStream.read(arrayOfByte);
      if (i != -1) {
        byteArrayOutputStream.write(arrayOfByte, 0, i);
        continue;
      } 
      String str = new String(byteArrayOutputStream.toByteArray(), "utf-8");
      Log.d("xqjsdk", str);
      compare(new JSONObject(str));
      return;
    } 
  }
  
  public void run() {
    try {
      if (this.mActivity == null || this.mOnMyCallBack == null) {
        this.mOnMyCallBack.onReceive("error", 0, null);
        return;
      } 
      check();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\com\xqj\games\CheckAPKVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */