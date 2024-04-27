package org.cocos2dx.lib;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

public class Cocos2dxHandler extends Handler {
  public static final int HANDLER_SHOW_DIALOG = 1;
  
  public static final int HANDLER_SHOW_EDITBOX_DIALOG = 2;
  
  private WeakReference<Cocos2dxActivity> mActivity;
  
  public Cocos2dxHandler(Cocos2dxActivity paramCocos2dxActivity) {
    this.mActivity = new WeakReference<Cocos2dxActivity>(paramCocos2dxActivity);
  }
  
  private void showDialog(Message paramMessage) {
    Cocos2dxActivity cocos2dxActivity = this.mActivity.get();
    DialogMessage dialogMessage = (DialogMessage)paramMessage.obj;
    (new AlertDialog.Builder((Context)cocos2dxActivity)).setTitle(dialogMessage.titile).setMessage(dialogMessage.message).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//          final Cocos2dxHandler this$0;
          
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {}
        }).create().show();
  }
  
  private void showEditBoxDialog(Message paramMessage) {
    EditBoxMessage editBoxMessage = (EditBoxMessage)paramMessage.obj;
    (new Cocos2dxEditBoxDialog((Context)this.mActivity.get(), editBoxMessage.title, editBoxMessage.content, editBoxMessage.inputMode, editBoxMessage.inputFlag, editBoxMessage.returnType, editBoxMessage.maxLength)).show();
  }
  
  public void handleMessage(Message paramMessage) {
    int i = paramMessage.what;
    if (i != 1) {
      if (i == 2)
        showEditBoxDialog(paramMessage); 
    } else {
      showDialog(paramMessage);
    } 
  }
  
  public static class DialogMessage {
    public String message;
    
    public String titile;
    
    public DialogMessage(String param1String1, String param1String2) {
      this.titile = param1String1;
      this.message = param1String2;
    }
  }
  
  public static class EditBoxMessage {
    public String content;
    
    public int inputFlag;
    
    public int inputMode;
    
    public int maxLength;
    
    public int returnType;
    
    public String title;
    
    public EditBoxMessage(String param1String1, String param1String2, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      this.content = param1String2;
      this.title = param1String1;
      this.inputMode = param1Int1;
      this.inputFlag = param1Int2;
      this.returnType = param1Int3;
      this.maxLength = param1Int4;
    }
  }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */