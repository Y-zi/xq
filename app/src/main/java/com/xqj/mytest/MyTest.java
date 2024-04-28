package com.xqj.mytest;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxGLSurfaceView;
import org.cocos2dx.lib.Cocos2dxHelper;
import org.cocos2dx.lib.Cocos2dxRenderer;

public class MyTest extends Cocos2dxActivity implements LocationUtils.CopyStatus {
  private static final int JOB_ID = 101;
  
  private static final int REQUEST_READ_EXTERNAL_STORAGE = 2;
  
  private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
  
  private static MyTest sContext;
  
  private ImageView mBgView = null;
  
  private boolean mIsNeedClearBackGround = false;
  
  private TextView mTipText = null;
  
  private boolean mbUseExternal = false;
  
  static {
    System.loadLibrary("testcpp");
  }
  
  private boolean checkExternalStorageState() {
    if (!Cocos2dxHelper.isUseExternalStorage());
    return true;
  }
  
  private void checkWritePermission() {
    try {
      if (isWritePermissionGranted()) {
        onGetPermission();
      } else {
        requestWritePermission();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public static MyTest getInstance() {
    return sContext;
  }
  
  private void initView() {
    this.mBgView = new ImageView((Context)this);
    int i = MResource.getIdByName(getApplicationContext(), "drawable", "my_game_splash");
    this.mBgView.setImageResource(i);
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
    layoutParams.addRule(14, -1);
    layoutParams.addRule(15, -1);
    this.mBgView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    mFrameLayout.addView((View)this.mBgView);
    this.mIsNeedClearBackGround = true;
  }
  
  private boolean isWritePermissionGranted() {
    return !(ContextCompat.checkSelfPermission((Context)this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0);
  }
  
  private void onGetPermission() {
    (new InitThread()).start();
  }
  
  private void requestWritePermission() {
    ActivityCompat.requestPermissions((Activity)this, new String[] { "android.permission.WRITE_EXTERNAL_STORAGE" }, 1);
  }
  
  private void writeToFileTest(String var1) {
    File var2 = new File(var1);
    if (!var2.exists()) {
      if (!var2.mkdirs()) {
        StringBuilder var7 = new StringBuilder();
        var7.append("writeToFileTest mkdir failed:");
        var7.append(var1);
        Log.e("MyTest", var7.toString());
        return;
      }

      StringBuilder var3 = new StringBuilder();
      var3.append("writeToFileTest mkdir suc:");
      var3.append(var1);
      Log.e("MyTest", var3.toString());
    }

    if (var2.exists()) {
      File var5 = new File(var2, "example.txt");

      try {
        FileWriter var6 = new FileWriter(var5);
        var6.write("Hello, Android!");
        var6.close();
      } catch (IOException var4) {
        var4.printStackTrace();
      }
    }

  }
  
  public void clearBackground() {
    Log.e("MyTest", "clearBackground");
    if (this.mIsNeedClearBackGround) {
      RelayNative.doClearBackground("1500");
      this.mIsNeedClearBackGround = false;
    } 
  }
  
  public void doOpenURL(String paramString) {}
  
  public ImageView getBgView() {
    return this.mBgView;
  }
  
  public TextView getTipText() {
    return this.mTipText;
  }
  
  public void onCopyProgressChanged(int paramInt1, int paramInt2) {}
  
  protected void onCreate(Bundle paramBundle) {
    MyJobService.setContent((Activity)this);
    sContext = this;
    boolean bool2 = false;
    boolean bool1 = bool2;
    try {
      getWindow().addFlags(128);
      bool1 = bool2;
      bool2 = (getPackageManager().getApplicationInfo(getPackageName(), 128)).metaData.getBoolean("USE_EXTERNAL_STORAGE");
      bool1 = bool2;
      Cocos2dxHelper.initResPath((Context)this, bool2);
      bool1 = bool2;
    } catch (PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException.printStackTrace();
    } 
    super.onCreate(paramBundle);
    initView();
    this.mbUseExternal = bool1;
    if (bool1) {
      checkWritePermission();
    } else {
      onGetPermission();
    } 
  }
  
  public Cocos2dxGLSurfaceView onCreateView() {
    Cocos2dxGLSurfaceView cocos2dxGLSurfaceView = new Cocos2dxGLSurfaceView((Context)this);
    cocos2dxGLSurfaceView.setEGLConfigChooser(5, 6, 5, 0, 16, 8);
    return cocos2dxGLSurfaceView;
  }
  
  public boolean onInitResPath() {
    if (Build.VERSION.SDK_INT > 29) {
      Log.e("MyTest", "version > 10");
    } else {
      Log.e("MyTest", "version <= 10");
    } 
    ApplicationInfo applicationInfo = getApplicationInfo();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Environment.getExternalStorageDirectory());
    stringBuilder.append("/Android/data/");
    stringBuilder.append(applicationInfo.packageName);
    stringBuilder.append("/files/");
    writeToFileTest(stringBuilder.toString());
    writeToFileTest(getExternalFilesDir(null).getAbsolutePath());
    String[] arrayOfString = new String[2];
    arrayOfString[0] = "assets/xqol";
    arrayOfString[1] = "assets/xqol_";
    String str = Cocos2dxHelper.getExternalAssetPath();
    int i = 1;
    try {
      int j = (getPackageManager().getPackageInfo(getPackageName(), 128)).versionCode;
      i = j;
      StringBuilder stringBuilder1 = new StringBuilder();
      i = j;
//      this();
      i = j;
      stringBuilder1.append("onInitResPath:");
      i = j;
      stringBuilder1.append(str);
      i = j;
      stringBuilder1.append(arrayOfString);
      i = j;
      Log.e("MyTest", stringBuilder1.toString());
      i = j;
    } catch (PackageManager.NameNotFoundException nameNotFoundException) {}
    return LocationUtils.CopyAssets(getPackageResourcePath(), str, arrayOfString, i, this);
  }
  
  public void onRenderPause() {
    Log.e("MyTest", "onRenderPause");
  }
  
  public void onRenderResume() {
    Log.e("MyTest", "onRenderResume");
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
    if (paramInt == 1)
      if (paramArrayOfint.length > 0 && paramArrayOfint[0] == 0) {
        onGetPermission();
      } else {
        Toast.makeText((Context)this, "需要写入外部存储权限来正常运行游戏", Toast.LENGTH_SHORT).show();
        requestWritePermission();
      }  
  }
  
  private class InitThread extends Thread {
//    final MyTest this$0;
    
    private InitThread() {}
    
    public void run() {
      if (!MyTest.this.onInitResPath())
        return; 
      RelayNative.setActivityMain(MyTest.this);
      Cocos2dxRenderer.setCanInit();
    }
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\com\xqj\mytest\MyTest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */