package com.xqj.games;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.IOException;
import org.json.JSONObject;

public class ResManager {
  public static final int Res_Begin = 1000;
  
  public static final int Res_CheckAPKVersion = 4;
  
  public static final int Res_CheckGameResVersion = 7;
  
  public static final int Res_CheckNetwork = 3;
  
  public static final int Res_DealOldRes = 2;
  
  public static final int Res_DownloadAPK = 5;
  
  public static final int Res_DownloadGameLib = 8;
  
  public static final int Res_DownloadGameRes = 9;
  
  public static final int Res_End = 1001;
  
  public static final int Res_EndToJump = 1002;
  
  public static final int Res_InstallAPK = 6;
  
  public static final int Res_UpdateProcess = 200;
  
  public static final int Res_UpdateTips = 100;
  
  public static final boolean isSimpleMode = false;
  
  public static final boolean isUse_ZIP_Download = true;
  
  public static float mCountPercent;
  
  public static float mIamgeTotalength;
  
  private boolean isbreakDraw = false;
  
  private Activity mActivity = null;
  
  private ImageView mCurImage = null;
  
  private TextView mCurPercentTextView = null;
  
  private RelativeLayout mCurProgressBarLayout = null;
  
  private TextView mCurTextView = null;
  
  private GameView mGameView = null;
  
  @SuppressLint("HandlerLeak")
  private Handler mHandler = new Handler() {
//      final ResManager this$0;
      
      public void handleMessage(Message param1Message) {
        try {
          int i = param1Message.what;
          if (i != 3) {
            if (i != 4) {
              if (i != 1000) {
                if (i == 1002) {
                  Log.d("xqjsdk", "=======Res_EndToJump========");
                  mOnMyCallBack.onReceive("", -1, null);
                } 
              } else {
                ResManager.mCountPercent = 0.0F;
                Log.d("xqjsdk", "=======Res_Begin========");
                mHandler.sendEmptyMessage(4);
              } 
            } else {
              Log.d("xqjsdk", "=======Res_CheckAPKVersion========");
              Message.obtain(mHandler, 100, "正在检测APK版本").sendToTarget();
              ResManager.mCountPercent = 0.06F;

              
              Activity activity = mActivity;
              OnMyCallBack onMyCallBack = new OnMyCallBack() {
//                  final ResManager.null this$1;
                  
                  public void onReceive(String param2String, int param2Int, JSONObject param2JSONObject) {
                    if (param2Int != 1)
                      mHandler.sendEmptyMessage(1002); 
                  }
                };
//              super(this);
//              this(activity, onMyCallBack);
//              this(checkAPKVersion);
              CheckAPKVersion checkAPKVersion = new CheckAPKVersion(activity, onMyCallBack);
              Thread thread = new Thread(checkAPKVersion);
              thread.start();
            } 
          } else {
            Message.obtain(mHandler, 100, "正在检测手机网络").sendToTarget();
            ResManager.mCountPercent = 0.04F;
            Log.d("xqjsdk", "=======Res_CheckNetwork========");

            OnMyCallBack onMyCallBack = new OnMyCallBack() {
//                final ResManager.null this$1;
                
                public void onReceive(String param2String, int param2Int, JSONObject param2JSONObject) {
                  if (param2Int == 1)
                    mHandler.sendEmptyMessage(4); 
                }
              };
//            super(this);
//            this(activity, onMyCallBack);
//            this(checkNetworkState);

            Activity activity = mActivity;
            CheckNetworkState checkNetworkState = new CheckNetworkState(activity, onMyCallBack);

            Thread thread = new Thread(checkNetworkState);
            thread.start();
          } 
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
      }
    };
  
  private Bitmap mLoadingbar = null;
  
  private OnMyCallBack mOnMyCallBack = null;
  
  private RelativeLayout mRelativeLayout = null;
  
  public ResManager(Activity paramActivity, RelativeLayout paramRelativeLayout) {
    this.mActivity = paramActivity;
    this.mRelativeLayout = paramRelativeLayout;
    this.isbreakDraw = false;
    mCountPercent = 0.0F;
  }
  
  private void init() {
    this.mCurTextView = new TextView((Context)this.mActivity);
    this.mCurTextView.setTextSize(18.0F);
    this.mCurTextView.setTextColor(-1);
    this.mCurTextView.setText("");
    this.mRelativeLayout.addView((View)this.mCurTextView, _tw(1280), _th(648));
    this.mCurTextView.setX(_tw(490));
    this.mCurTextView.setY(_th(630));
    this.mCurProgressBarLayout = new RelativeLayout((Context)this.mActivity);
    this.mRelativeLayout.addView((View)this.mCurProgressBarLayout, _tw(768), _th(20));
    this.mCurProgressBarLayout.setX(_tw(256));
    this.mCurProgressBarLayout.setY(_th(600));
  }
  
  public int _th(int paramInt) {
    return SuperTools.getInstance()._th(paramInt);
  }
  
  public int _tw(int paramInt) {
    return SuperTools.getInstance()._tw(paramInt);
  }
  
  public boolean run(OnMyCallBack paramOnMyCallBack) {
    if (paramOnMyCallBack == null || this.mActivity == null)
      return false; 
    this.mOnMyCallBack = paramOnMyCallBack;
    init();
    this.mHandler.sendEmptyMessage(1000);
    return true;
  }
  
  public void setPercent(float paramFloat) throws IOException {
    RelativeLayout relativeLayout = this.mCurProgressBarLayout;
    if (relativeLayout != null) {
      int i = (int)(paramFloat * mIamgeTotalength);
      relativeLayout.removeView((View)this.mCurImage);
      this.mCurProgressBarLayout.addView((View)this.mCurImage, i, _th(12));
      this.mCurImage.setX(_tw(7));
      this.mCurImage.setY(_th(4));
    } 
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\com\xqj\games\ResManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */