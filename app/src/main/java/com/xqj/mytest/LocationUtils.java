package com.xqj.mytest;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LocationUtils {
  public static int COPY_PROGRESS = 0;
  
  public static final String STR_ASSETS_PREFIX = "assets/";
  
  public static boolean CopyAssets(String paramString1, String paramString2, String[] paramArrayOfString, int paramInt, CopyStatus paramCopyStatus) {
    String str1 = "assets/";
    if (!JuageVersionAndProcess(paramString2, paramInt)) {
      Log.e("LocationUtils", "version same not need decompression!");
      return true;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("decompression assets!");
    stringBuilder.append(paramString1);
    Log.e("LocationUtils", stringBuilder.toString());
    stringBuilder = new StringBuilder();
    stringBuilder.append(paramString2);
    stringBuilder.append("_Tmp");
    String str2 = stringBuilder.toString();
    try {
      int k;
      ZipFile zipFile = new ZipFile(paramString1);
//      this(paramString1);
      Enumeration<? extends ZipEntry> enumeration2 = zipFile.entries();
      byte[] arrayOfByte = new byte[32768];
      File file2 = new File(str2);
//      this(str2);
      if (!file2.exists() && !file2.mkdirs()) {
        StringBuilder stringBuilder3 = new StringBuilder();
//        this();
        stringBuilder3.append("Can NOT create directory:");
        stringBuilder3.append(str2);
        Log.e("LocationUtils", stringBuilder3.toString());
        return false;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
//      this();
      stringBuilder1.append("Can NOT create assets_temp directory suc");
      stringBuilder1.append(str2);
      Log.e("LocationUtils", stringBuilder1.toString());
      if (paramArrayOfString.length == 0) {
        k = 1;
      } else {
        k = paramArrayOfString.length;
      } 
      HashSet<String> hashSet = new HashSet();
//      this();
      byte b = 0;
      int j = 0;
      int i = 0;
      String str = str1;
      Enumeration<? extends ZipEntry> enumeration1 = enumeration2;
      while (enumeration1.hasMoreElements()) {
        ZipEntry zipEntry = enumeration1.nextElement();
        String str3 = zipEntry.getName();
        if (!str3.startsWith(str))
          continue; 
        int n = paramArrayOfString.length;
        int m = 0;
        while (true) {
          if (m < n) {
            String str6 = paramArrayOfString[m];
            if (str3.startsWith(str6)) {
              i = j * 100 / k;
              if (hashSet.contains(str6)) {
                n = (j + 1) * 100 / k;
                int i1 = i + 1;
                i = i1;
                m = j;
                if (i1 > n) {
                  i = n;
                  m = j;
                } 
              } else {
                m = j + 1;
              } 
              hashSet.add(str6);
              if (i > COPY_PROGRESS) {
                paramCopyStatus.onCopyProgressChanged(i, b);
                COPY_PROGRESS = i;
              } 
              n = 1;
              j = m;
              break;
            } 
            m++;
            continue;
          } 
          n = 0;
          break;
        } 
        if (n == 0)
          continue; 
        String str4 = str3.substring(str3.indexOf(str) + 7);
        if (str4 == null || str4.trim().equalsIgnoreCase(""))
          continue; 
        StringBuilder stringBuilder3 = new StringBuilder();
//        this();
        stringBuilder3.append(str2);
        stringBuilder3.append(File.separator);
        stringBuilder3.append(str4);
        str4 = stringBuilder3.toString();
        String str5 = str4.substring(0, str4.lastIndexOf(File.separator));
        File file = new File(str5);
//        this(str5);
        if (!file.exists() && !file.mkdirs()) {
          StringBuilder stringBuilder4 = new StringBuilder();
//          this();
          stringBuilder4.append("Can NOT create target file directory:");
          stringBuilder4.append(file);
          Log.e("LocationUtils", stringBuilder4.toString());
          return false;
        } 
        file = new File(str4);
//        this(str4);
        if (!file.exists() && !file.createNewFile()) {
          StringBuilder stringBuilder4 = new StringBuilder();
//          this();
          stringBuilder4.append("Can NOT create target file:");
          stringBuilder4.append(file);
          Log.e("LocationUtils", stringBuilder4.toString());
          return false;
        } 
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
//        this(file);
        while (true) {
          m = inputStream.read(arrayOfByte, 0, 32768);
          if (m != -1) {
            fileOutputStream.write(arrayOfByte, 0, m);
            continue;
          } 
          fileOutputStream.flush();
          fileOutputStream.close();
          b++;
          break;
        } 
        paramCopyStatus.onCopyProgressChanged(i, b);
      } 
      zipFile.close();
      WriteVersion(str2, paramInt);
      File file3 = new File(str2);
//      this(str2);
      File file1 = new File(paramString2);
//      this(paramString2);
      file3.renameTo(file1);
      StringBuilder stringBuilder2 = new StringBuilder();
//      this();
      stringBuilder2.append("renameTo:");
      stringBuilder2.append(paramString2);
      stringBuilder2.append(" count:");
      stringBuilder2.append(b);
      stringBuilder2.append(" progress:");
      stringBuilder2.append(i);
      Log.e("LocationUtils", stringBuilder2.toString());
      return file1.setLastModified(paramInt);
    } catch (IOException iOException) {
      Log.e("LocationUtils", "decompression error!");
      deleteDirectory(str2);
      return false;
    } 
  }
  
  public static boolean JuageVersionAndProcess(String paramString, int paramInt) {
    if ((new File(paramString)).exists()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append(File.separator);
      stringBuilder.append("version.dat");
      File file = new File(stringBuilder.toString());
      if (file.exists()) {
        try {
          FileInputStream fileInputStream = new FileInputStream(file);
//          this(file);
          byte[] arrayOfByte = new byte[100];
          int i = fileInputStream.read(arrayOfByte, 0, 100);
          fileInputStream.close();
          String str = new String(arrayOfByte, 0, i);
//          this(arrayOfByte, 0, i);
          if (Long.valueOf(str).intValue() >= paramInt) {
            Log.d("LocationUtils", "same version, return");
            return false;
          } 
          Log.d("LocationUtils", "different version, delete Directory");
          deleteDirectory(paramString);
        } catch (IOException iOException) {
          Log.d("LocationUtils", "Exception error, delete Directory");
          deleteDirectory(paramString);
        } 
      } else {
        Log.d("LocationUtils", "version compare file not exist, delete Directory");
        deleteDirectory(paramString);
      } 
    } 
    return true;
  }
  
  protected static void WriteVersion(String paramString, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(File.separator);
    stringBuilder.append("version.dat");
    File file = new File(stringBuilder.toString());
    if (!file.exists())
      try {
        if (!file.createNewFile()) {
          IOException iOException = new IOException("Can NOT create version file!");
//          this("Can NOT create version file!");
          throw iOException;
        } 
      } catch (IOException iOException) {
        Log.d("LocationUtils", "Can NOT create version file!");
        return;
      }  
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(file);
//      this(file);
      String str = String.valueOf(paramInt);
      fileOutputStream.write(str.getBytes(), 0, str.length());
      fileOutputStream.close();
    } catch (IOException iOException) {
      Log.d("LocationUtils", "FileOutputStream error");
    } 
  }
  
  public static boolean deleteDirectory(String paramString) {
    String str = paramString;
    if (!paramString.endsWith(File.separator)) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append(File.separator);
      str = stringBuilder.toString();
    } 
    File file = new File(str);
    if (file.exists() && file.isDirectory()) {
      File[] arrayOfFile = file.listFiles();
      int i = arrayOfFile.length;
      byte b = 0;
      boolean bool = true;
      while (b < i) {
        File file1 = arrayOfFile[b];
        if (file1.isFile()) {
          boolean bool1 = file1.delete();
          bool = bool1;
          if (!bool1) {
            bool = bool1;
            break;
          } 
        } else {
          boolean bool1 = deleteDirectory(file1.getAbsolutePath());
          bool = bool1;
          if (!bool1) {
            bool = bool1;
            break;
          } 
        } 
        b++;
      } 
      if (bool)
        return file.delete(); 
    } 
    return false;
  }
  
  public static boolean makeDirectory(String paramString) {
    String[] arrayOfString = paramString.split("/");
    if (arrayOfString.length < 1)
      return false; 
    File file = new File(arrayOfString[0]);
    for (byte b = 1; b < arrayOfString.length; b++) {
      if (!file.exists() || !file.isDirectory())
        file.mkdir(); 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(file);
      stringBuilder.append(File.separator);
      stringBuilder.append(arrayOfString[b]);
      file = new File(stringBuilder.toString());
      if (!file.exists() || !file.isDirectory())
        file.mkdir(); 
    } 
    return true;
  }
  
  public static interface CopyStatus {
    void onCopyProgressChanged(int param1Int1, int param1Int2);
  }
}


/* Location:              \xq\tool\classes-dex2jar.jar!\com\xqj\mytest\LocationUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */