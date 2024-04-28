package org.cocos2dx.lib;

import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Cocos2dxSound {
    private static final int INVALID_SOUND_ID = -1;

    private static final int INVALID_STREAM_ID = -1;

    public static final int MAX_SIMULTANEOUS_STREAMS_DEFAULT = 5;

    public static final int MAX_SIMULTANEOUS_STREAMS_I9100 = 3;

    private static final int SOUND_PRIORITY = 1;

    private static final int SOUND_QUALITY = 5;

    private static final float SOUND_RATE = 1.0F;

    private static final String TAG = "Cocos2dxSound";

    private final Context mContext;

    private final ArrayList<SoundInfoForLoadedCompleted> mEffecToPlayWhenLoadedArray = new ArrayList<SoundInfoForLoadedCompleted>();

    private float mLeftVolume;

    private final HashMap<String, Integer> mPathSoundIDMap = new HashMap<String, Integer>();

    private final HashMap<String, ArrayList<Integer>> mPathStreamIDsMap = new HashMap<String, ArrayList<Integer>>();

    private float mRightVolume;

    private Semaphore mSemaphore;

    private SoundPool mSoundPool;

    private int mStreamIdSyn;

    private int simultaneousStreams;

    public Cocos2dxSound(Context paramContext, int paramInt) {
        this.mContext = paramContext;
        initData(paramInt);
    }

    private int doPlayEffect(String paramString, int paramInt, boolean paramBoolean) {
        byte bool;
        SoundPool soundPool = this.mSoundPool;
        float f2 = this.mLeftVolume;
        float f1 = this.mRightVolume;
        if (paramBoolean) {
            bool = -1;
        } else {
            bool = 0;
        }
        paramInt = soundPool.play(paramInt, f2, f1, 1, bool, 1.0F);
        ArrayList<Integer> arrayList2 = this.mPathStreamIDsMap.get(paramString);
        ArrayList<Integer> arrayList1 = arrayList2;
        if (arrayList2 == null) {
            arrayList1 = new ArrayList();
            this.mPathStreamIDsMap.put(paramString, arrayList1);
        }
        arrayList1.add(Integer.valueOf(paramInt));
        return paramInt;
    }

    private void initData(int paramInt) {
        this.mSoundPool = new SoundPool(paramInt, 3, 5);
        this.mSoundPool.setOnLoadCompleteListener(new OnLoadCompletedListener(this));
        this.mLeftVolume = 0.5F;
        this.simultaneousStreams = paramInt;
        this.mRightVolume = 0.5F;
        this.mSemaphore = new Semaphore(0, true);
    }

    public int createSoundIDFromAsset(String paramString) {
        int b1;
        try {
            if (paramString.startsWith("/")) {
                b1 = mSoundPool.load(paramString, 0);
            } else {
                b1 = mSoundPool.load(mContext.getAssets().openFd(paramString), 0);
            }
        } catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("error: ");
            stringBuilder.append(exception.getMessage());
            Log.e("Cocos2dxSound", stringBuilder.toString(), exception);
            b1 = -1;
        }
        int b2 = b1;
        if (b1 == 0)
            b2 = -1;
        return b2;
    }

    public void end() {
        this.mSoundPool.release();
        this.mPathStreamIDsMap.clear();
        this.mPathSoundIDMap.clear();
        this.mEffecToPlayWhenLoadedArray.clear();
        this.mLeftVolume = 0.5F;
        this.mRightVolume = 0.5F;
        initData(this.simultaneousStreams);
    }

    public float getEffectsVolume() {
        return (this.mLeftVolume + this.mRightVolume) / 2.0F;
    }

    public void pauseAllEffects() {
        this.mSoundPool.autoPause();
    }

    public void pauseEffect(int paramInt) {
        this.mSoundPool.pause(paramInt);
    }

    public int playEffect(String paramString, boolean paramBoolean) {
        int i;
        Integer integer = this.mPathSoundIDMap.get(paramString);
        if (integer != null) {
            i = doPlayEffect(paramString, integer.intValue(), paramBoolean);
        } else {
            Integer integer1 = Integer.valueOf(preloadEffect(paramString));
            if (integer1.intValue() == -1)
                return -1;
            synchronized (this.mSoundPool) {
                ArrayList<SoundInfoForLoadedCompleted> arrayList = this.mEffecToPlayWhenLoadedArray;
                SoundInfoForLoadedCompleted soundInfoForLoadedCompleted = new SoundInfoForLoadedCompleted(paramString, integer1.intValue(), paramBoolean);
//        this(this, paramString, integer1.intValue(), paramBoolean);
                arrayList.add(soundInfoForLoadedCompleted);
                try {
                    this.mSemaphore.acquire();
                    i = this.mStreamIdSyn;
                    return i;
                } catch (Exception exception) {
                    return -1;
                }
            }
        }
        return i;
    }

    public int preloadEffect(String paramString) {
        Integer integer2 = this.mPathSoundIDMap.get(paramString);
        Integer integer1 = integer2;
        if (integer2 == null) {
            integer2 = Integer.valueOf(createSoundIDFromAsset(paramString));
            integer1 = integer2;
            if (integer2.intValue() != -1) {
                this.mPathSoundIDMap.put(paramString, integer2);
                integer1 = integer2;
            }
        }
        return integer1.intValue();
    }

    public void resumeAllEffects() {
        if (!this.mPathStreamIDsMap.isEmpty()) {
            Iterator iterator = mPathStreamIDsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Iterator<Integer> iterator1 = ((ArrayList) ((Map.Entry) iterator.next()).getValue()).iterator();
                while (iterator1.hasNext()) {
                    int i = ((Integer) iterator1.next()).intValue();
                    this.mSoundPool.resume(i);
                }
            }
        }
    }

    public void resumeEffect(int paramInt) {
        this.mSoundPool.resume(paramInt);
    }

    public void setEffectsVolume(float paramFloat) {
        float f = paramFloat;
        if (paramFloat < 0.0F)
            f = 0.0F;
        paramFloat = f;
        if (f > 1.0F)
            paramFloat = 1.0F;
        this.mRightVolume = paramFloat;
        this.mLeftVolume = paramFloat;
        if (!this.mPathStreamIDsMap.isEmpty()) {
            Iterator iterator = this.mPathStreamIDsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Iterator<Integer> iterator1 = ((ArrayList) ((Map.Entry) iterator.next()).getValue()).iterator();
                while (iterator1.hasNext()) {
                    int i = ((Integer) iterator1.next()).intValue();
                    this.mSoundPool.setVolume(i, this.mLeftVolume, this.mRightVolume);
                }
            }
        }
    }

    public void stopAllEffects() {
        if (!this.mPathStreamIDsMap.isEmpty()) {
            Iterator iterator = this.mPathStreamIDsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Iterator<Integer> iterator1 = ((ArrayList) ((Map.Entry) iterator.next()).getValue()).iterator();
                while (iterator1.hasNext()) {
                    int i = ((Integer) iterator1.next()).intValue();
                    this.mSoundPool.stop(i);
                }
            }
        }
        this.mPathStreamIDsMap.clear();
    }

    public void stopEffect(int paramInt) {
        this.mSoundPool.stop(paramInt);
        for (String str : this.mPathStreamIDsMap.keySet()) {
            if (((ArrayList) this.mPathStreamIDsMap.get(str)).contains(Integer.valueOf(paramInt))) {
                ((ArrayList) this.mPathStreamIDsMap.get(str)).remove(((ArrayList) this.mPathStreamIDsMap.get(str)).indexOf(Integer.valueOf(paramInt)));
                break;
            }
        }
    }

    public void unloadEffect(String paramString) {
        ArrayList arrayList = this.mPathStreamIDsMap.get(paramString);
        int a;
        if (arrayList != null) {
            Iterator var3 = arrayList.iterator();
            while (var3.hasNext()) {
                a = (Integer) var3.next();
                mSoundPool.stop(a);
            }
        }


        this.mPathStreamIDsMap.remove(paramString);
        Integer integer = this.mPathSoundIDMap.get(paramString);
        if (integer != null) {
            this.mSoundPool.unload(integer.intValue());
            this.mPathSoundIDMap.remove(paramString);
        }
    }

    public class OnLoadCompletedListener implements SoundPool.OnLoadCompleteListener {
        final Cocos2dxSound lcocos2dxSound;
        public OnLoadCompletedListener(Cocos2dxSound var1) {
            this.lcocos2dxSound = var1;
        }

        public void onLoadComplete(SoundPool param1SoundPool, int param1Int1, int param1Int2) {
            if (param1Int2 == 0) {
                Iterator<SoundInfoForLoadedCompleted> iterator = lcocos2dxSound.mEffecToPlayWhenLoadedArray.iterator();
                while (iterator.hasNext()) {
                    SoundInfoForLoadedCompleted soundInfoForLoadedCompleted = iterator.next();
                    if (param1Int1 == soundInfoForLoadedCompleted.soundID) {
                        lcocos2dxSound.doPlayEffect(soundInfoForLoadedCompleted.path, soundInfoForLoadedCompleted.soundID, soundInfoForLoadedCompleted.isLoop);
                        lcocos2dxSound.mEffecToPlayWhenLoadedArray.remove(soundInfoForLoadedCompleted);
                        break;
                    }
                }
            } else {
                lcocos2dxSound.mStreamIdSyn= -1;
            }
            lcocos2dxSound.mSemaphore.release();
        }
    }

    public class SoundInfoForLoadedCompleted {
        public boolean isLoop;

        public String path;

        public int soundID;

//    final Cocos2dxSound this$0;

        public SoundInfoForLoadedCompleted(String param1String, int param1Int, boolean param1Boolean) {
            this.path = param1String;
            this.soundID = param1Int;
            this.isLoop = param1Boolean;
        }
    }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxSound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */