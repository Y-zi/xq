package org.cocos2dx.lib;

import android.content.Context;
import android.graphics.Typeface;
import java.util.HashMap;

public class Cocos2dxTypefaces {
  private static final HashMap<String, Typeface> sTypefaceCache = new HashMap<String, Typeface>();
  
  public static Typeface get(Context var0, String var1) throws Throwable {
    synchronized(Cocos2dxTypefaces.class){}

    Typeface var22;
    label205: {
      Throwable var10000;
      label204: {
        boolean var10001;
        label203: {
          label210: {
            try {
              if (sTypefaceCache.containsKey(var1)) {
                break label203;
              }

              if (var1.startsWith("/")) {
                var22 = Typeface.createFromFile(var1);
                break label210;
              }
            } catch (Throwable var21) {
              var10000 = var21;
              var10001 = false;
              break label204;
            }

            try {
              var22 = Typeface.createFromAsset(var0.getAssets(), var1);
            } catch (Throwable var20) {
              var10000 = var20;
              var10001 = false;
              break label204;
            }
          }

          try {
            sTypefaceCache.put(var1, var22);
          } catch (Throwable var19) {
            var10000 = var19;
            var10001 = false;
            break label204;
          }
        }

        label189:
        try {
          var22 = (Typeface)sTypefaceCache.get(var1);
          break label205;
        } catch (Throwable var18) {
          var10000 = var18;
          var10001 = false;
          break label189;
        }
      }

      Throwable var23 = var10000;
      throw var23;
    }

    return var22;
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxTypefaces.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */