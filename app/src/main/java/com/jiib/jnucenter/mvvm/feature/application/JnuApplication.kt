package com.jiib.jnucenter.mvvm.feature.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color

class JnuApplication : Application() {

    lateinit var notification_manager : NotificationManager

    override fun onCreate() {
        super.onCreate()

        // 오레오 이상에선 Notification 채널이 강제
        // 전역 접근을 위한 노티 채널
        notification_manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "101",
                "FCM_ALARM",
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.apply {
                enableVibration(true)
                description = "notification"
                notification_manager.createNotificationChannel(channel)
            }
        }
    }
}