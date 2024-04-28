package org.cocos2dx.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class Cocos2dxEditText extends EditText {
  private Cocos2dxGLSurfaceView mCocos2dxGLSurfaceView;
  
  public Cocos2dxEditText(Context paramContext) {
    super(paramContext);
  }
  
  public Cocos2dxEditText(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public Cocos2dxEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    super.onKeyDown(paramInt, paramKeyEvent);
    if (paramInt == 4)
      this.mCocos2dxGLSurfaceView.requestFocus(); 
    return true;
  }
  
  public void setCocos2dxGLSurfaceView(Cocos2dxGLSurfaceView paramCocos2dxGLSurfaceView) {
    this.mCocos2dxGLSurfaceView = paramCocos2dxGLSurfaceView;
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxEditText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */