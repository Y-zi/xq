package org.cocos2dx.lib;

import android.opengl.GLSurfaceView;
import android.util.Log;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Cocos2dxRenderer implements GLSurfaceView.Renderer {
  private static final long NANOSECONDSPERMICROSECOND = 1000000L;
  
  private static final long NANOSECONDSPERSECOND = 1000000000L;
  
  private static boolean mCocosInitCompleted = false;
  
  private static boolean mNativeInitCompleted = false;
  
  private static long sAnimationInterval = 16666666L;
  
  private long mLastTickInNanoSeconds;
  
  private Cocos2dxHelper.Cocos2dxHelperListener mListener = null;
  
  private int mScreenHeight;
  
  private int mScreenWidth;
  
  public Cocos2dxRenderer(Cocos2dxHelper.Cocos2dxHelperListener paramCocos2dxHelperListener) {
    this.mListener = paramCocos2dxHelperListener;
  }
  
  private static native void nativeDeleteBackward();
  
  private static native String nativeGetContentText();
  
  private static native void nativeInit(int paramInt1, int paramInt2);
  
  private static native void nativeInsertText(String paramString);
  
  private static native boolean nativeKeyDown(int paramInt);
  
  private static native void nativeOnPause();
  
  private static native void nativeOnResume();
  
  private static native void nativeRender();
  
  private static native void nativeTouchesBegin(int paramInt, float paramFloat1, float paramFloat2);
  
  private static native void nativeTouchesCancel(int[] paramArrayOfint, float[] paramArrayOffloat1, float[] paramArrayOffloat2);
  
  private static native void nativeTouchesEnd(int paramInt, float paramFloat1, float paramFloat2);
  
  private static native void nativeTouchesMove(int[] paramArrayOfint, float[] paramArrayOffloat1, float[] paramArrayOffloat2);
  
  public static void setAnimationInterval(double paramDouble) {
    sAnimationInterval = (long)(paramDouble * 1.0E9D);
  }
  
  public static void setCanInit() {
    mNativeInitCompleted = true;
  }
  
  public String getContentText() {
    return nativeGetContentText();
  }
  
  public void handleActionCancel(int[] paramArrayOfint, float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
    nativeTouchesCancel(paramArrayOfint, paramArrayOffloat1, paramArrayOffloat2);
  }
  
  public void handleActionDown(int paramInt, float paramFloat1, float paramFloat2) {
    nativeTouchesBegin(paramInt, paramFloat1, paramFloat2);
  }
  
  public void handleActionMove(int[] paramArrayOfint, float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
    nativeTouchesMove(paramArrayOfint, paramArrayOffloat1, paramArrayOffloat2);
  }
  
  public void handleActionUp(int paramInt, float paramFloat1, float paramFloat2) {
    nativeTouchesEnd(paramInt, paramFloat1, paramFloat2);
  }
  
  public void handleDeleteBackward() {
    nativeDeleteBackward();
  }
  
  public void handleInsertText(String paramString) {
    nativeInsertText(paramString);
  }
  
  public void handleKeyDown(int paramInt) {
    nativeKeyDown(paramInt);
  }
  
  public void handleOnPause() {
    nativeOnPause();
    this.mListener.onRenderPause();
  }
  
  public void handleOnResume() {
    nativeOnResume();
    this.mListener.onRenderResume();
  }
  
  public void onDrawFrame(GL10 paramGL10) {
    if (!mCocosInitCompleted) {
      if (!mNativeInitCompleted)
        return; 
      Log.e("Cocos2dxRenderer", "Cocos2dxRenderer.nativeInit");
      nativeInit(this.mScreenWidth, this.mScreenHeight);
      this.mLastTickInNanoSeconds = System.nanoTime();
      mCocosInitCompleted = true;
      Cocos2dxHelper.Cocos2dxHelperListener cocos2dxHelperListener = this.mListener;
      if (cocos2dxHelperListener != null)
        cocos2dxHelperListener.clearBackground(); 
    } 
    if (sAnimationInterval <= 1.6666666666666666E7D) {
      if (mNativeInitCompleted)
        nativeRender(); 
    } else {
      long l = System.nanoTime();
      l = this.mLastTickInNanoSeconds + sAnimationInterval - l;
      if (l > 0L)
        try {
          Thread.sleep(l / 1000000L);
        } catch (Exception exception) {} 
      this.mLastTickInNanoSeconds = System.nanoTime();
      nativeRender();
    } 
  }
  
  public void onSurfaceChanged(GL10 paramGL10, int paramInt1, int paramInt2) {}
  
  public void onSurfaceCreated(GL10 paramGL10, EGLConfig paramEGLConfig) {
    this.mLastTickInNanoSeconds = System.nanoTime();
  }
  
  public void setScreenWidthAndHeight(int paramInt1, int paramInt2) {
    this.mScreenWidth = paramInt1;
    this.mScreenHeight = paramInt2;
  }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */