package com.xqj.games;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetworkState implements Runnable, RMConstDefine {
  private Activity mActivity = null;
  
  private OnMyCallBack mOnMyCallBack = null;
  
  public CheckNetworkState(Activity paramActivity, OnMyCallBack paramOnMyCallBack) {
    this.mActivity = paramActivity;
    this.mOnMyCallBack = paramOnMyCallBack;
  }
  
  public static boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager = (ConnectivityManager)SuperTools.getInstance().getCurActivity().getSystemService("connectivity");
    NetworkInfo.State state = connectivityManager.getNetworkInfo(1).getState();
    NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
    return (networkInfo != null && (networkInfo.getState() == NetworkInfo.State.CONNECTED || networkInfo.getState() == NetworkInfo.State.CONNECTING)) ? true : ((state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING));
  }
  
  public void run() {
    if (this.mActivity == null || this.mOnMyCallBack == null) {
      this.mOnMyCallBack.onReceive("error", 0, null);
      return;
    } 
    if (isNetworkAvailable()) {
      this.mOnMyCallBack.onReceive("yes", 1, null);
    } else {
      SuperTools.getInstance().showErrorDialog("网络", "网络连接异常，请联系管理员");
    } 
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\com\xqj\games\CheckNetworkState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */