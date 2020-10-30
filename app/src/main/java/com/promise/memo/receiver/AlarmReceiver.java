package com.promise.memo.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.promise.memo.Bean.GlobalValues;
import com.promise.memo.Bean.NoteBean;
import com.promise.memo.R;
import com.promise.memo.UI.AlarmActivity;
import com.promise.memo.UI.MainActivity;

import java.io.Serializable;

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager m_notificationMgr = null;
    private static final int NOTIFICATION_FLAG = 3;

    @Override
    public void onReceive(Context context, Intent intent) {
        m_notificationMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (intent.getAction().equals(GlobalValues.TIMER_ACTION_REPEATING)) {
            Log.e("alarm_receiver", "周期闹钟");
        } else if (intent.getAction().equals(GlobalValues.TIMER_ACTION)) {
            Log.e("alarm_receiver", "周期闹钟");

            // 进入闹钟操作界面
            Intent alaramIntent = new Intent(context, AlarmActivity.class);
            alaramIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");
            String id = intent.getStringExtra("id");
            alaramIntent.putExtra("title", title);
            alaramIntent.putExtra("content", content);

            alaramIntent.putExtra("id", id);
            context.startActivity(alaramIntent);
        }
    }
}
