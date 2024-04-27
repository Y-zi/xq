package com.xqj.mytest;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

public class MyJobScheduler {
  private static final int JOB_ID = 123;
  
  public static void cancelScheduleJob(Context paramContext) {
    ((JobScheduler)paramContext.getSystemService("jobscheduler")).cancel(123);
  }
  
  public static void scheduleJob(Context paramContext) {
    JobInfo.Builder builder = new JobInfo.Builder(123, new ComponentName(paramContext, MyJobService.class));
    if (Build.VERSION.SDK_INT >= 24) {
      builder.setMinimumLatency(10000L);
      builder.setOverrideDeadline(15000L);
      builder.setBackoffCriteria(10000L, 0);
    } else {
      builder.setPeriodic(10000L);
    } 
    builder.setPersisted(false);
    builder.setRequiredNetworkType(1);
    builder.setRequiresCharging(true);
    builder.setTriggerContentMaxDelay(20000L);
    ((JobScheduler)paramContext.getSystemService("jobscheduler")).schedule(builder.build());
  }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\com\xqj\mytest\MyJobScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */