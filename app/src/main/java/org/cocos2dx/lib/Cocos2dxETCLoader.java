package org.cocos2dx.lib;

import android.content.Context;
import android.opengl.ETC1Util;
import android.util.Log;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Cocos2dxETCLoader {
    private static final String ASSETS_PATH = "assets/";

    private static Context context;

    public static boolean loadTexture(String paramString) {
        StringBuilder stringBuilder = null;
      ETC1Util.ETC1Texture eTC1Texture1 = null;
        if (!ETC1Util.isETC1Supported())
            return false;
        if (paramString.length() == 0)
            return false;
        String str = paramString;
        try {
            InputStream inputStream;
            if (paramString.charAt(0) == '/') {
                str = paramString;
                inputStream = new FileInputStream(paramString);
//        str = paramString;
//        this(paramString);
            } else {
                String str1 = paramString;
                str = paramString;
                if (paramString.startsWith("assets/")) {
                    str = paramString;
                    str1 = paramString.substring(7);
                }
                str = str1;
                InputStream inputStream1 = context.getAssets().open(str1);
                paramString = str1;
                inputStream = inputStream1;
            }
            str = paramString;
            ETC1Util.ETC1Texture eTC1Texture2 = ETC1Util.createTexture(inputStream);
            str = paramString;
            inputStream.close();
            eTC1Texture1 = eTC1Texture2;
        } catch (Exception exception) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to create texture for ");
            stringBuilder.append(str);
            Log.d("Cocos2dx", stringBuilder.toString());
            stringBuilder = null;
        }
        if (eTC1Texture1 != null) {
            boolean bool = true;
            try {
                int j = eTC1Texture1.getWidth();
                int i = eTC1Texture1.getHeight();
                int k = eTC1Texture1.getData().remaining();
                byte[] arrayOfByte = new byte[k];
                ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
                byteBuffer.order(ByteOrder.nativeOrder());
                byteBuffer.put(eTC1Texture1.getData());
                nativeSetTextureInfo(j, i, arrayOfByte, k);
            } catch (Exception exception) {
                Log.d("invoke native function error", exception.toString());
                bool = false;
            }
            return bool;
        }
        return false;
    }

    private static native void nativeSetTextureInfo(int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3);

    public static void setContext(Context paramContext) {
        context = paramContext;
    }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxETCLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */