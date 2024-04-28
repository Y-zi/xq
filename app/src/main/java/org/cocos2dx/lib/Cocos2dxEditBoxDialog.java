package org.cocos2dx.lib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Cocos2dxEditBoxDialog extends Dialog {
  private final int kEditBoxInputFlagInitialCapsAllCharacters = 4;
  
  private final int kEditBoxInputFlagInitialCapsSentence = 3;
  
  private final int kEditBoxInputFlagInitialCapsWord = 2;
  
  private final int kEditBoxInputFlagPassword = 0;
  
  private final int kEditBoxInputFlagSensitive = 1;
  
  private final int kEditBoxInputModeAny = 0;
  
  private final int kEditBoxInputModeDecimal = 5;
  
  private final int kEditBoxInputModeEmailAddr = 1;
  
  private final int kEditBoxInputModeNumeric = 2;
  
  private final int kEditBoxInputModePhoneNumber = 3;
  
  private final int kEditBoxInputModeSingleLine = 6;
  
  private final int kEditBoxInputModeUrl = 4;
  
  private final int kKeyboardReturnTypeDefault = 0;
  
  private final int kKeyboardReturnTypeDone = 1;
  
  private final int kKeyboardReturnTypeGo = 4;
  
  private final int kKeyboardReturnTypeSearch = 3;
  
  private final int kKeyboardReturnTypeSend = 2;
  
  private EditText mInputEditText;
  
  private final int mInputFlag;
  
  private int mInputFlagConstraints;
  
  private final int mInputMode;
  
  private int mInputModeContraints;
  
  private boolean mIsMultiline;
  
  private final int mMaxLength;
  
  private final String mMessage;
  
  private final int mReturnType;
  
  private TextView mTextViewTitle;
  
  private final String mTitle;
  
  public Cocos2dxEditBoxDialog(Context paramContext, String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super(paramContext, 16973841);
    this.mTitle = paramString1;
    this.mMessage = paramString2;
    this.mInputMode = paramInt1;
    this.mInputFlag = paramInt2;
    this.mReturnType = paramInt3;
    this.mMaxLength = paramInt4;
  }
  
  private void closeKeyboard() {
    ((InputMethodManager)getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.mInputEditText.getWindowToken(), 0);
  }
  
  private int convertDipsToPixels(float paramFloat) {
    return Math.round(paramFloat * (getContext().getResources().getDisplayMetrics()).density);
  }
  
  private void openKeyboard() {
    ((InputMethodManager)getContext().getSystemService("input_method")).showSoftInput((View)this.mInputEditText, 0);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(-2147483648));
    LinearLayout linearLayout = new LinearLayout(getContext());
    linearLayout.setOrientation(1);
    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(-1, -1);
    this.mTextViewTitle = new TextView(getContext());
    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
    int i = convertDipsToPixels(10.0F);
    layoutParams2.rightMargin = i;
    layoutParams2.leftMargin = i;
    this.mTextViewTitle.setTextSize(1, 20.0F);
    linearLayout.addView((View)this.mTextViewTitle, (ViewGroup.LayoutParams)layoutParams2);
    this.mInputEditText = new EditText(getContext());
    layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
    i = convertDipsToPixels(10.0F);
    layoutParams2.rightMargin = i;
    layoutParams2.leftMargin = i;
    linearLayout.addView((View)this.mInputEditText, (ViewGroup.LayoutParams)layoutParams2);
    setContentView((View)linearLayout, (ViewGroup.LayoutParams)layoutParams1);
    getWindow().addFlags(1024);
    this.mTextViewTitle.setText(this.mTitle);
    this.mInputEditText.setText(this.mMessage);
    i = this.mInputEditText.getImeOptions();
    this.mInputEditText.setImeOptions(i | 0x10000000);
    i = this.mInputEditText.getImeOptions();
    switch (this.mInputMode) {
      case 6:
        this.mInputModeContraints = 1;
        break;
      case 5:
        this.mInputModeContraints = 12290;
        break;
      case 4:
        this.mInputModeContraints = 17;
        break;
      case 3:
        this.mInputModeContraints = 3;
        break;
      case 2:
        this.mInputModeContraints = 4098;
        break;
      case 1:
        this.mInputModeContraints = 33;
        break;
      case 0:
        this.mInputModeContraints = 131073;
        break;
    } 
    if (this.mIsMultiline)
      this.mInputModeContraints |= 0x20000; 
    this.mInputEditText.setInputType(this.mInputModeContraints | this.mInputFlagConstraints);
    int j = this.mInputFlag;
    if (j != 0) {
      if (j != 1) {
        if (j != 2) {
          if (j != 3) {
            if (j == 4)
              this.mInputFlagConstraints = 4096; 
          } else {
            this.mInputFlagConstraints = 16384;
          } 
        } else {
          this.mInputFlagConstraints = 8192;
        } 
      } else {
        this.mInputFlagConstraints = 524288;
      } 
    } else {
      this.mInputFlagConstraints = 129;
    } 
    this.mInputEditText.setInputType(this.mInputFlagConstraints | this.mInputModeContraints);
    j = this.mReturnType;
    if (j != 0) {
      if (j != 1) {
        if (j != 2) {
          if (j != 3) {
            if (j != 4) {
              this.mInputEditText.setImeOptions(i | 0x1);
            } else {
              this.mInputEditText.setImeOptions(i | 0x2);
            } 
          } else {
            this.mInputEditText.setImeOptions(i | 0x3);
          } 
        } else {
          this.mInputEditText.setImeOptions(i | 0x4);
        } 
      } else {
        this.mInputEditText.setImeOptions(i | 0x6);
      } 
    } else {
      this.mInputEditText.setImeOptions(i | 0x1);
    } 
    i = this.mMaxLength;
    if (i > 0)
      this.mInputEditText.setFilters(new InputFilter[] { (InputFilter)new InputFilter.LengthFilter(i) }); 
    (new Handler()).postDelayed(new Runnable() {
//          final Cocos2dxEditBoxDialog this$0;
          
          public void run() {
            Cocos2dxEditBoxDialog.this.mInputEditText.requestFocus();
            Cocos2dxEditBoxDialog.this.mInputEditText.setSelection(Cocos2dxEditBoxDialog.this.mInputEditText.length());
            Cocos2dxEditBoxDialog.this.openKeyboard();
          }
        },  200L);
    this.mInputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//          final Cocos2dxEditBoxDialog this$0;
          
          public boolean onEditorAction(TextView param1TextView, int param1Int, KeyEvent param1KeyEvent) {
            if (param1Int != 0 || (param1Int == 0 && param1KeyEvent != null && param1KeyEvent.getAction() == 0)) {
              Cocos2dxHelper.setEditTextDialogResult(Cocos2dxEditBoxDialog.this.mInputEditText.getText().toString());
              Cocos2dxEditBoxDialog.this.closeKeyboard();
              Cocos2dxEditBoxDialog.this.dismiss();
              return true;
            } 
            return false;
          }
        });
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxEditBoxDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */