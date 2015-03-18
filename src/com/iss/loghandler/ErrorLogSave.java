
package com.iss.loghandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

/**
 * 目前将错误的log日志存储到SD卡或者发送到开发者邮箱中去<br>
 * 必须在配置文件 AndroidManifest.xml 中声明
 * <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height: normal;mso-layout-grid-align:none;text-autospace:none'>
 * <span style='font-size:
 * 10.0pt;font-family:"Courier New";color:black;mso-ansi-language:DE'></span>
 * <span style='font-size:10.0pt;font-family:"Courier
 * New";color:teal;mso-ansi-language: DE'>&lt;</span><span class=SpellE><span
 * style='font-size:10.0pt;font-family:
 * "Courier New";color:#3F7F7F;mso-ansi-language
 * :DE'>uses-permission</span></span> <span
 * style='font-size:10.0pt;font-family:"Courier New";mso-ansi-language:DE'>
 * <span class=SpellE> <span style='color:#7F007F'>android:name</span></span>
 * <span style='color:black'>=</span><i><span style='color:#2A00FF'>&quot;<span
 * class=SpellE>android.permission.READ_LOGS</span> <span
 * class=SpellE></span>&quot;</span></i> <span
 * style='color:teal'>/&gt;</span><o:p></o:p></span>
 * </p>
 * 
 * @author pengjun
 */
public class ErrorLogSave extends Thread {

    private static final String TAG = "ErrorLogSave";

    Context context;

    private static final String SD_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath();

    private static final String FILE_PATH = SD_PATH + "/itotem_error_log/";

    private static String packageName;

    protected final Handler mHandler;

    public ErrorLogSave(Context context) {
        this.context = context;
        // HandlerThread localHandlerThread = new HandlerThread("StatsManager");
        // localHandlerThread.start();
        // mHandler = new Handler(localHandlerThread.getLooper());
        mHandler = new Handler();
        packageName = context.getPackageName();
    }

    /**
     * 对外接口onError， 错误日志信息采集与发送 <!-- 获得客户端crash的报告 --> <uses-permission
     * android:name="android.permission.READ_LOGS" />
     * 
     * @param paramContext
     */
    public static void onError(Context context) {
        new ErrorLogSave(context).start();
    }

    /**
     * 获取异常日志信息
     * 
     * @param paramContext
     * @return
     */
    private static String getExceptionLog(Context paramContext) {
        String localObject = "";
        try {
            String str1 = paramContext.getPackageName();
            String str2 = "";
            int i1 = 0;
            int i2 = 0;
            ArrayList<String> localArrayList = new ArrayList<String>();
            localArrayList.add("logcat");
            localArrayList.add("-d");
            localArrayList.add("-v");
            localArrayList.add("raw");
            localArrayList.add("-s");
            localArrayList.add("AndroidRuntime:E");
            localArrayList.add("-p");
            localArrayList.add(str1);
            Process localProcess = Runtime.getRuntime().exec(
                    (String[]) localArrayList.toArray(new String[localArrayList.size()]));
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(
                    localProcess.getInputStream()), 1024);
            for (String str3 = localBufferedReader.readLine(); str3 != null; str3 = localBufferedReader
                    .readLine()) {
                if (str3.indexOf("thread attach failed") < 0) {
                    str2 = str2 + str3 + '\n';
                }
                if ((i2 == 0) && (str3.toLowerCase().indexOf("exception") >= 0)) {
                    i2 = 1;
                }
                if ((i1 != 0) || (str3.indexOf(str1) < 0)) {
                    continue;
                }
                i1 = 1;
            }
            if ((str2.length() > 0) && (i2 != 0) && (i1 != 0)) {
                localObject = str2;
            }
            try {
                Runtime.getRuntime().exec("logcat -c");
            } catch (Exception localException2) {
                Log.e(TAG, "Failed to clear log");
            }
        } catch (Exception localException1) {
            Log.e(TAG, "Failed to catch error log");
        }
        return localObject;
    }

    /**
     * 存储错误日志到sd卡</br> 目录名称:"/sd/itotem_error_log/"</br> 文件名称:
     * packagename_tiem(YYYY_MM_DD-HH_mm_ss)
     * 
     * @param context
     * @param errorLog
     */
    protected static void cacheErrorLogToSDFile(Context context, String errorLog) {
        Log.e(TAG, "cache Error Log To SD File is ruuning:" + errorLog);
        if (TextUtils.isEmpty(errorLog)) {
            return;
        }
        // String deviceInfo =
        // CollectDataManager.getDeviceInfo(context).toString();
        // String deviceInfo =
        // CollectDataManager.getDebugInfosToErrorMessage(context);
        // errorLog+="\n deviceInfo:"+deviceInfo;
        FileOutputStream outputStream = null;
        try {
            long curTimeM = System.currentTimeMillis();
            if (TextUtils.isEmpty(packageName)) {
                packageName = context.getPackageName();
            }
            // 获取新的文件名称
            String fileName = packageName + "_" + getTime(curTimeM) + ".txt";
            Log.i(TAG, "new file is :" + fileName);
            // File file = new File(FILE_PATH, fileName);
            File pathFile = new File(FILE_PATH);
            File file = new File(pathFile, fileName);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            System.out.println("file length:" + file.length());
            // 打开一个新的文件
            outputStream = new FileOutputStream(file);
            outputStream.write(errorLog.getBytes());
            outputStream.close();
            // ByteArrayInputStream bis = new
            // ByteArrayInputStream(errorLog.getBytes());
            // bis.read(buffer);
            // 将固定文件内的内容写到新的文件内
            // FileInputStream fis = new FileInputStream(file);
            // byte buffer[] = new byte[1024];
            // int len = 0;
            // while ((len = fis.read(buffer)) != -1) {
            //
            // outputStream.write(buffer, 0, len);
            //
            // }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                outputStream = null;
            }
        }
    }

    private static String getTime(long curTime) {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date date = new Date(curTime);
        return df1.format(date);
    }

    /**
     * 获取错误日志并保存
     * 
     * @param paramContext
     * @param appkey
     */

    @Override
    public void run() {
        // TODO Auto-generated method stub
        String str = getExceptionLog(context);
        mHandler.post(new SaveErrorLog2SD(str));
        super.run();
    }

    private static final Object a = new Object();// 线程锁

    /**
     * 保存错误日志到sd卡
     * 
     * @author pengjun
     */
    private class SaveErrorLog2SD implements Runnable {
        String errorLog;

        SaveErrorLog2SD(String errorLog) {
            this.errorLog = errorLog;
        }

        @Override
        public void run() {
            // TODO 执行保存错误日志到卡
            String deviceInfo = CollectDataManager.getDebugInfosToErrorMessage(context);
            errorLog += "\n deviceInfo:" + deviceInfo;
            cacheErrorLogToSDFile(context, errorLog);

        }

    }

}
