package com.xqj.games;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

public class LoginActivity extends Activity {
  public static LoginActivity activity;
  
  public static LoginActivity getInstance() {
    return activity;
  }
  
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    activity = this;
    RelativeLayout relativeLayout = new RelativeLayout((Context)this);
    ThirdLogin.create(relativeLayout);
    setContentView((View)relativeLayout);
  }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\com\xqj\games\LoginActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */