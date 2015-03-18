
package com.iss.loghandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 在异常发生时会触发,会把异常信息打印并显示到终端设备上.多用于debug测试阶段</br> 注册 {@link ErrorHandler} 方式:
 * </br> Thread.setDefaultUncaughtExceptionHandler(new
 * ErrorHandler(currentActivity)); </br></br> 或者调用
 * {@link ErrorHandler#registerNewErrorHandler(Activity)} 方法注册异常监听.</br></br>
 * 增加电子邮件的支持, 调用 {@link ErrorHandler#enableEmailReports(String, String)}
 * </br></br> ErrorHandler 必须在 AndroidManifest.xml 里注册:
 * <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height: normal;mso-layout-grid-align:none;text-autospace:none'>
 * <span style='font-size: 10.0pt;font-family:"Courier New";color:teal;mso-ansi
 * -language:DE'>&lt;</span><span class=SpellE><span
 * style='font-size:10.0pt;font-family:"Courier New";
 * color:#3F7F7F;mso-ansi-language:DE'>activity</span></span><span
 * style='font-size:10.0pt;font-family:"Courier New";mso-ansi-language:DE'>
 * <span class=SpellE><span
 * style='color:#7F007F'>android:name</span></span><span
 * style='color:black'>=</span><i><span style='color:#2A00FF'>&quot;<span
 * class=SpellE>com.itotem.loghandler.ErrorHandler</span>&quot;</span></i> <span
 * class=SpellE><span style='color:#7F007F'>android:process</span></span><span
 * style='color:black'>=</span><i><span style='color:#2A00FF'>&quot;:<span
 * class=SpellE>myexeptionprocess</span>&quot;</span></i><o:p></o:p></span>
 * </p>
 * <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height: normal;mso-layout-grid-align:none;text-autospace:none'>
 * <span style='font-size:
 * 10.0pt;font-family:"Courier New";mso-ansi-language:DE'><span
 * style='mso-tab-count: 1'>����� </span><span class=SpellE><span
 * style='color:#7F007F'>android:taskAffinity</span></span><span
 * style='color:black'>=</span><i><span style='color:#2A00FF'>&quot;<span
 * class=SpellE>system.ErrorHandler</span>&quot;</span></i><span style='color:
 * teal'>&gt;</span><o:p></o:p></span>
 * </p>
 * <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height: normal;mso-layout-grid-align:none;text-autospace:none'>
 * <span style='font-size:
 * 10.0pt;font-family:"Courier New";color:black;mso-ansi-language:DE'><span
 * style='mso-tab-count:1'>����� </span></span><span style='font-size:10.0pt;
 * font-family:"Courier New";color:teal;mso-ansi-language :DE'>&lt;</span><span
 * class=SpellE><span style='font-size:10.0pt;font-family:"Courier New";
 * color:#3F7F7F;mso-ansi-language:DE'>intent</span></span><span
 * style='font-size: 10.0pt;font-family:"Courier New";color:#3F7F7F;mso-ansi-
 * language:DE'>-filter</span><span style='font-size:10.0pt;font-family:"Courier
 * New";color:teal;mso-ansi-language: DE'>&gt;</span><span
 * style='font-size:10.0pt;font-family:"Courier New";
 * mso-ansi-language:DE'><o:p></o:p></span>
 * </p>
 * <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height: normal;mso-layout-grid-align:none;text-autospace:none'>
 * <span style='font-size:
 * 10.0pt;font-family:"Courier New";color:black;mso-ansi-language:DE'><span
 * style='mso-tab-count:2'>����������� </span></span><span
 * style='font-size:10.0pt;
 * font-family:"Courier New";color:teal;mso-ansi-language :DE'>&lt;</span><span
 * class=SpellE><span style='font-size:10.0pt;font-family:"Courier New";
 * color:#3F7F7F;mso-ansi-language:DE'>category</span></span><span
 * style='font-size:10.0pt;font-family:"Courier New";mso-ansi-language:DE'>
 * <span class=SpellE><span
 * style='color:#7F007F'>android:name</span></span><span
 * style='color:black'>=</span><i><span style='color:#2A00FF'>&quot;<span
 * class=SpellE>android.intent.category.DEFAULT</span>&quot;</span></i> <span
 * style='color:teal'>/&gt;</span><o:p></o:p></span>
 * </p>
 * <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height: normal;mso-layout-grid-align:none;text-autospace:none'>
 * <span style='font-size:
 * 10.0pt;font-family:"Courier New";color:black;mso-ansi-language:DE'><span
 * style='mso-tab-count:1'>����� </span><span style='mso-tab-count:1'>�����
 * </span></span><span style='font-size:10.0pt;font-family:"Courier
 * New";color:teal;mso-ansi-language: DE'>&lt;</span><span class=SpellE><span
 * style='font-size:10.0pt;font-family: "Courier New";color
 * :#3F7F7F;mso-ansi-language:DE'>action</span></span><span
 * style='font-size:10.0pt;font-family:"Courier New";mso-ansi-language:DE'>
 * <span class=SpellE><span
 * style='color:#7F007F'>android:name</span></span><span
 * style='color:black'>=</span><i><span style='color:#2A00FF'>&quot;<span
 * class=SpellE>android.intent.action.VIEW</span>&quot;</span></i> <span
 * style='color:teal'>/&gt;</span><o:p></o:p></span>
 * </p>
 * <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height: normal;mso-layout-grid-align:none;text-autospace:none'>
 * <span style='font-size:
 * 10.0pt;font-family:"Courier New";color:black;mso-ansi-language:DE'><span
 * style='mso-tab-count:1'>����� </span><span style='mso-tab-count:1'>�����
 * </span></span><span style='font-size:10.0pt;font-family:"Courier
 * New";color:teal;mso-ansi-language: DE'>&lt;</span><span class=SpellE><span
 * style='font-size:10.0pt;font-family:
 * "Courier New";color:#3F7F7F;mso-ansi-language:DE'>data</span></span><span
 * style='font-size:10.0pt;font-family:"Courier New";mso-ansi-language:DE'>
 * <span class=SpellE><span
 * style='color:#7F007F'>android:mimeType</span></span><span
 * style='color:black'>=</span><i><span style='color:#2A00FF'>&quot;<span
 * class=SpellE>errors</span>/<span
 * class=SpellE>itotemUnhandleCatcher</span>&quot;</span></i> <span
 * style='color:teal'>/&gt;</span><o:p></o:p></span>
 * </p>
 * <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height: normal;mso-layout-grid-align:none;text-autospace:none'>
 * <span style='font-size:
 * 10.0pt;font-family:"Courier New";color:black;mso-ansi-language:DE'><span
 * style='mso-tab-count:1'>����� </span></span><span style='font-size:10.0pt;
 * font-family:"Courier New";color:teal;mso-ansi-language :DE'>&lt;/</span><span
 * class=SpellE><span style='font-size:10.0pt;font-family:"Courier New";
 * color:#3F7F7F;mso-ansi-language:DE'>intent</span></span><span
 * style='font-size: 10.0pt;font-family:"Courier New";color:#3F7F7F;mso-ansi-
 * language:DE'>-filter</span><span style='font-size:10.0pt;font-family:"Courier
 * New";color:teal;mso-ansi-language: DE'>&gt;</span><span
 * style='font-size:10.0pt;font-family:"Courier New";
 * mso-ansi-language:DE'><o:p></o:p></span>
 * </p>
 * <p class=MsoNormal>
 * <span style='font-size:10.0pt;line-height:115%;font-family:
 * "Courier New";color:teal;mso-ansi-language:DE'>&lt;/</span><span
 * class=SpellE><span
 * style='font-size:10.0pt;line-height:115%;font-family:"Courier
 * New";color:#3F7F7F; mso-ansi-language:DE'>activity</span></span><span
 * style='font-size:10.0pt; line-height:115%;font-family:"Courier New";color:
 * teal;mso-ansi-language:DE'>&gt;</span>
 * </p>
 * 
 * @author perry.li
 */
public class ErrorHandler extends Activity implements UncaughtExceptionHandler {

    /**
     * 必须有相同的“"x/y" 字串在AndroidManifest</br> </br> 参考 {@link ErrorHandler} 下的注释配置
     * AndroidManifest 清单
     */
    public static final String DEFINED_TYPE = "errors/itotemUnhandleCatcher";

    private static Activity myCurrentActivity;

    private static UncaughtExceptionHandler defaultHandler;

    // private static String myDeveloperMailAdress = "perry.li@itotem.com.cn";
    private static String myDeveloperMailAdress;

    private static String myMailSubject = "Error in iTotem App";

    private static final String PASSED_ERROR_TEXT_ID = "Error Text";

    private static final CharSequence ERROR_ACTIVITY_TITLE = "itotem error util :(";

    private static final String DEV_MAIL = "dev mail";

    private static final String TITLE_MAIL = "title mail";

    private static final String LOG_TAG = "ErrorHandler";

    LinearLayout linear;

    EditText myTextView;

    Button sendButton;

    Handler handler;

    /**
     * 使用这个 {@link ErrorHandler#ErrorHandler(Activity) }构建函数 代替.
     * 这个构造函数必须由Android系统和 {@link ErrorHandler} 能正常工作, 之后必须调用
     * {@link ErrorHandler#setCurrentActivity(Activity)} !
     */
    @Deprecated
    public ErrorHandler() {
        if (defaultHandler == null)
            defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    /**
     * 参考 {@link ErrorHandler} 的详细注释
     * 
     * @param a
     */
    public ErrorHandler(Activity a) {
        setCurrentActivity(a);
        if (defaultHandler == null)
            defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public static void showErrorLog(Activity a, Exception errorToShow,
            boolean keepBrokenProcessRunning) {
        showErrorActivity(a, throwableToString(errorToShow), keepBrokenProcessRunning);
    }

    public static String throwableToString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter p = new PrintWriter(sw);
        t.printStackTrace(p);
        String s = sw.toString();
        p.close();
        return s;
    }

    private static void showErrorActivity(final Activity activity, final String errorText,
            boolean keepBrokenProcessRunning) {
        if (activity != null) {
            myCurrentActivity = activity;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.putExtra(PASSED_ERROR_TEXT_ID, errorText);
            i.putExtra(DEV_MAIL, myDeveloperMailAdress);
            i.putExtra(TITLE_MAIL, myMailSubject);
            i.setType(DEFINED_TYPE);
            Log.e("ErrorHandler", "Starting from " + activity + " to " + ErrorHandler.class);
            activity.startActivity(i);

            if (!keepBrokenProcessRunning) {
                /*
                 * After displaying the error in a new process the current
                 * process can be killed. This wont affect the
                 * ErrorHandler-activity because it is running in its own
                 * process (see AndroidManifest)
                 */
                activity.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String myErrorText = getIntent().getExtras().getString(PASSED_ERROR_TEXT_ID);
        handler = new Handler();
        /*
         * because this is a new process even the static fields will be reseted!
         * the correct values can be restored by passing them in the intent
         */
        myDeveloperMailAdress = getIntent().getExtras().getString(DEV_MAIL);
        myMailSubject = getIntent().getExtras().getString(TITLE_MAIL);
        loadErrorLayout(this, myErrorText);
    }

    private void loadErrorLayout(Activity a, String myErrorText) {
        linear = new LinearLayout(this);
        linear.setBackgroundColor(0xFF880000);
        linear.setOrientation(LinearLayout.VERTICAL);
        LayoutParams paramsEdit = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        paramsEdit.weight = 1;
        myTextView = new EditText(this);
        myTextView.setBackgroundColor(0x00000000);
        myTextView.setTextColor(0xFFFFFFFF);
        myTextView.setTextSize(17);
        myTextView.setLayoutParams(paramsEdit);
        LayoutParams paramsButton = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        paramsButton.weight = 5;
        sendButton = new Button(this);
        sendButton.setText("发送错误报告给开发者...");
        sendButton.setLayoutParams(paramsButton);

        linear.setOrientation(LinearLayout.VERTICAL);
        linear.addView(myTextView);
        linear.addView(sendButton);
        sendButton.setVisibility(View.GONE);
        a.setContentView(linear);
        a.setTitle(ERROR_ACTIVITY_TITLE);
        myTextView.setEnabled(false);
        // myErrorText = addDebugInfosToErrorMessage(myErrorText);
        myErrorText += CollectDataManager.getDebugInfosToErrorMessage(a);
        if (myErrorText != null)
            myTextView.setText(myErrorText);

        if (myDeveloperMailAdress != null) {
            // myTextView.setEnabled(true);
            enableMailButton(a, myTextView);
        } else {
            // 保存到sd卡
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    ErrorLogSave.cacheErrorLogToSDFile(ErrorHandler.this, myTextView.getText()
                            .toString());
                }
            });
        }
    }

    private void enableMailButton(final Activity a, final EditText myTextView) {
        sendButton.setVisibility(View.VISIBLE);
        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail(a, myTextView);
            }
        });
    }

    private static void sendMail(Activity a, EditText myTextView) {
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {
            myDeveloperMailAdress
        });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, myMailSubject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, myTextView.getText());
        a.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        Log.e(LOG_TAG, "A wild 'Uncaught exeption' appeares!");
        // Log.e(LOG_TAG, "Error=" + ex.toString());
        ex.printStackTrace();
        if (myCurrentActivity != null) {
            Log.e("ErrorHandler", "Starting error activity");
            showErrorActivity(myCurrentActivity, throwableToString(ex), false);
        } else {
            Log.e("ErrorHandler", "No current activity set -> error activity couldn't be started");
            // 保存到sd卡
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    String debugText = CollectDataManager
                            .getDebugInfosToErrorMessage(ErrorHandler.this);
                    ErrorLogSave.cacheErrorLogToSDFile(ErrorHandler.this, throwableToString(ex)
                            + debugText);
                }
            });
            defaultHandler.uncaughtException(thread, ex);
        }
    }

    public static void enableEmailReports(String developerEmailAdress, String emailTitle) {
        myDeveloperMailAdress = developerEmailAdress;
        myMailSubject = emailTitle;
    }

    public static void setCurrentActivity(Activity a) {
        myCurrentActivity = a;
    }

    public static void registerNewErrorHandler(Activity currentActivity) {
        Thread.setDefaultUncaughtExceptionHandler(new ErrorHandler(currentActivity));
    }
}
