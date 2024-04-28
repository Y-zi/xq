package com.xqj.mytest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtils {
  public static void showConfirmationDialog(Context paramContext, String paramString1, String paramString2, final ConfirmationDialogCallback callback) {
    AlertDialog.Builder builder = new AlertDialog.Builder(paramContext);
    builder.setTitle(paramString1);
    builder.setMessage(paramString2);
    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//          final ConfirmationDialogCallback val$callback;
          
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            ConfirmationDialogCallback confirmationDialogCallback = callback;
            if (confirmationDialogCallback != null)
              confirmationDialogCallback.onConfirm(); 
          }
        });
    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//          final ConfirmationDialogCallback val$callback;
          
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            ConfirmationDialogCallback confirmationDialogCallback = callback;
            if (confirmationDialogCallback != null)
              confirmationDialogCallback.onCancel(); 
          }
        });
    builder.create().show();
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\com\xqj\mytest\DialogUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */