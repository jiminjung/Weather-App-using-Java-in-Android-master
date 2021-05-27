package com.frsarker.weatherapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.frsarker.weatherapp.NotificationHelper;

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmBroadcastReceiver", "onReceive");

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification("2021년 5월 26일",
                "날씨:흐림   평균기온:18도   최저기온:14도   최고기온:28도");
        notificationHelper.getManager().notify(1,nb.build());


    }
}