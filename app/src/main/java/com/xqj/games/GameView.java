package com.xqj.games;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.View;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GameView extends View implements Runnable {
  private Activity mActivity = null;
  
  private int mIndex = 0;
  
  private List<Info> mList = null;
  
  public GameView(Activity paramActivity, int[] paramArrayOfint, String[] paramArrayOfString) {
    super((Context)paramActivity);
    this.mActivity = paramActivity;
    this.mList = new ArrayList<Info>();
    this.mIndex = 0;
    if (paramArrayOfint != null)
      for (byte b = 0; b < paramArrayOfint.length; b++)
        this.mList.add(new Info(paramArrayOfint[b], null, null));  
    if (paramArrayOfString != null)
      for (byte b = 0; b < paramArrayOfString.length; b++)
        this.mList.add(new Info(0, paramArrayOfString[b], null));  
    setBitmap();
    (new Thread(this)).start();
  }
  
  public static Bitmap getNewBitmap(Bitmap paramBitmap) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.PNG, 30, byteArrayOutputStream);
    try {
      byteArrayOutputStream.flush();
      byteArrayOutputStream.close();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    int m = paramBitmap.getWidth();
    int i = paramBitmap.getHeight();
    Matrix matrix = new Matrix();
    int k = SuperTools.getInstance().getWidth();
    int j = SuperTools.getInstance().getHeight();
    matrix.postScale(k / m, j / i);
    return Bitmap.createBitmap(paramBitmap, 0, 0, m, i, matrix, true);
  }
  
  public static Bitmap getNewBitmap(Bitmap paramBitmap, float paramFloat1, float paramFloat2) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.PNG, 30, byteArrayOutputStream);
    try {
      byteArrayOutputStream.flush();
      byteArrayOutputStream.close();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    Matrix matrix = new Matrix();
    matrix.postScale(paramFloat1, paramFloat2);
    return Bitmap.createBitmap(paramBitmap, 0, 0, i, j, matrix, true);
  }
  
  private boolean setBitmap() {
    try {
      for (Info info : this.mList) {
        InputStream inputStream = null;
        if (info.resId != 0) {
          inputStream = getResources().openRawResource(info.resId);
        } else if (!info.resName.equals("")) {
          inputStream = this.mActivity.getAssets().open(info.resName);
        } 
        info.bitmap = getNewBitmap(BitmapFactory.decodeStream(inputStream));
        inputStream.close();
      } 
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    return true;
  }
  
  public void draw(Canvas paramCanvas) {
    super.draw(paramCanvas);
    Paint paint = new Paint();
    paint.setStyle(Paint.Style.FILL_AND_STROKE);
    if (((Info)this.mList.get(this.mIndex)).bitmap != null)
      paramCanvas.drawBitmap(((Info)this.mList.get(this.mIndex)).bitmap, 0.0F, 0.0F, paint); 
  }
  
  public void nextBigmap() {
    if (this.mIndex >= this.mList.size() - 1) {
      this.mIndex = 0;
    } else {
      this.mIndex++;
    } 
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Thread.sleep(100L);
      } catch (InterruptedException interruptedException) {
        interruptedException.printStackTrace();
        Thread.currentThread().interrupt();
      } 
      postInvalidate();
    } 
  }
  
  class Info {
    public Bitmap bitmap;
    
    public int resId;
    
    public String resName;
    
//    final GameView this$0;
    
    Info(int param1Int, String param1String, Bitmap param1Bitmap) {
      this.resId = param1Int;
      this.resName = param1String;
      this.bitmap = param1Bitmap;
    }
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\com\xqj\games\GameView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */