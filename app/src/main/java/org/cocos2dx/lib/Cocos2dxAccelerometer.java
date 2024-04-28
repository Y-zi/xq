package org.cocos2dx.lib;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.view.WindowManager;

public class Cocos2dxAccelerometer implements SensorEventListener {
  private static final String TAG = "Cocos2dxAccelerometer";
  
  private final Sensor mAccelerometer;
  
  private final Context mContext;
  
  private final int mNaturalOrientation;
  
  private final SensorManager mSensorManager;
  
  public Cocos2dxAccelerometer(Context paramContext) {
    this.mContext = paramContext;
    this.mSensorManager = (SensorManager)this.mContext.getSystemService("sensor");
    this.mAccelerometer = this.mSensorManager.getDefaultSensor(1);
    this.mNaturalOrientation = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getOrientation();
  }
  
  public static native void onSensorChanged(float paramFloat1, float paramFloat2, float paramFloat3, long paramLong);
  
  public void disable() {
    this.mSensorManager.unregisterListener(this);
  }
  
  public void enable() {
    this.mSensorManager.registerListener(this, this.mAccelerometer, 1);
  }
  
  public void onAccuracyChanged(Sensor paramSensor, int paramInt) {}
  
  public void onSensorChanged(SensorEvent paramSensorEvent) {
    float f1;
    float f2;
    if (paramSensorEvent.sensor.getType() != 1)
      return; 
    float f3 = paramSensorEvent.values[0];
    float f4 = paramSensorEvent.values[1];
    float f5 = paramSensorEvent.values[2];
    int i = (this.mContext.getResources().getConfiguration()).orientation;
    if (i == 2 && this.mNaturalOrientation != 0) {
      f1 = -f4;
      f2 = f3;
    } else {
      f1 = f3;
      f2 = f4;
      if (i == 1) {
        f1 = f3;
        f2 = f4;
        if (this.mNaturalOrientation != 0) {
          f2 = -f3;
          f1 = f4;
        } 
      } 
    } 
    Cocos2dxGLSurfaceView.queueAccelerometer(f1, f2, f5, paramSensorEvent.timestamp);
  }
  
  public void setInterval(float paramFloat) {
    if (Build.VERSION.SDK_INT < 11) {
      this.mSensorManager.registerListener(this, this.mAccelerometer, 1);
    } else {
      this.mSensorManager.registerListener(this, this.mAccelerometer, (int)(paramFloat * 100000.0F));
    } 
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxAccelerometer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */