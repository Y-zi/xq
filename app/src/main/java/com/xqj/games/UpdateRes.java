package com.xqj.games;

import android.widget.RelativeLayout;
import org.json.JSONObject;

public class UpdateRes extends Template_Page {
  public void onCreate(MainActivity paramMainActivity, final RelativeLayout l, OnMyCallBack paramOnMyCallBack) {
    (new ResManager(paramMainActivity, l)).run(new OnMyCallBack() {
//          final UpdateRes this$0;
//
//          final RelativeLayout val$l;
          
          public void onReceive(String param1String, int param1Int, JSONObject param1JSONObject) {
            ThirdLogin.create(l);
          }
        });
  }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\com\xqj\games\UpdateRes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */