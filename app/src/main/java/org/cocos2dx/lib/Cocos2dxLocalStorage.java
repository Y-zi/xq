package org.cocos2dx.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Cocos2dxLocalStorage {
  private static String DATABASE_NAME = "jsb.sqlite";
  
  private static final int DATABASE_VERSION = 1;
  
  private static String TABLE_NAME = "data";
  
  private static final String TAG = "Cocos2dxLocalStorage";
  
  private static SQLiteDatabase mDatabase;
  
  private static DBOpenHelper mDatabaseOpenHelper;
  
  public static void destory() {
    SQLiteDatabase sQLiteDatabase = mDatabase;
    if (sQLiteDatabase != null)
      sQLiteDatabase.close(); 
  }
  
  @SuppressLint("Range")
  public static String getItem(String paramString) {
    String str1 = null;
    Cursor cursor2 = null;
    String str3 = null;
    Cursor cursor1 = cursor2;
    try {
      StringBuilder stringBuilder = new StringBuilder();
      cursor1 = cursor2;
//      this();
      cursor1 = cursor2;
      stringBuilder.append("select value from ");
      cursor1 = cursor2;
      stringBuilder.append(TABLE_NAME);
      cursor1 = cursor2;
      stringBuilder.append(" where key=?");
      cursor1 = cursor2;
      String str = stringBuilder.toString();
      cursor1 = cursor2;
      cursor2 = mDatabase.rawQuery(str, new String[] { paramString });
      paramString = str3;
      while (true) {
        String str4 = paramString;
        if (cursor2.moveToNext()) {
          if (paramString != null) {
            str4 = paramString;
            Log.e("Cocos2dxLocalStorage", "The key contains more than one value.");
            break;
          } 
          str4 = paramString;
          paramString = cursor2.getString(cursor2.getColumnIndex("value"));
          continue;
        } 
        break;
      }
      str3 = paramString;
      cursor2.close();
    } catch (Exception exception) {
      exception.printStackTrace();
      str1 = str3;
    } 
    String str2 = str1;
    if (str1 == null)
      str2 = ""; 
    return str2;
  }
  
  public static boolean init(String paramString1, String paramString2) {
    if (Cocos2dxActivity.getContext() != null) {
      DATABASE_NAME = paramString1;
      TABLE_NAME = paramString2;
      mDatabaseOpenHelper = new DBOpenHelper(Cocos2dxActivity.getContext());
      mDatabase = mDatabaseOpenHelper.getWritableDatabase();
      return true;
    } 
    return false;
  }
  
  public static void removeItem(String paramString) {
    try {
      StringBuilder stringBuilder = new StringBuilder();
//      this();
      stringBuilder.append("delete from ");
      stringBuilder.append(TABLE_NAME);
      stringBuilder.append(" where key=?");
      String str = stringBuilder.toString();
      mDatabase.execSQL(str, new Object[] { paramString });
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public static void setItem(String paramString1, String paramString2) {
    try {
      StringBuilder stringBuilder = new StringBuilder();
//      this();
      stringBuilder.append("replace into ");
      stringBuilder.append(TABLE_NAME);
      stringBuilder.append("(key,value)values(?,?)");
      String str = stringBuilder.toString();
      mDatabase.execSQL(str, new Object[] { paramString1, paramString2 });
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private static class DBOpenHelper extends SQLiteOpenHelper {
    DBOpenHelper(Context param1Context) {
      super(param1Context, Cocos2dxLocalStorage.DATABASE_NAME, null, 1);
    }
    
    public void onCreate(SQLiteDatabase param1SQLiteDatabase) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("CREATE TABLE IF NOT EXISTS ");
      stringBuilder.append(Cocos2dxLocalStorage.TABLE_NAME);
      stringBuilder.append("(key TEXT PRIMARY KEY,value TEXT);");
      param1SQLiteDatabase.execSQL(stringBuilder.toString());
    }
    
    public void onUpgrade(SQLiteDatabase param1SQLiteDatabase, int param1Int1, int param1Int2) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Upgrading database from version ");
      stringBuilder.append(param1Int1);
      stringBuilder.append(" to ");
      stringBuilder.append(param1Int2);
      stringBuilder.append(", which will destroy all old data");
      Log.w("Cocos2dxLocalStorage", stringBuilder.toString());
    }
  }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxLocalStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */