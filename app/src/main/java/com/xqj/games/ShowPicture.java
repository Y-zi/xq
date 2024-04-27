package com.xqj.games;

import android.widget.RelativeLayout;
import org.json.JSONObject;

public class ShowPicture extends Template_Page {
  public void onCreate(MainActivity paramMainActivity, RelativeLayout paramRelativeLayout, OnMyCallBack paramOnMyCallBack) {
    SuperTools.delayAction(200, new OnMyCallBack() {
//          final ShowPicture this$0;
          
          public void onReceive(String param1String, int param1Int, JSONObject param1JSONObject) {
            MainActivity.switchStatus(2);
          }
        });
  }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\com\xqj\games\ShowPicture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */