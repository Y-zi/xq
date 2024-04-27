package com.xqj.mytest;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RelayNative {
  private static MyTest mActivityMain;
  
  public static native void DestroyEngine();
  
  public static native int GetFrameInterval();
  
  public static native int GetProcessFrameRate();
  
  public static native int InitEngine(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, String paramString1, String paramString2, String paramString3, long paramLong);
  
  public static native void OnAutoPatch(int paramInt);
  
  public static native boolean OnDoubleTap(int paramInt, float paramFloat1, float paramFloat2, long paramLong1, long paramLong2);
  
  public static native boolean OnFling(int paramInt1, float paramFloat1, float paramFloat2, long paramLong1, long paramLong2, int paramInt2, float paramFloat3, float paramFloat4, long paramLong3, long paramLong4, float paramFloat5, float paramFloat6);
  
  public static native void OnInputString(String paramString);
  
  public static native void OnKeyboardShow(boolean paramBoolean, int paramInt);
  
  public static native void OnLogin(int paramInt, String paramString);
  
  public static native void OnLogout();
  
  public static native void OnLongPress(int paramInt, float paramFloat1, float paramFloat2, long paramLong1, long paramLong2);
  
  public static native void OnProcUpdateMemroyInfo(int paramInt1, int paramInt2, float paramFloat);
  
  public static native void OnScale(float paramFloat1, float paramFloat2, long paramLong1, long paramLong2, float paramFloat3);
  
  public static native boolean OnSingleTap(int paramInt, float paramFloat1, float paramFloat2, long paramLong1, long paramLong2);
  
  public static native boolean OnTouchEvent(int paramInt, float paramFloat1, float paramFloat2, long paramLong1, long paramLong2);
  
  public static native void Process();
  
  public static native void Render();
  
  public static native void Resize(int paramInt1, int paramInt2);
  
  public static void doClearBackground(String paramString) {
    MyTest myTest = mActivityMain;
    if (myTest == null)
      return; 
    final ImageView bgView = myTest.getBgView();
    final TextView tipText = mActivityMain.getTipText();
    long l2 = 1500L;
    long l1 = l2;
    if (paramString != null) {
      l1 = l2;
      if (paramString.length() != 0)
        l1 = Integer.valueOf(paramString).intValue(); 
    } 
    (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
//          final ImageView val$bgView;
//
//          final TextView val$tipText;
          
          public void run() {
            ImageView imageView = bgView;
            if (imageView != null)
//              imageView.setVisibility(4);
              imageView.setVisibility(View.INVISIBLE);
            TextView textView = tipText;
            if (textView != null) {
              textView.setText("");
//              tipText.setVisibility(0);
              tipText.setVisibility(View.VISIBLE);
            }
            RelayNative.mActivityMain.onWindowFocusChanged(true);
          }
        },  l1);
  }
  
  public static void doEditChgPos(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (mActivityMain == null);
  }
  
  public static void doEditFocusChg(String paramString, int paramInt1, int paramInt2, int paramInt3) {
    if (mActivityMain == null);
  }
  
  public static void doEnterPlatform(int paramInt) {}
  
  public static void doExit() {
    Process.killProcess(Process.myPid());
  }
  
  public static void doLogin() {}
  
  public static void doLogout() {}
  
  public static void doOpenURL(String paramString) {
    MyTest myTest = mActivityMain;
    if (myTest == null)
      return; 
    myTest.doOpenURL(paramString);
  }
  
  public static void doProcPay(String paramString) {}
  
  public static void doProcUpdateMemoryInfo(float paramFloat) {
    if (mActivityMain == null);
  }
  
  public static void setActivityMain(MyTest paramMyTest) {
    mActivityMain = paramMyTest;
  }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\com\xqj\mytest\RelayNative.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */