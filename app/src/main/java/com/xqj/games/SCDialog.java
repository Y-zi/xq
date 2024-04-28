package com.xqj.games;

import android.app.Activity;
import android.content.Context;
import org.json.JSONObject;

public class SCDialog {
  private ConfirmDialog mConfirmDialog = null;
  
  private OnMyClickedListener mOnMyClickedListener = null;
  
  public SCDialog(Activity paramActivity, String paramString1, String paramString2, String paramString3, String paramString4, OnMyClickedListener paramOnMyClickedListener) {
    this.mConfirmDialog = new ConfirmDialog((Context)paramActivity) {
//        final SCDialog this$0;
        
        public boolean onClickNegative() {
          if (SCDialog.this.mOnMyClickedListener != null)
            SCDialog.this.mOnMyClickedListener.onNegative(new JSONObject()); 
          return false;
        }
        
        public boolean onClickPositive() {
          if (SCDialog.this.mOnMyClickedListener != null)
            SCDialog.this.mOnMyClickedListener.onPositive(new JSONObject()); 
          return false;
        }
      };
    this.mOnMyClickedListener = paramOnMyClickedListener;
    if (isExist(paramString1))
      this.mConfirmDialog.setTitle(paramString1); 
    if (isExist(paramString2))
      this.mConfirmDialog.setMessage(paramString2); 
    if (isExist(paramString3))
      this.mConfirmDialog.setNegativeString(paramString3); 
    if (isExist(paramString4))
      this.mConfirmDialog.setPositiveString(paramString4); 
  }
  
  public static SCDialog create(Activity paramActivity, String paramString1, String paramString2, String paramString3, String paramString4, OnMyClickedListener paramOnMyClickedListener) {
    return new SCDialog(paramActivity, paramString1, paramString2, paramString3, paramString4, paramOnMyClickedListener);
  }
  
  private boolean isExist(String paramString) {
    return !(paramString == null || paramString.equals(""));
  }
  
  public void show() {
    ConfirmDialog confirmDialog = this.mConfirmDialog;
    if (confirmDialog != null)
      confirmDialog.show(); 
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\com\xqj\games\SCDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */