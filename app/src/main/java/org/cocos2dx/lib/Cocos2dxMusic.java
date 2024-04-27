package org.cocos2dx.lib;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;
import java.io.FileInputStream;

public class Cocos2dxMusic {
  private static final String TAG = "Cocos2dxMusic";
  
  private MediaPlayer mBackgroundMediaPlayer;
  
  private final Context mContext;
  
  private String mCurrentPath;
  
  private boolean mIsLoop = false;
  
  private float mLeftVolume;
  
  private boolean mPaused;
  
  private float mRightVolume;
  
  public Cocos2dxMusic(Context paramContext) {
    this.mContext = paramContext;
    initData();
  }
  
  private MediaPlayer createMediaplayer(String paramString) {
    MediaPlayer mediaPlayer = new MediaPlayer();
    try {
      if (paramString.startsWith("/")) {
        FileInputStream fileInputStream = new FileInputStream(paramString);
//        this(paramString);
        mediaPlayer.setDataSource(fileInputStream.getFD());
        fileInputStream.close();
      } else {
        AssetFileDescriptor assetFileDescriptor = this.mContext.getAssets().openFd(paramString);
        mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
      } 
      mediaPlayer.prepare();
      mediaPlayer.setVolume(this.mLeftVolume, this.mRightVolume);
//      MediaPlayer mediaPlayer1 = mediaPlayer;
    } catch (Exception exception) {
      paramString = null;
      String str = TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("error: ");
      stringBuilder.append(exception.getMessage());
      Log.e(str, stringBuilder.toString(), exception);
    } 
    return mediaPlayer;
  }
  
  private void initData() {
    this.mLeftVolume = 0.5F;
    this.mRightVolume = 0.5F;
    this.mBackgroundMediaPlayer = null;
    this.mPaused = false;
    this.mCurrentPath = null;
  }
  
  public void end() {
    MediaPlayer mediaPlayer = this.mBackgroundMediaPlayer;
    if (mediaPlayer != null)
      mediaPlayer.release(); 
    initData();
  }
  
  public float getBackgroundVolume() {
    return (this.mBackgroundMediaPlayer != null) ? ((this.mLeftVolume + this.mRightVolume) / 2.0F) : 0.0F;
  }
  
  public boolean isBackgroundMusicPlaying() {
    boolean bool;
    MediaPlayer mediaPlayer = this.mBackgroundMediaPlayer;
    if (mediaPlayer == null) {
      bool = false;
    } else {
      bool = mediaPlayer.isPlaying();
    } 
    return bool;
  }
  
  public void pauseBackgroundMusic() {
    MediaPlayer mediaPlayer = this.mBackgroundMediaPlayer;
    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
      this.mBackgroundMediaPlayer.pause();
      this.mPaused = true;
    } 
  }
  
  public void playBackgroundMusic(String paramString, boolean paramBoolean) {
    String str = this.mCurrentPath;
    if (str == null) {
      this.mBackgroundMediaPlayer = createMediaplayer(paramString);
      this.mCurrentPath = paramString;
    } else if (!str.equals(paramString)) {
      MediaPlayer mediaPlayer1 = this.mBackgroundMediaPlayer;
      if (mediaPlayer1 != null)
        mediaPlayer1.release(); 
      this.mBackgroundMediaPlayer = createMediaplayer(paramString);
      this.mCurrentPath = paramString;
    } 
    MediaPlayer mediaPlayer = this.mBackgroundMediaPlayer;
    if (mediaPlayer == null) {
      Log.e(TAG, "playBackgroundMusic: background media player is null");
    } else {
      try {
        if (this.mPaused) {
          mediaPlayer.seekTo(0);
          this.mBackgroundMediaPlayer.start();
        } else if (mediaPlayer.isPlaying()) {
          this.mBackgroundMediaPlayer.seekTo(0);
        } else {
          this.mBackgroundMediaPlayer.start();
        } 
        this.mBackgroundMediaPlayer.setLooping(paramBoolean);
        this.mPaused = false;
        this.mIsLoop = paramBoolean;
      } catch (Exception exception) {
        Log.e(TAG, "playBackgroundMusic: error state");
      } 
    } 
  }
  
  public void preloadBackgroundMusic(String paramString) {
    String str = this.mCurrentPath;
    if (str == null || !str.equals(paramString)) {
      MediaPlayer mediaPlayer = this.mBackgroundMediaPlayer;
      if (mediaPlayer != null)
        mediaPlayer.release(); 
      this.mBackgroundMediaPlayer = createMediaplayer(paramString);
      this.mCurrentPath = paramString;
    } 
  }
  
  public void resumeBackgroundMusic() {
    MediaPlayer mediaPlayer = this.mBackgroundMediaPlayer;
    if (mediaPlayer != null && this.mPaused) {
      mediaPlayer.start();
      this.mPaused = false;
    } 
  }
  
  public void rewindBackgroundMusic() {
    if (this.mBackgroundMediaPlayer != null)
      playBackgroundMusic(this.mCurrentPath, this.mIsLoop); 
  }
  
  public void setBackgroundVolume(float paramFloat) {
    float f = paramFloat;
    if (paramFloat < 0.0F)
      f = 0.0F; 
    paramFloat = f;
    if (f > 1.0F)
      paramFloat = 1.0F; 
    this.mRightVolume = paramFloat;
    this.mLeftVolume = paramFloat;
    MediaPlayer mediaPlayer = this.mBackgroundMediaPlayer;
    if (mediaPlayer != null)
      mediaPlayer.setVolume(this.mLeftVolume, this.mRightVolume); 
  }
  
  public void stopBackgroundMusic() {
    MediaPlayer mediaPlayer = this.mBackgroundMediaPlayer;
    if (mediaPlayer != null) {
      mediaPlayer.release();
      this.mBackgroundMediaPlayer = createMediaplayer(this.mCurrentPath);
      this.mPaused = false;
    } 
  }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxMusic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */