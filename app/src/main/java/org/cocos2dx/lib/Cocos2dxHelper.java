package org.cocos2dx.lib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.xqj.games.SCDialog;
import com.xqj.games.SdkFunc;
import com.xqj.games.SuperTools;
import com.xqj.games.ThirdLogin;
import com.xqj.mytest.MyTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class Cocos2dxHelper {
    private static final String PREFS_NAME = "Cocos2dxPrefsFile";

    private static boolean sAccelerometerEnabled;

    private static AssetManager sAssetManager;

    private static Cocos2dxMusic sCocos2dMusic;

    private static Cocos2dxSound sCocos2dSound;

    private static Cocos2dxAccelerometer sCocos2dxAccelerometer;

    private static Cocos2dxHelperListener sCocos2dxHelperListener;

    private static Context sContext;

    private static String sFileDirectory;

    private static String sPackageName;

    private static boolean sUseExternalStorage;

    private static String strAssetsPath;

    private static String strDocPath;

    private static String strPatchPath;

    public static void disableAccelerometer() {
        sAccelerometerEnabled = false;
        sCocos2dxAccelerometer.disable();
    }

    public static void enableAccelerometer() {
        sAccelerometerEnabled = true;
        sCocos2dxAccelerometer.enable();
    }

    public static void end() {
        sCocos2dMusic.end();
        sCocos2dSound.end();
    }

    public static String getAbsolutePathOnExternalStorage(ApplicationInfo paramApplicationInfo, String paramString) {
        StringBuilder stringBuilder1;
        if (Build.VERSION.SDK_INT >= 29) {
            File file = ((Activity) sContext).getExternalFilesDir(null);
            stringBuilder1 = new StringBuilder();
            stringBuilder1.append(file.getAbsolutePath());
            stringBuilder1.append("/files/");
            stringBuilder1.append(paramString);
            return stringBuilder1.toString();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(Environment.getExternalStorageDirectory());
        stringBuilder2.append("/Android/data/");
        stringBuilder2.append(paramApplicationInfo.packageName);
        stringBuilder2.append("/files/");
        stringBuilder2.append(paramString);
        return stringBuilder2.toString();
    }

    public static AssetManager getAssetManager() {
        return sAssetManager;
    }

    public static float getBackgroundMusicVolume() {
        return sCocos2dMusic.getBackgroundVolume();
    }

    public static boolean getBoolForKey(String paramString, boolean paramBoolean) {
        return ((Activity) sContext).getSharedPreferences("Cocos2dxPrefsFile", 0).getBoolean(paramString, paramBoolean);
    }

    public static String getCocos2dxPackageName() {
        return sPackageName;
    }

    public static String getCocos2dxWritablePath() {
        return sFileDirectory;
    }

    public static String getCurrentLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static int getDPI() {
        if (sContext != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = ((Activity) sContext).getWindowManager();
            if (windowManager != null) {
                Display display = windowManager.getDefaultDisplay();
                if (display != null) {
                    display.getMetrics(displayMetrics);
                    return (int) (displayMetrics.density * 160.0F);
                }
            }
        }
        return -1;
    }

    public static String getDeviceModel() {
        return Build.MODEL;
    }

    public static double getDoubleForKey(String paramString, double paramDouble) {
        return sContext.getSharedPreferences("Cocos2dxPrefsFile", 0).getFloat(paramString, (float) paramDouble);
    }

    public static float getEffectsVolume() {
        return sCocos2dSound.getEffectsVolume();
    }

    public static String getExternalAssetPath() {
        return strAssetsPath;
    }

    public static float getFloatForKey(String paramString, float paramFloat) {
        return sContext.getSharedPreferences("Cocos2dxPrefsFile", 0).getFloat(paramString, paramFloat);
    }

    public static int getIntegerForKey(String paramString, int paramInt) {
        return sContext.getSharedPreferences("Cocos2dxPrefsFile", 0).getInt(paramString, paramInt);
    }

    public static String getStringForKey(String paramString1, String paramString2) {
        try {
            Runnable runnable;
            String[] arrayOfString1;
            StringBuilder stringBuilder1;
            String[] arrayOfString2;
            StringBuilder stringBuilder2 = new StringBuilder();
//      this();
            stringBuilder2.append("Cocos2dxhelper.getStringForKey::");
            stringBuilder2.append(paramString1);
            stringBuilder2.append("::");
            stringBuilder2.append(paramString2);
            Log.d("cocos", stringBuilder2.toString());
            if ("sdklogin".equals(paramString1)) {
                MyTest myTest = MyTest.getInstance();
                runnable = new Runnable() {
                    public void run() {
                        SCDialog.create((Activity) MyTest.getInstance(), "系统信息", "此功能暂未开放", "知道了", "", null).show();
                    }
                };
//        super();
                myTest.runOnUiThread(runnable);
                return "";
            }
            if (paramString1.startsWith("v_")) {
                if ("v_sdklogin_acc".equals(paramString1)) {
                    return ThirdLogin.nowAcc;
                } else if ("v_sdklogin_pass".equals(paramString1)) {
                    return ThirdLogin.nowPass;
                } else {
                    return "v_sdklogin_type".equals(paramString1) ? ThirdLogin.nowType : SuperTools.getInstance().readLocalFile(paramString1);
                }
            } else if (paramString1.startsWith("saveA_#")) {
                arrayOfString1 = paramString1.split("A_#");
                SuperTools.getInstance().writeLocalFile(arrayOfString1[1], arrayOfString1[2]);
                return "succ";
            } else if (paramString1.startsWith("cmd#")) {
                arrayOfString2 = paramString1.split("#");
                stringBuilder1 = new StringBuilder();
//        this();
                stringBuilder1.append("aa[1].equals(\"opensdkloginui\")==");
                stringBuilder1.append(arrayOfString2[1].equals("opensdkloginui"));
                Log.e("xqj", stringBuilder1.toString());
                if (arrayOfString2[1].equals("opensdkloginui"))
                    System.exit(0);
                return "";
            }else
            return sContext.getSharedPreferences("Cocos2dxPrefsFile", 0).getString(paramString1, paramString2);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }
    }

    public static void init(Context paramContext, Cocos2dxHelperListener paramCocos2dxHelperListener) {
        byte b;
        ApplicationInfo applicationInfo = paramContext.getApplicationInfo();
        sContext = paramContext;
        sCocos2dxHelperListener = paramCocos2dxHelperListener;
        sPackageName = applicationInfo.packageName;
        sFileDirectory = paramContext.getFilesDir().getAbsolutePath();
        nativeSetApkPath(applicationInfo.sourceDir);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(strAssetsPath);
        stringBuilder.append("/");
        nativeSetExternalAssetPath(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(strDocPath);
        stringBuilder.append("/");
        nativeSetExternalDocPath(stringBuilder.toString());
        Log.d("cocos2dx.initsetpath", strAssetsPath);
        Log.d("cocos2dx.initsetpath", strDocPath);
        sCocos2dxAccelerometer = new Cocos2dxAccelerometer(paramContext);
        sCocos2dMusic = new Cocos2dxMusic(paramContext);
        if (getDeviceModel().indexOf("GT-I9100") != -1) {
            b = 3;
        } else {
            b = 5;
        }
        sCocos2dSound = new Cocos2dxSound(paramContext, b);
        sAssetManager = paramContext.getAssets();
        Cocos2dxBitmap.setContext(paramContext);
        Cocos2dxETCLoader.setContext(paramContext);
    }

    public static void initResPath(Context paramContext, boolean paramBoolean) {
        sContext = paramContext;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(paramContext.getApplicationContext().getFilesDir());
        stringBuilder2.append("/resPath.dat");
        String str = stringBuilder2.toString();
        try {
            FileInputStream fileInputStream;
            String str1 = null;
            String str2 = null;
            File file = new File(str);
//            this(str);
            boolean bool = file.exists();
            str = "1";
            if (bool) {
                fileInputStream = new FileInputStream(file);
//                this(file);
                byte[] arrayOfByte = new byte[fileInputStream.available()];
                fileInputStream.read(arrayOfByte);
                str2 = new String(arrayOfByte);
//                this(arrayOfByte);
                int i = str2.indexOf(";");
                strAssetsPath = str2.substring(0, i);
                str1 = str2.substring(i + 1);
                i = str1.indexOf(";");
                strDocPath = str1.substring(0, i);
                sUseExternalStorage = str1.substring(i + 1).equals("1");
                fileInputStream.close();
            } else {
                String str3;
                if (!paramBoolean || !Environment.getExternalStorageState().equals("mounted")) {
                    StringBuilder stringBuilder3 = new StringBuilder();
//                    this();
                    stringBuilder3.append(paramContext.getApplicationContext().getFilesDir());
                    stringBuilder3.append("/assets");
                    strAssetsPath = stringBuilder3.toString();
                    stringBuilder3 = new StringBuilder();
//                    this();
                    stringBuilder3.append(paramContext.getApplicationContext().getFilesDir());
                    stringBuilder3.append("/doc");
                    strDocPath = stringBuilder3.toString();
                    sUseExternalStorage = false;
                } else {
                    ApplicationInfo applicationInfo = paramContext.getApplicationInfo();
                    strAssetsPath = getAbsolutePathOnExternalStorage(applicationInfo, "assets");
                    strDocPath = getAbsolutePathOnExternalStorage(applicationInfo, "doc");
                    sUseExternalStorage = true;
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
//                this((File) str2);
                StringBuilder stringBuilder = new StringBuilder();
//                this();
                stringBuilder.append(strAssetsPath);
                stringBuilder.append(";");
                stringBuilder.append(strDocPath);
                stringBuilder.append(";");
                if (sUseExternalStorage) {
                    str3 = str1;
                } else {
                    str3 = "0";
                }
                stringBuilder.append(str3);
                fileOutputStream.write(stringBuilder.toString().getBytes());
                fileOutputStream.close();
            }
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(strDocPath);
        stringBuilder1.append("/Patch");
        strPatchPath = stringBuilder1.toString();
        Log.d("cocos2dxhelperinitpath", strAssetsPath);
        Log.d("cocos2dxhelperinitpath", strDocPath);
    }

    public static boolean isBackgroundMusicPlaying() {
        return sCocos2dMusic.isBackgroundMusicPlaying();
    }

    public static boolean isUseExternalStorage() {
        return sUseExternalStorage;
    }

    public static native boolean nativeOnJobService();

    private static native void nativeSetApkPath(String paramString);

    private static native void nativeSetEditTextDialogResult(byte[] paramArrayOfbyte);

    private static native void nativeSetExternalAssetPath(String paramString);

    private static native void nativeSetExternalDocPath(String paramString);

    public static void onPause() {
        if (sAccelerometerEnabled)
            sCocos2dxAccelerometer.disable();
    }

    public static void onResume() {
        if (sAccelerometerEnabled)
            sCocos2dxAccelerometer.enable();
    }

    public static boolean openURL(String paramString) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("openURL:");
        stringBuilder.append(paramString);
        Log.e("Cocos2dxHelper", stringBuilder.toString());
        boolean bool = false;
        try {
            int i = paramString.indexOf("/game_pay.html?");
            byte b = -1;
            if (i != -1) {
                SdkFunc.pay(paramString);
                return true;
            }
            if (paramString.endsWith("allpay")) {
                SuperTools.getInstance().showErrorDialog("充值", "请到【汉中活动区】的 【寻秦夫人】处充值。 ");
                SuperTools.getInstance().showLoadingAlert((Activity) MyTest.getInstance(), "请到【汉中活动区】的 【寻秦夫人】处充值。 ");
                return true;
            }
            if (paramString.hashCode() == 282249071 && paramString.equals("sdklogin"))
                b = 0;
            if (b != 0) {
                Intent intent = new Intent("android.intent.action.VIEW");
//                this("android.intent.action.VIEW");
                intent.setData(Uri.parse(paramString));
                sContext.startActivity(intent);
            } else {
                ThirdLogin.callLoginUi();
            }
            bool = true;
        } catch (Exception exception) {
        }
        return bool;
    }

    public static void pauseAllEffects() {
        sCocos2dSound.pauseAllEffects();
    }

    public static void pauseBackgroundMusic() {
        sCocos2dMusic.pauseBackgroundMusic();
    }

    public static void pauseEffect(int paramInt) {
        sCocos2dSound.pauseEffect(paramInt);
    }

    public static void playBackgroundMusic(String paramString, boolean paramBoolean) {
        sCocos2dMusic.playBackgroundMusic(paramString, paramBoolean);
    }

    public static int playEffect(String paramString, boolean paramBoolean) {
        return sCocos2dSound.playEffect(paramString, paramBoolean);
    }

    public static void preloadBackgroundMusic(String paramString) {
        sCocos2dMusic.preloadBackgroundMusic(paramString);
    }

    public static void preloadEffect(String paramString) {
        sCocos2dSound.preloadEffect(paramString);
    }

    public static void resumeAllEffects() {
        sCocos2dSound.resumeAllEffects();
    }

    public static void resumeBackgroundMusic() {
        sCocos2dMusic.resumeBackgroundMusic();
    }

    public static void resumeEffect(int paramInt) {
        sCocos2dSound.resumeEffect(paramInt);
    }

    public static void rewindBackgroundMusic() {
        sCocos2dMusic.rewindBackgroundMusic();
    }

    public static void setAccelerometerInterval(float paramFloat) {
        sCocos2dxAccelerometer.setInterval(paramFloat);
    }

    public static void setBackgroundMusicVolume(float paramFloat) {
        sCocos2dMusic.setBackgroundVolume(paramFloat);
    }

    public static void setBoolForKey(String paramString, boolean paramBoolean) {
        SharedPreferences.Editor editor = ((Activity) sContext).getSharedPreferences("Cocos2dxPrefsFile", 0).edit();
        editor.putBoolean(paramString, paramBoolean);
        editor.commit();
    }

    public static void setDoubleForKey(String paramString, double paramDouble) {
        SharedPreferences.Editor editor = ((Activity) sContext).getSharedPreferences("Cocos2dxPrefsFile", 0).edit();
        editor.putFloat(paramString, (float) paramDouble);
        editor.commit();
    }

    public static void setEditTextDialogResult(String paramString) {
        try {
            byte[] var1 = paramString.getBytes("UTF8");
            Cocos2dxHelperListener var2 = sCocos2dxHelperListener;
            Runnable var4 = new Runnable() {
//                final byte[] val$bytesUTF8;
//
//                {
//                    this.val$bytesUTF8 = var1;
//                }

                public void run() {
                    Cocos2dxHelper.nativeSetEditTextDialogResult(var1);
                }
            };
            var2.runOnGLThread(var4);
        } catch (UnsupportedEncodingException var3) {
        }

    }

    public static void setEffectsVolume(float paramFloat) {
        sCocos2dSound.setEffectsVolume(paramFloat);
    }

    public static void setFloatForKey(String paramString, float paramFloat) {
        SharedPreferences.Editor editor = ((Activity) sContext).getSharedPreferences("Cocos2dxPrefsFile", 0).edit();
        editor.putFloat(paramString, paramFloat);
        editor.commit();
    }

    public static void setIntegerForKey(String paramString, int paramInt) {
        SharedPreferences.Editor editor = ((Activity) sContext).getSharedPreferences("Cocos2dxPrefsFile", 0).edit();
        editor.putInt(paramString, paramInt);
        editor.commit();
    }

    public static void setStringForKey(String paramString1, String paramString2) {
        SharedPreferences.Editor editor = ((Activity) sContext).getSharedPreferences("Cocos2dxPrefsFile", 0).edit();
        editor.putString(paramString1, paramString2);
        editor.commit();
    }

    private static void showDialog(String paramString1, String paramString2) {
        sCocos2dxHelperListener.showDialog(paramString1, paramString2);
    }

    private static void showEditTextDialog(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        sCocos2dxHelperListener.showEditTextDialog(paramString1, paramString2, paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public static void stopAllEffects() {
        sCocos2dSound.stopAllEffects();
    }

    public static void stopBackgroundMusic() {
        sCocos2dMusic.stopBackgroundMusic();
    }

    public static void stopEffect(int paramInt) {
        sCocos2dSound.stopEffect(paramInt);
    }

    public static void terminateProcess() {
        Process.killProcess(Process.myPid());
    }

    public static void unloadEffect(String paramString) {
        sCocos2dSound.unloadEffect(paramString);
    }

    public static interface Cocos2dxHelperListener {
        void clearBackground();

        void onRenderPause();

        void onRenderResume();

        void runOnGLThread(Runnable param1Runnable);

        void showDialog(String param1String1, String param1String2);

        void showEditTextDialog(String param1String1, String param1String2, int param1Int1, int param1Int2, int param1Int3, int param1Int4);
    }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */