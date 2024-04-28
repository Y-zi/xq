package com.xqj.games.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.RelativeLayout;

public class BorderRelativeLayout extends RelativeLayout {
  private DisplayMetrics displayMetrics;
  
  private boolean isNeedBottomBorder = true;
  
  private boolean isNeedLeftBorder = false;
  
  private boolean isNeedRightBorder = true;
  
  private boolean isNeedTopBorder = false;
  
  private int mBorderBottomLeftBreakSize;
  
  private int mBorderBottomRightBreakSize;
  
  private float mBorderStrokeWidth = 1.0F;
  
  private Paint mPain;
  
  private int mPaintColor;
  
  public BorderRelativeLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public BorderRelativeLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public BorderRelativeLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    this.displayMetrics = paramContext.getResources().getDisplayMetrics();
    init();
  }
  
  private void init() {
    this.mPain = new Paint();
    this.mPain.setColor(Color.parseColor("#000000"));
    this.mPain.setAntiAlias(true);
    this.mPain.setStrokeWidth(this.mBorderStrokeWidth);
  }
  
  protected void dispatchDraw(Canvas paramCanvas) {
    super.dispatchDraw(paramCanvas);
    if (this.isNeedTopBorder)
      paramCanvas.drawLine(0.0F, 0.0F, getWidth(), 0.0F, this.mPain); 
    if (this.isNeedBottomBorder)
      paramCanvas.drawLine(this.mBorderBottomLeftBreakSize, getHeight(), (getWidth() - this.mBorderBottomRightBreakSize), getHeight(), this.mPain); 
    if (this.isNeedLeftBorder)
      paramCanvas.drawLine(0.0F, 0.0F, 0.0F, getHeight(), this.mPain); 
    if (this.isNeedRightBorder)
      paramCanvas.drawLine(getWidth(), 0.0F, getWidth(), getHeight(), this.mPain); 
  }
  
  public void setBorderColor(int paramInt) {
    if (this.mPain.getColor() == paramInt)
      return; 
    this.mPain.setColor(paramInt);
    invalidate();
  }
  
  public void setBorderState(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
    this.isNeedTopBorder = paramBoolean1;
    this.isNeedBottomBorder = paramBoolean2;
    this.isNeedLeftBorder = paramBoolean3;
    this.isNeedRightBorder = paramBoolean4;
    invalidate();
  }
  
  public void setBorderStrokeWidth(float paramFloat) {
    if (this.mPain.getStrokeWidth() == paramFloat)
      return; 
    this.mPain.setStrokeWidth(TypedValue.applyDimension(1, paramFloat, this.displayMetrics));
    invalidate();
  }
  
  public void setNeedBottomBorder(boolean paramBoolean) {
    this.isNeedBottomBorder = paramBoolean;
    invalidate();
  }
  
  public void setNeedLeftBorder(boolean paramBoolean) {
    this.isNeedLeftBorder = paramBoolean;
    invalidate();
  }
  
  public void setNeedRightBorder(boolean paramBoolean) {
    this.isNeedRightBorder = paramBoolean;
    invalidate();
  }
  
  public void setNeedTopBorder(boolean paramBoolean) {
    this.isNeedTopBorder = paramBoolean;
    invalidate();
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\com\xqj\game\\utils\BorderRelativeLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */