package com.xqj.games;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public abstract class ConfirmDialog extends AlertDialog.Builder {
  public ConfirmDialog(Context paramContext) {
    super(paramContext);
    setCancelable(false);
  }
  
  public abstract boolean onClickNegative();
  
  public abstract boolean onClickPositive();
  
  public void setNegativeString(String paramString) {
    setNegativeButton(paramString, new DialogInterface.OnClickListener() {
//          final ConfirmDialog this$0;
          
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            if (ConfirmDialog.this.onClickNegative())
              param1DialogInterface.dismiss(); 
          }
        });
  }
  
  public void setPositiveString(String paramString) {
    setPositiveButton(paramString, new DialogInterface.OnClickListener() {
//          final ConfirmDialog this$0;
          
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            if (ConfirmDialog.this.onClickPositive())
              param1DialogInterface.dismiss(); 
          }
        });
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\com\xqj\games\ConfirmDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */