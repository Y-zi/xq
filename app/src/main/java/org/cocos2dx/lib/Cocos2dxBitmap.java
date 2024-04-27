package org.cocos2dx.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;

public class Cocos2dxBitmap {
  private static final int HORIZONTALALIGN_CENTER = 3;
  
  private static final int HORIZONTALALIGN_LEFT = 1;
  
  private static final int HORIZONTALALIGN_RIGHT = 2;
  
  private static final int VERTICALALIGN_BOTTOM = 2;
  
  private static final int VERTICALALIGN_CENTER = 3;
  
  private static final int VERTICALALIGN_TOP = 1;
  
  private static Context sContext;
  
  private static TextProperty computeTextProperty(String paramString, int paramInt1, int paramInt2, Paint paramPaint) {
    Paint.FontMetricsInt fontMetricsInt = paramPaint.getFontMetricsInt();
    int i = (int)Math.ceil((fontMetricsInt.bottom - fontMetricsInt.top));
    String[] arrayOfString = splitString(paramString, paramInt1, paramInt2, paramPaint);
    if (paramInt1 == 0) {
      int j = arrayOfString.length;
      byte b = 0;
      for (paramInt1 = 0; b < j; paramInt1 = paramInt2) {
        String str = arrayOfString[b];
        int k = (int)Math.ceil(paramPaint.measureText(str, 0, str.length()));
        paramInt2 = paramInt1;
        if (k > paramInt1)
          paramInt2 = k; 
        b++;
      } 
    } 
    return new TextProperty(paramInt1, i, arrayOfString);
  }
  
  private static int computeX(String paramString, int paramInt1, int paramInt2) {
    int i = paramInt1;
    if (paramInt2 != 2)
      if (paramInt2 != 3) {
        i = 0;
      } else {
        i = paramInt1 / 2;
      }  
    return i;
  }
  
  private static int computeY(Paint.FontMetricsInt paramFontMetricsInt, int paramInt1, int paramInt2, int paramInt3) {
    int j = -paramFontMetricsInt.top;
    int i = j;
    if (paramInt1 > paramInt2)
      if (paramInt3 != 1) {
        if (paramInt3 != 2) {
          if (paramInt3 != 3) {
            i = j;
          } else {
            paramInt3 = -paramFontMetricsInt.top;
            paramInt2 = (paramInt1 - paramInt2) / 2;
            paramInt1 = paramInt3;
            i = paramInt1 + paramInt2;
          } 
        } else {
          paramInt3 = -paramFontMetricsInt.top;
          paramInt2 = paramInt1 - paramInt2;
          paramInt1 = paramInt3;
          i = paramInt1 + paramInt2;
        } 
      } else {
        i = -paramFontMetricsInt.top;
      }  
    return i;
  }
  
  public static void createTextBitmap(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    createTextBitmapShadowStroke(paramString1, paramString2, paramInt1, 1.0F, 1.0F, 1.0F, paramInt2, paramInt3, paramInt4, false, 0.0F, 0.0F, 0.0F, false, 1.0F, 1.0F, 1.0F, 1.0F);
  }
  
  public static void createTextBitmapShadowStroke(String paramString1, String paramString2, int paramInt1, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean2, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10) {
    float f1;
    int j = paramInt2 & 0xF;
    int k = paramInt2 >> 4 & 0xF;
    paramString1 = refactorString(paramString1);
    Paint paint = newPaint(paramString2, paramInt1, j);
    double d = paramFloat1;
    Double.isNaN(d);
    paramInt2 = (int)(d * 255.0D);
    d = paramFloat2;
    Double.isNaN(d);
    int i = (int)(d * 255.0D);
    d = paramFloat3;
    Double.isNaN(d);
    paint.setARGB(255, paramInt2, i, (int)(d * 255.0D));
    TextProperty textProperty = computeTextProperty(paramString1, paramInt3, paramInt4, paint);
    if (paramInt4 == 0) {
      paramInt2 = textProperty.mTotalHeight;
    } else {
      paramInt2 = paramInt4;
    } 
    float f2 = 0.0F;
    if (paramBoolean1) {
      paint.setShadowLayer(paramFloat6, paramFloat4, paramFloat5, -8553091);
      paramFloat2 = Math.abs(paramFloat4);
      paramFloat3 = Math.abs(paramFloat5);
      if (paramFloat4 < 0.0D) {
        paramFloat1 = paramFloat2;
      } else {
        paramFloat1 = 0.0F;
      } 
      f1 = paramFloat1;
      paramFloat6 = paramFloat2;
      paramFloat4 = paramFloat3;
      if (paramFloat5 < 0.0D) {
        f2 = paramFloat3;
        f1 = paramFloat1;
        paramFloat6 = paramFloat2;
        paramFloat4 = paramFloat3;
      } 
    } else {
      f1 = 0.0F;
      paramFloat6 = 0.0F;
      paramFloat4 = 0.0F;
    } 
    Bitmap bitmap = Bitmap.createBitmap(textProperty.mMaxWidth + (int)paramFloat6, paramInt2 + (int)paramFloat4, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
    i = computeY(fontMetricsInt, paramInt4, textProperty.mTotalHeight, k);
    String[] arrayOfString = textProperty.mLines;
    paramInt2 = arrayOfString.length;
    for (paramInt3 = 0; paramInt3 < paramInt2; paramInt3++) {
      String str = arrayOfString[paramInt3];
      canvas.drawText(str, computeX(str, textProperty.mMaxWidth, j) + f1, i + f2, paint);
      i += textProperty.mHeightPerLine;
    } 
    if (paramBoolean2) {
      Paint paint1 = newPaint(paramString2, paramInt1, j);
      paint1.setStyle(Paint.Style.STROKE);
      paint1.setStrokeWidth(0.5F * paramFloat10);
      paint1.setARGB(255, (int)paramFloat7 * 255, (int)paramFloat8 * 255, (int)paramFloat9 * 255);
      paramInt1 = computeY(fontMetricsInt, paramInt4, textProperty.mTotalHeight, k);
      arrayOfString = textProperty.mLines;
      paramInt3 = arrayOfString.length;
      for (paramInt2 = 0; paramInt2 < paramInt3; paramInt2++) {
        String str = arrayOfString[paramInt2];
        canvas.drawText(str, computeX(str, textProperty.mMaxWidth, j) + f1, paramInt1 + f2, paint1);
        paramInt1 += textProperty.mHeightPerLine;
      } 
    } 
    initNativeObject(bitmap);
  }
  
  private static LinkedList<String> divideStringWithMaxWidth(String paramString, int paramInt, Paint paramPaint) {
    int k = paramString.length();
    LinkedList<String> linkedList = new LinkedList();
    int i = 1;
    int j;
    int m;
    for (j = 0; i <= k; j = m) {
      int i1 = (int)Math.ceil(paramPaint.measureText(paramString, j, i));
      int n = i;
      m = j;
      if (i1 >= paramInt) {
        m = paramString.substring(0, i).lastIndexOf(" ");
        if (m != -1 && m > j) {
          linkedList.add(paramString.substring(j, m));
          i = m + 1;
        } else if (i1 > paramInt) {
          linkedList.add(paramString.substring(j, i - 1));
          i--;
        } else {
          linkedList.add(paramString.substring(j, i));
        } 
        while (i < k && paramString.charAt(i) == ' ')
          i++; 
        m = i;
        n = i;
      } 
      i = n + 1;
    } 
    if (j < k)
      linkedList.add(paramString.substring(j)); 
    return linkedList;
  }
  
  private static int getFontSizeAccordingHeight(int paramInt) {
    Paint paint = new Paint();
    Rect rect = new Rect();
    paint.setTypeface(Typeface.DEFAULT);
    boolean bool = false;
    byte b = 1;
    while (!bool) {
      paint.setTextSize(b);
      paint.getTextBounds("SghMNy", 0, 6, rect);
      b++;
      if (paramInt - rect.height() <= 2)
        bool = true; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("incr size:");
      stringBuilder.append(b);
      Log.d("font size", stringBuilder.toString());
    } 
    return b;
  }
  
  private static byte[] getPixels(Bitmap paramBitmap) {
    if (paramBitmap != null) {
      byte[] arrayOfByte = new byte[paramBitmap.getWidth() * paramBitmap.getHeight() * 4];
      ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
      byteBuffer.order(ByteOrder.nativeOrder());
      paramBitmap.copyPixelsToBuffer(byteBuffer);
      return arrayOfByte;
    } 
    return null;
  }
  
  private static String getStringWithEllipsis(String paramString, float paramFloat1, float paramFloat2) {
    if (TextUtils.isEmpty(paramString))
      return ""; 
    TextPaint textPaint = new TextPaint();
    textPaint.setTypeface(Typeface.DEFAULT);
    textPaint.setTextSize(paramFloat2);
    return TextUtils.ellipsize(paramString, textPaint, paramFloat1, TextUtils.TruncateAt.END).toString();
  }
  
  private static void initNativeObject(Bitmap paramBitmap) {
    byte[] arrayOfByte = getPixels(paramBitmap);
    if (arrayOfByte == null)
      return; 
    nativeInitBitmapDC(paramBitmap.getWidth(), paramBitmap.getHeight(), arrayOfByte);
  }
  
  private static native void nativeInitBitmapDC(int paramInt1, int paramInt2, byte[] paramArrayOfbyte);
  
  private static Paint newPaint(String paramString, int paramInt1, int paramInt2) {
    Paint paint = new Paint();
    paint.setColor(-1);
    paint.setTextSize(paramInt1);
    paint.setAntiAlias(true);
    if (paramString.endsWith(".ttf")) {
      try {
        paint.setTypeface(Cocos2dxTypefaces.get(sContext, paramString));
      } catch (Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("error to create ttf type face: ");
        stringBuilder.append(paramString);
        Log.e("Cocos2dxBitmap", stringBuilder.toString());
        paint.setTypeface(Typeface.create(paramString, Typeface.NORMAL));
      } catch (Throwable e) {
//          throw new RuntimeException(e);
      }
    } else {
      paint.setTypeface(Typeface.create(paramString, Typeface.NORMAL));
    } 
    if (paramInt2 != 2) {
      if (paramInt2 != 3) {
        paint.setTextAlign(Paint.Align.LEFT);
      } else {
        paint.setTextAlign(Paint.Align.CENTER);
      } 
    } else {
      paint.setTextAlign(Paint.Align.RIGHT);
    } 
    return paint;
  }
  
  private static String refactorString(String paramString) {
    if (paramString.compareTo("") == 0)
      return " "; 
    StringBuilder stringBuilder = new StringBuilder(paramString);
    int i = 0;
    for (int j = stringBuilder.indexOf("\n"); j != -1; j = stringBuilder.indexOf("\n", i)) {
      if (j == 0 || stringBuilder.charAt(j - 1) == '\n') {
        stringBuilder.insert(i, " ");
        i = j + 2;
      } else {
        i = j + 1;
      } 
      if (i > stringBuilder.length() || j == stringBuilder.length())
        break; 
    } 
    return stringBuilder.toString();
  }
  
  public static void setContext(Context paramContext) {
    sContext = paramContext;
  }
  
  private static String[] splitString(String paramString, int paramInt1, int paramInt2, Paint paramPaint) {
    String[] arrayOfString1;
    String[] arrayOfString2 = paramString.split("\\n");
    Paint.FontMetricsInt fontMetricsInt = paramPaint.getFontMetricsInt();
    int j = paramInt2 / (int)Math.ceil((fontMetricsInt.bottom - fontMetricsInt.top));
    int i = 0;
    byte bool = 0;
    if (paramInt1 != 0) {
      LinkedList<String> linkedList = new LinkedList();
      i = arrayOfString2.length;
      for (paramInt2 = bool; paramInt2 < i; paramInt2++) {
        String str = arrayOfString2[paramInt2];
        if ((int)Math.ceil(paramPaint.measureText(str)) > paramInt1) {
          linkedList.addAll(divideStringWithMaxWidth(str, paramInt1, paramPaint));
        } else {
          linkedList.add(str);
        } 
        if (j > 0 && linkedList.size() >= j)
          break; 
      } 
      if (j > 0 && linkedList.size() > j)
        while (linkedList.size() > j)
          linkedList.removeLast();  
      arrayOfString1 = new String[linkedList.size()];
      linkedList.toArray(arrayOfString1);
    } else {
      arrayOfString1 = arrayOfString2;
      if (paramInt2 != 0) {
        arrayOfString1 = arrayOfString2;
        if (arrayOfString2.length > j) {
          LinkedList<String> linkedList = new LinkedList();
          for (paramInt1 = i; paramInt1 < j; paramInt1++)
            linkedList.add(arrayOfString2[paramInt1]); 
          arrayOfString1 = new String[linkedList.size()];
          linkedList.toArray(arrayOfString1);
        } 
      } 
    } 
    return arrayOfString1;
  }
  
  private static class TextProperty {
    private final int mHeightPerLine;
    
    private final String[] mLines;
    
    private final int mMaxWidth;
    
    private final int mTotalHeight;
    
    TextProperty(int param1Int1, int param1Int2, String[] param1ArrayOfString) {
      this.mMaxWidth = param1Int1;
      this.mHeightPerLine = param1Int2;
      this.mTotalHeight = param1Int2 * param1ArrayOfString.length;
      this.mLines = param1ArrayOfString;
    }
  }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxBitmap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */