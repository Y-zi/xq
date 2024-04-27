package com.xqj.games;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

public class PayActivity extends Activity {
  public static PayActivity activity;
  
  public WebView web = null;
  
  public static PayActivity getInstance() {
    return activity;
  }
  
  public void gourl(String paramString) {
    this.web.loadUrl(paramString);
  }
  
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    activity = this;
    this.web = new WebView((Context)this);
    this.web.getSettings().setJavaScriptEnabled(true);
    setContentView((View)this.web);
    gourl(paramBundle.getString("url"));
  }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\com\xqj\games\PayActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */