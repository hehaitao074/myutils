package com.iss.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class PushServiceMode extends Service{

	private static final String TAG = "PushServiceMode";
	public static final long PUSH_TIME=1000 * 10  ;// 30秒执行一次;//检查蓝牙连接的时间间隔
	public  int i =0;
	String result ;
	
	@Override
	public IBinder onBind(Intent intent) {
		return myBinder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.e("MyLog1", "PushServiceMode.onCreate...");
		//启动线程
		startPush();
	}
	boolean isRun = true;
	private void startPush() {
		new Thread(new Runnable() {
			public void run() {
				while(isRun){
					startSynchNote();
					try {
						Thread.sleep(PUSH_TIME);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public class MyBinder extends Binder{
		public PushServiceMode getService(){
            return PushServiceMode.this;
        }
	}
	
	/**
	 * 后台同步
	 */
	protected void startSynchNote() {
		Log.e(TAG,"push service..."+i++);

	}
	

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("MyLog", "PushServiceMode.onDestroy............................................................");
		isRun = false;
		//需要关闭蓝牙
	}

	private MyBinder myBinder = new MyBinder();
	
	Handler handler = new Handler();
	
	
}
