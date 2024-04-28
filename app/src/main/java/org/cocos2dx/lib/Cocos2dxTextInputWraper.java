package org.cocos2dx.lib;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class Cocos2dxTextInputWraper implements TextWatcher, TextView.OnEditorActionListener {
  private static final String TAG = "Cocos2dxTextInputWraper";
  
  private final Cocos2dxGLSurfaceView mCocos2dxGLSurfaceView;
  
  private String mOriginText;
  
  private String mText;
  
  public Cocos2dxTextInputWraper(Cocos2dxGLSurfaceView paramCocos2dxGLSurfaceView) {
    this.mCocos2dxGLSurfaceView = paramCocos2dxGLSurfaceView;
  }
  
  private boolean isFullScreenEdit() {
    return ((InputMethodManager)this.mCocos2dxGLSurfaceView.getCocos2dxEditText().getContext().getSystemService("input_method")).isFullscreenMode();
  }
  
  public void afterTextChanged(Editable paramEditable) {
    if (isFullScreenEdit())
      return; 
    int j = paramEditable.length() - this.mText.length();
    int i = j;
    if (j > 0) {
      String str = paramEditable.subSequence(this.mText.length(), paramEditable.length()).toString();
      this.mCocos2dxGLSurfaceView.insertText(str);
    } else {
      while (i < 0) {
        this.mCocos2dxGLSurfaceView.deleteBackward();
        i++;
      } 
    } 
    this.mText = paramEditable.toString();
  }
  
  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
    this.mText = paramCharSequence.toString();
  }
  
  public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent) {
    if (this.mCocos2dxGLSurfaceView.getCocos2dxEditText() == paramTextView && isFullScreenEdit()) {
      for (int i = this.mOriginText.length(); i > 0; i--)
        this.mCocos2dxGLSurfaceView.deleteBackward(); 
      String str2 = paramTextView.getText().toString();
      String str1 = str2;
      if (str2.compareTo("") == 0)
        str1 = "\n"; 
      str2 = str1;
      if ('\n' != str1.charAt(str1.length() - 1)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str1);
        stringBuilder.append('\n');
        str2 = stringBuilder.toString();
      } 
      this.mCocos2dxGLSurfaceView.insertText(str2);
    } 
    if (paramInt == 6)
      this.mCocos2dxGLSurfaceView.requestFocus(); 
    return false;
  }
  
  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void setOriginText(String paramString) {
    this.mOriginText = paramString;
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxTextInputWraper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */