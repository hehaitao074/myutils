package com.iss.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 开机启动推送服务
 * @author pengjun
 *
 */
public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
//		context.startService(new Intent(context, PushServiceMode.class));
	}
}
