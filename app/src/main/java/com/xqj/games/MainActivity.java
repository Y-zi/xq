package com.xqj.games;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import java.util.Random;
import org.json.JSONObject;

public class MainActivity extends Activity {
  public static final int S_Game = 3;
  
  public static final int S_ShowPic = 1;
  
  public static final int S_UpdateRes = 2;
  
  private static boolean isExiting = false;
  
  public static MainActivity mActivity;
  
  private static RelativeLayout mRelativeLayout;
  
  private String[] bgs = new String[] { "bg.png", "bg.png" };
  
  private int mCurPageIndex = -1;
  
  protected RelativeLayout mFrameLayout = null;
  
  private Handler mHandler = new Handler() {
//      final MainActivity this$0;
      
      public void dispatchMessage(Message param1Message) {
        if (param1Message.what == 1001)
          MainActivity.this.setStatus(param1Message.arg1); 
      }
    };
  
  private int mHeight = 0;
  
  private int mLastPageIndex = -1;
  
  private OnMyCallBack mOnMyCallBack = new OnMyCallBack() {
//      final MainActivity this$0;
      
      public void onReceive(String param1String, int param1Int, JSONObject param1JSONObject) {}
    };
  
  private PageData[] mPageDatas = new PageData[] { new PageData(1, new ShowPicture(), this.bgs[(new Random()).nextInt(2)]), new PageData(2, new UpdateRes(), this.bgs[(new Random()).nextInt(2)]), new PageData(3, new Game(), "") };
  
  private int mWidth = 0;
  
  private void addCurView(PageData paramPageData) {
    if (paramPageData.pageLayout == null)
      paramPageData.pageLayout = new RelativeLayout((Context)mActivity); 
    paramPageData.pageLayout.removeAllViews();
    mRelativeLayout.addView((View)paramPageData.pageLayout, new ViewGroup.LayoutParams(this.mWidth, this.mHeight));
  }
  
  private void addPageBG(PageData paramPageData) {
    if (paramPageData != null && paramPageData.pageLayout != null && !paramPageData.pageBG.equals("")) {
      GameView gameView = new GameView(mActivity, null, new String[] { paramPageData.pageBG });
      paramPageData.pageLayout.addView(gameView, new ViewGroup.LayoutParams(this.mWidth, this.mHeight));
    } 
  }
  
  public static RelativeLayout getCurLayout() {
    MainActivity mainActivity = mActivity;
    PageData pageData = mainActivity.getPageData(mainActivity.mCurPageIndex);
    return (pageData != null) ? pageData.pageLayout : null;
  }
  
  public static int getCurPageIndex() {
    return mActivity.mCurPageIndex;
  }
  
  private PageData getPageData(int paramInt) {
    byte b = 0;
    while (true) {
      PageData[] arrayOfPageData = this.mPageDatas;
      if (b < arrayOfPageData.length) {
        if (paramInt == (arrayOfPageData[b]).pageIndex)
          return this.mPageDatas[b]; 
        b++;
        continue;
      } 
      return null;
    } 
  }
  
  private void removeLastView(final PageData data) {
    if (data.pageLayout == null)
      return; 
    SuperTools.delayAction(500, new OnMyCallBack() {
//          final MainActivity this$0;
          
//          final PageData val$data;
          
          public void onReceive(String param1String, int param1Int, JSONObject param1JSONObject) {
            MainActivity.mActivity.runOnUiThread(new Runnable() {
//                  final MainActivity.null this$1;
                  
                  public void run() {
                    MainActivity.mRelativeLayout.removeView((View)data.pageLayout);
                  }
                });
          }
        });
  }
  
  private void setStatus(int paramInt) {
    PageData pageData = getPageData(this.mLastPageIndex);
    if (pageData != null)
      removeLastView(pageData); 
    pageData = getPageData(paramInt);
    if (pageData != null) {
      this.mLastPageIndex = this.mCurPageIndex;
      this.mCurPageIndex = paramInt;
      if (pageData.pageIndex == 3)
        continuteOnCreate(); 
      addCurView(pageData);
      addPageBG(pageData);
      pageData.pageClass.onCreate(mActivity, pageData.pageLayout, this.mOnMyCallBack);
    } 
  }
  
  public static void switchStatus(int paramInt) {
    mActivity.mHandler.obtainMessage(1001, paramInt, -1).sendToTarget();
  }
  
  protected void addViewToFrameLayout(RelativeLayout paramRelativeLayout) {
    if (paramRelativeLayout == null)
      return; 
    paramRelativeLayout.removeAllViews();
    this.mFrameLayout.removeView((View)paramRelativeLayout);
    this.mFrameLayout.addView((View)paramRelativeLayout);
  }
  
  protected void continuteOnCreate() {}
  
  public void init() {
    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
    this.mFrameLayout = new RelativeLayout((Context)this);
    this.mFrameLayout.setLayoutParams(layoutParams);
  }
  
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    mActivity = this;
    SuperTools.getInstance().setScreenOrientationLandscape(this);
    SuperTools.getInstance().setCurActivity(mActivity);
    this.mWidth = SuperTools.getInstance().getWidth();
    this.mHeight = SuperTools.getInstance().getHeight();
    mRelativeLayout = new RelativeLayout((Context)mActivity);
    mActivity.setContentView((View)mRelativeLayout, new ViewGroup.LayoutParams(this.mWidth, this.mHeight));
    setStatus(1);
    init();
  }
  
  private class PageData {
    String pageBG;
    
    Template_Page pageClass;
    
    int pageIndex;
    
    RelativeLayout pageLayout;
    
//    final MainActivity this$0;
    
    PageData(int param1Int, Template_Page param1Template_Page, String param1String) {
      this.pageIndex = param1Int;
      this.pageClass = param1Template_Page;
      this.pageLayout = null;
      this.pageBG = param1String;
    }
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\com\xqj\games\MainActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */