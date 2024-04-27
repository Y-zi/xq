package com.xqj.games;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.xqj.mytest.MyTest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class SuperTools {
  private static final String PREF_KEY = "";
  
  private static final String RES_ROOT_PATH = "xqj_";
  
  private static String dd = ".";
  
  public static boolean isDebug = true;
  
  private static SuperTools mSuperTools;
  
  private static int num;
  
  private Activity mActivity = null;
  
  private List<Activity> mActivityLists = new ArrayList<Activity>();
  
  private int mHeight = -1;
  
  private int mWidth = -1;
  
  public static void delayAction(final int time, final OnMyCallBack o) {
    (new Thread(new Runnable() {
//          final OnMyCallBack val$o;
//
//          final int val$time;
          
          public void run() {
            try {
              Thread.sleep(time);
            } catch (InterruptedException interruptedException) {
              interruptedException.printStackTrace();
            } 
            OnMyCallBack onMyCallBack = o;
            if (onMyCallBack != null)
              onMyCallBack.onReceive("", -1, null); 
          }
        })).start();
  }
  
  public static void deleteFile(File paramFile) {
    if (paramFile.isFile()) {
      paramFile.delete();
      return;
    } 
    if (paramFile.isDirectory()) {
      File[] arrayOfFile = paramFile.listFiles();
      if (arrayOfFile == null || arrayOfFile.length == 0) {
        paramFile.delete();
        return;
      } 
      for (byte b = 0; b < arrayOfFile.length; b++)
        deleteFile(arrayOfFile[b]); 
      paramFile.delete();
    } 
  }
  
  private void finishAllActivity() {
    for (byte b = 0; b < this.mActivityLists.size(); b++) {
      if (this.mActivityLists.get(b) != null && !((Activity)this.mActivityLists.get(b)).isFinishing())
        ((Activity)this.mActivityLists.get(b)).finish(); 
    } 
  }
  
  public static SuperTools getInstance() {
    if (mSuperTools == null)
      mSuperTools = new SuperTools(); 
    return mSuperTools;
  }
  
  private void getScreenParameters() {
    new DisplayMetrics();
    DisplayMetrics displayMetrics = this.mActivity.getResources().getDisplayMetrics();
    this.mWidth = displayMetrics.widthPixels;
    this.mHeight = displayMetrics.heightPixels;
  }
  
  public static int getStringType(String paramString) {
    return Pattern.compile("[0-9]*").matcher(paramString).matches() ? 0 : (Pattern.compile("[a-zA-Z]").matcher(paramString).matches() ? 1 : (Pattern.compile("[一-龥]").matcher(paramString).matches() ? 2 : -1));
  }
  
  private static boolean isEmpty(String paramString) {
    return (paramString == null || paramString.equals(""));
  }
  
  private boolean isExistActivity(Activity paramActivity) {
    for (byte b = 0; b < this.mActivityLists.size(); b++) {
      if (paramActivity == this.mActivityLists.get(b))
        return true; 
    } 
    return false;
  }
  
  public static void runByThread(Runnable paramRunnable) {
    (new Thread(paramRunnable)).start();
  }
  
  public static void runByUiThread(Activity paramActivity, Runnable paramRunnable) {
    paramActivity.runOnUiThread(paramRunnable);
  }
  
  public int _th(int paramInt) {
    return (int)(getHeight() * paramInt / 720.0F);
  }
  
  public int _tw(int paramInt) {
    return (int)(getWidth() * paramInt / 1280.0F);
  }
  
  public void deleteFile(String paramString) {
    deleteFile(new File(paramString));
  }
  
  public String getAPKPath() {
    return "";
  }
  
  public Activity getCurActivity() {
    return this.mActivity;
  }
  
  public String getDownloadLibPath() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getAPKPath());
    stringBuilder.append("lib/");
    return stringBuilder.toString();
  }
  
  public String getDownloadResPath() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getAPKPath());
    stringBuilder.append("data/");
    return stringBuilder.toString();
  }
  
  public int getHeight() {
    if (this.mHeight < 0)
      getScreenParameters(); 
    return this.mHeight;
  }
  
  public String getIMEI() {
    String str2 = readLocalFile("IMEI");
    String str1 = str2;
    if (str2.equals("")) {
      str1 = String.valueOf(getRandom()).replace("-", "");
      writeLocalFile("IMEI", str1);
    } 
    return str1;
  }
  
  public String getInstallAPKPath() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getAPKPath());
    stringBuilder.append("InstallAPK/");
    return stringBuilder.toString();
  }
  
//  public String getModel() {
//    TelephonyManager telephonyManager = (TelephonyManager)this.mActivity.getSystemService("phone");
//    return Build.MODEL;
//  }
//
//  public boolean getPhoneIsConnected() {
//    return ((ConnectivityManager)this.mActivity.getSystemService("connectivity")).getActiveNetworkInfo().isConnected();
//  }
//
//  public int getPhoneNetworkType() {
//    return ((ConnectivityManager)this.mActivity.getSystemService("connectivity")).getActiveNetworkInfo().getType();
//  }
  
  public long getRandom() {
    return (new Random(System.currentTimeMillis())).nextLong();
  }
  
  public String getSomeThing(boolean paramBoolean) {
    int var2 = 0;
    if (paramBoolean) {
      num = 0;
    }

    if (num >= 4) {
      num = 0;
    }

    String var5 = "";

    while(true) {
      int var4 = num;
      int var3 = var4;
      String var6 = var5;
      if (var2 >= var4) {
        while(var3 < 3) {
          StringBuilder var7 = new StringBuilder();
          var7.append(var6);
          var7.append(" ");
          var6 = var7.toString();
          ++var3;
        }

        ++num;
        return var6;
      }

      StringBuilder var8 = new StringBuilder();
      var8.append(var5);
      var8.append(dd);
      var5 = var8.toString();
      ++var2;
    }
  }
  
  public String getSystemPath() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.mActivity.getDir("LIBRARY_DIR", 0).getPath());
    stringBuilder.append("/");
    return stringBuilder.toString();
  }
  
  public String getVersionCode() {
    try {
      int i = (this.mActivity.getPackageManager().getPackageInfo(this.mActivity.getPackageName(), 0)).versionCode;
      return String.valueOf(i);
    } catch (PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException.printStackTrace();
      return "10000";
    } 
  }
  
  public String getVersionName() {
    try {
      return String.valueOf((this.mActivity.getPackageManager().getPackageInfo(this.mActivity.getPackageName(), 0)).versionName);
    } catch (PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException.printStackTrace();
      return "10000";
    } 
  }
  
  public int getWidth() {
    if (this.mWidth < 0)
      getScreenParameters(); 
    return this.mWidth;
  }
  
  public boolean isExistFile(String paramString) {
    return (new File(paramString)).exists();
  }
  
  public boolean isThird() {
    return true;
  }
  
  public boolean openURL(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("openURL:");
    stringBuilder.append(paramString);
    Log.e("Cocos2dxHelper", stringBuilder.toString());
    byte b = -1;
    boolean bool = false;
    try {
      if (paramString.hashCode() == 282249071 && paramString.equals("sdklogin"))
        b = 0; 
      if (b != 0) {
        Intent intent = new Intent("android.intent.action.VIEW");
//        this("android.intent.action.VIEW");
        intent.setData(Uri.parse(paramString));
        this.mActivity.startActivity(intent);
      } else {
        ThirdLogin.callLoginUi();
      } 
      bool = true;
    } catch (Exception exception) {}
    return bool;
  }
  
  public String postHttp(String paramString) {
    try {
      Log.d("apkurl", paramString);
      HttpGet httpGet = new HttpGet(paramString);
//      this(paramString);
      DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
//      this();
      HttpResponse httpResponse = defaultHttpClient.execute((HttpUriRequest)httpGet);
      if (httpResponse.getStatusLine().getStatusCode() == 200) {
        InputStream inputStream = httpResponse.getEntity().getContent();
        byte[] arrayOfByte = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        this();
        while (true) {
          int i = inputStream.read(arrayOfByte);
          if (i != -1) {
            byteArrayOutputStream.write(arrayOfByte, 0, i);
            continue;
          } 
          return new String(byteArrayOutputStream.toByteArray(), "utf-8");
        } 
      } 
      return "{\"rt\":-1}";
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
  
  public JSONObject postHttpJson(String paramString) {
    return postHttpJson(paramString, null);
  }
  
  public JSONObject postHttpJson(String paramString, Map<String, String> paramMap) {
//    Exception var10000;
//    boolean var10001;
//    String var21;
//    label106: {
//      String var24;
//      label105: {
//        label104: {
//          label103: {
//            Exception var20;
//            label109: {
//              HttpResponse var23;
//              label101: {
//                label110: {
//                  HttpGet var4;
//                  DefaultHttpClient var6;
//                  try {
//                    Log.d("apkurl", paramString);
//                    var4 = new HttpGet(paramString);
//                    var6 = new DefaultHttpClient();
//                  } catch (Exception var16) {
//                    var10000 = var16;
//                    var10001 = false;
//                    break label110;
//                  }
//
//                  if (paramMap != null) {
//                    Iterator var17;
//                    try {
//                      var17 = paramMap.keySet().iterator();
//                    } catch (Exception var14) {
//                      var10000 = var14;
//                      var10001 = false;
//                      break label110;
//                    }
//
//                    while(true) {
//                      try {
//                        if (!var17.hasNext()) {
//                          break;
//                        }
//
//                        String var5 = (String)var17.next();
//                        var4.setHeader(var5, (String)paramMap.get(var5));
//                      } catch (Exception var15) {
//                        var10000 = var15;
//                        var10001 = false;
//                        break label110;
//                      }
//                    }
//                  }
//
//                  byte[] var18;
//                  ByteArrayOutputStream var19;
//                  InputStream var26;
//                  try {
//                    var4.setHeader("Accept-Language:", "zh,zh-CN;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
//                    var23 = var6.execute(var4);
//                    if (var23.getStatusLine().getStatusCode() != 200) {
//                      break label103;
//                    }
//
//                    var26 = var23.getEntity().getContent();
//                    var18 = new byte[1024];
//                    var19 = new ByteArrayOutputStream();
//                  } catch (Exception var12) {
//                    var10000 = var12;
//                    var10001 = false;
//                    break label110;
//                  }
//
//                  while(true) {
//                    int var3;
//                    try {
//                      var3 = var26.read(var18);
//                    } catch (Exception var11) {
//                      var10000 = var11;
//                      var10001 = false;
//                      break;
//                    }
//
//                    if (var3 == -1) {
//                      try {
//                        var21 = new String(var19.toByteArray(), "utf-8");
//                        break label101;
//                      } catch (Exception var10) {
//                        var10000 = var10;
//                        var10001 = false;
//                        break;
//                      }
//                    }
//
//                    try {
//                      var19.write(var18, 0, var3);
//                    } catch (Exception var13) {
//                      var10000 = var13;
//                      var10001 = false;
//                      break;
//                    }
//                  }
//                }
//
//                var20 = var10000;
//                var1 = "";
//                break label109;
//              }
//
//              var1 = var21;
//
//              try {
//                if (var23.getFirstHeader("Set-Cookie") != null) {
//                  var24 = var23.getFirstHeader("Set-Cookie").getValue();
//                  break label105;
//                }
//                break label104;
//              } catch (Exception var9) {
//                var1 = var21;
//                var20 = var9;
//              }
//            }
//
//            var20.printStackTrace();
//            break label104;
//          }
//
//          var1 = "{\"rt\":-1}";
//        }
//
//        var21 = "";
//        break label106;
//      }
//
//      var1 = var21;
//      var21 = var24;
//    }
//
//    label112: {
//      JSONObject var25;
//      try {
//        var25 = new JSONObject(paramString);
//      } catch (Exception var8) {
//        var10000 = var8;
//        var10001 = false;
//        break label112;
//      }
//
//      if (var21 == null) {
//        return var25;
//      }
//
//      try {
//        if (!"".equals(var21)) {
//          var25.put("cookie", var21);
//        }
//
//        return var25;
//      } catch (Exception var7) {
//        var10000 = var7;
//        var10001 = false;
//      }
//    }
//
//    Exception var22 = var10000;
//    var22.printStackTrace();
    return null;
  }
  
  public String readLocalFile(String paramString) {
    String str = this.mActivity.getSharedPreferences("", 0).getString(paramString, "");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("readLocalFile=");
    stringBuilder.append(paramString);
    stringBuilder.append("      v=");
    stringBuilder.append(str);
    Log.d("xqjsdk", stringBuilder.toString());
    return str;
  }
  
  public void releaseGameRes() {
    finishAllActivity();
    Process.killProcess(Process.myPid());
    System.exit(0);
  }
  
  public void removeLocalFile(String paramString) {
    this.mActivity.getSharedPreferences("", 0).edit().remove(paramString).commit();
  }
  
  public void setCurActivity(Activity paramActivity) {
    this.mActivity = paramActivity;
    if (!isExistActivity(paramActivity))
      this.mActivityLists.add(paramActivity); 
  }
  
  public void setScreenOrientationLandscape(Activity paramActivity) {
    setCurActivity(paramActivity);
    paramActivity.requestWindowFeature(1);
    paramActivity.getWindow().setFlags(1024, 1024);
    paramActivity.getWindow().setFlags(128, 128);
  }
  
//  public void setkeyboardVisible(View paramView, boolean paramBoolean) {
//    InputMethodManager inputMethodManager = (InputMethodManager)this.mActivity.getSystemService("input_method");
//    inputMethodManager.isActive();
//    if (paramBoolean) {
//      inputMethodManager.showSoftInput(paramView, 2);
//      return;
//    }
//    inputMethodManager.hideSoftInputFromWindow(paramView.getWindowToken(), 0);
//  }
  
  public void showErrorDialog(final String title, final String content) {
    this.mActivity.runOnUiThread(new Runnable() {
//          final SuperTools this$0;
//
//          final String val$content;
//
//          final String val$title;
          
          public void run() {
            SCDialog.create(SuperTools.this.mActivity, title, content, "关闭", "", new OnMyClickedListener() {
//                  final SuperTools.null this$1;
                  
                  public void onNegative(JSONObject param2JSONObject) {}
                  
                  public void onPositive(JSONObject param2JSONObject) {}
                }).show();
          }
        });
  }
  
  public void showErrorDialogInGame(final String title, final String content) {
    MyTest.getInstance().runOnUiThread(new Runnable() {
//          final SuperTools this$0;
//
//          final String val$content;
//
//          final String val$title;
          
          public void run() {
            SCDialog.create((Activity)MyTest.getInstance(), title, content, "关闭", "", new OnMyClickedListener() {
//                  final SuperTools.null this$1;
                  
                  public void onNegative(JSONObject param2JSONObject) {}
                  
                  public void onPositive(JSONObject param2JSONObject) {}
                }).show();
          }
        });
  }
  
  public void showExitGameDialog(final String title, final String content) {
    this.mActivity.runOnUiThread(new Runnable() {
//          final SuperTools this$0;
//
//          final String val$content;
//
//          final String val$title;
          
          public void run() {
            SCDialog.create(SuperTools.this.mActivity, title, content, "关闭", "退出游戏", new OnMyClickedListener() {
//                  final SuperTools.null this$1;
                  
                  public void onNegative(JSONObject param2JSONObject) {}
                  
                  public void onPositive(JSONObject param2JSONObject) {
                    System.exit(0);
                  }
                }).show();
          }
        });
  }
  
  public AlertDialog showLoadingAlert(Activity paramActivity, String paramString) {
    AlertDialog alertDialog = (new AlertDialog.Builder((Context)paramActivity)).create();
    alertDialog.setMessage(paramString);
    alertDialog.show();
    return alertDialog;
  }
  
  public void showLoadingAlertOnUi(final Activity activity, final String msg) {
    activity.runOnUiThread(new Runnable() {
//          final SuperTools this$0;
//
//          final Activity val$activity;
//
//          final String val$msg;
          
          public void run() {
            AlertDialog alertDialog = (new AlertDialog.Builder((Context)activity)).create();
            alertDialog.setMessage(msg);
            alertDialog.show();
          }
        });
  }
  
  public void writeLocalFile(String paramString1, String paramString2) {
    SharedPreferences sharedPreferences = this.mActivity.getSharedPreferences("", 0);
    sharedPreferences.edit().remove(paramString1).commit();
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(paramString1, paramString2);
    editor.commit();
  }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\com\xqj\games\SuperTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */