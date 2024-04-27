package org.cocos2dx.lib;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public abstract class Cocos2dxActivity extends Activity implements Cocos2dxHelper.Cocos2dxHelperListener {
  private static final String TAG = "Cocos2dxActivity";
  
  protected static FrameLayout mFrameLayout;
  
  private static Context sContext;
  
  protected Cocos2dxGLSurfaceView mGLSurfaceView;
  
  private Cocos2dxHandler mHandler;
  
  public static Context getContext() {
    return sContext;
  }
  
  private static final boolean isAndroidEmulator() {
    String str1 = Build.MODEL;
    String str3 = TAG;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("model=");
    stringBuilder2.append(str1);
    Log.d(str3, stringBuilder2.toString());
    String str2 = Build.PRODUCT;
    str3 = TAG;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("product=");
    stringBuilder1.append(str2);
    Log.d(str3, stringBuilder1.toString());
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (str2 != null) {
      if (!str2.equals("sdk") && !str2.contains("_sdk")) {
        bool1 = bool2;
        if (str2.contains("sdk_")) {
          bool1 = true;
          str2 = TAG;
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("isEmulator=");
          stringBuilder1.append(bool1);
          Log.d(str2, stringBuilder1.toString());
          return bool1;
        } 
        str2 = TAG;
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("isEmulator=");
        stringBuilder1.append(bool1);
        Log.d(str2, stringBuilder1.toString());
        return bool1;
      } 
    } else {
      str2 = TAG;
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("isEmulator=");
      stringBuilder1.append(bool1);
      Log.d(str2, stringBuilder1.toString());
      return bool1;
    } 
    bool1 = true;
    str2 = TAG;
    stringBuilder1 = new StringBuilder();
    stringBuilder1.append("isEmulator=");
    stringBuilder1.append(bool1);
    Log.d(str2, stringBuilder1.toString());
    return bool1;
  }
  
  public void init() {
    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
    mFrameLayout = new FrameLayout((Context)this);
    mFrameLayout.setLayoutParams(layoutParams);
    layoutParams = new ViewGroup.LayoutParams(-1, -2);
    Cocos2dxEditText cocos2dxEditText = new Cocos2dxEditText((Context)this);
    cocos2dxEditText.setLayoutParams(layoutParams);
    mFrameLayout.addView((View)cocos2dxEditText);
    mGLSurfaceView = onCreateView();
    mFrameLayout.addView((View)mGLSurfaceView);
    if (isAndroidEmulator())
      mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0); 
    mGLSurfaceView.setCocos2dxRenderer(new Cocos2dxRenderer(this));
    mGLSurfaceView.setCocos2dxEditText(cocos2dxEditText);
    setContentView((View)mFrameLayout);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    sContext = (Context)this;
    mHandler = new Cocos2dxHandler(this);
    init();
    Cocos2dxHelper.init((Context)this, this);
  }
  
  public Cocos2dxGLSurfaceView onCreateView() {
    return new Cocos2dxGLSurfaceView((Context)this);
  }
  
  protected void onPause() {
    super.onPause();
    Cocos2dxHelper.onPause();
    mGLSurfaceView.onPause();
  }
  
  public void onRenderPause() {}
  
  public void onRenderResume() {}
  
  protected void onResume() {
    super.onResume();
    Cocos2dxHelper.onResume();
    mGLSurfaceView.onResume();
  }
  
  public void onWindowFocusChanged(boolean paramBoolean) {
    String str = TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("onWindowFocusChanged() hasFocus=");
    stringBuilder.append(paramBoolean);
    Log.d(str, stringBuilder.toString());
    super.onWindowFocusChanged(paramBoolean);
    if (paramBoolean) {
      Cocos2dxHelper.onResume();
      mGLSurfaceView.onResume();
    } 
  }
  
  public void runOnGLThread(Runnable paramRunnable) {
    mGLSurfaceView.queueEvent(paramRunnable);
  }
  
  public void showDialog(String paramString1, String paramString2) {
    Message message = new Message();
    message.what = 1;
    message.obj = new Cocos2dxHandler.DialogMessage(paramString1, paramString2);
    mHandler.sendMessage(message);
  }
  
  public void showEditTextDialog(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    Message message = new Message();
    message.what = 2;
    message.obj = new Cocos2dxHandler.EditBoxMessage(paramString1, paramString2, paramInt1, paramInt2, paramInt3, paramInt4);
    mHandler.sendMessage(message);
  }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */