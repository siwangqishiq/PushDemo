package com.xinlan.pushdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "MyReceiver";
	private WindowManager.LayoutParams layoutParams;
	private WindowManager windowManager;
	private View view;// ͸������

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		System.out.println("onReceive - " + intent.getAction() + ", extras: "
				+ printBundle(bundle));

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle
					.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			System.out.println("panyi---����Registration Id : " + regId);
		} else if (JPushInterface.ACTION_UNREGISTER.equals(intent.getAction())) {
			String regId = bundle
					.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			System.out.println("panyi---����UnRegistration Id : " + regId);
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {// ���͵���Ϣ
			System.out.println("���յ������������Զ�����Ϣ: "
					+ bundle.getString(JPushInterface.EXTRA_MESSAGE));

			view = LayoutInflater.from(context)
					.inflate(R.layout.floating, null);

			windowManager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, LayoutParams.TYPE_SYSTEM_ERROR,
					LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT);
			layoutParams.gravity = Gravity.CENTER;
			TextView text = (TextView) view.findViewById(R.id.text);
			text.setText(bundle.getString(JPushInterface.EXTRA_MESSAGE));
			Button close = (Button) view.findViewById(R.id.close);
			close.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					windowManager.removeView(view);
				}
			});
			windowManager.addView(view, layoutParams);
			System.out.println(bundle.getString(JPushInterface.EXTRA_MESSAGE));
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {// ���͵�֪ͨ
			int notifactionId = bundle
					.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			System.out.println("���յ�����������֪ͨ��ID: " + notifactionId);
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
		} else {
		}
	}

	// ��ӡ���е� intent extra ����
	private String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
}
