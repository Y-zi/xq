package com.xqj.mytest;

import android.app.Activity;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.cocos2dx.lib.Cocos2dxHelper;

public class MyJobService extends JobService {
  private static final String TAG = "MyJobService";
  
  private static Activity mActivity;
  
  private Handler m_handler = new Handler(new Handler.Callback() {
//        final MyJobService this$0;
        
        public boolean handleMessage(Message param1Message) {
          boolean bool = Cocos2dxHelper.nativeOnJobService();
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("nativeOnJobService:");
          stringBuilder.append(bool);
          Log.e("MyJobService", stringBuilder.toString());
          MyJobService.this.jobFinished((JobParameters)param1Message.obj, false);
          return true;
        }
      });
  
  public static void setContent(Activity paramActivity) {
    mActivity = paramActivity;
  }
  
  public boolean onStartJob(JobParameters paramJobParameters) {
    Log.e("MyJobService", "onStartJob");
    Handler handler = this.m_handler;
    handler.sendMessageDelayed(Message.obtain(handler, 1, paramJobParameters), 10000L);
    return true;
  }
  
  public boolean onStopJob(JobParameters paramJobParameters) {
    Log.e("MyJobService", "onStopJob");
    this.m_handler.removeMessages(1);
    return true;
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\com\xqj\mytest\MyJobService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */