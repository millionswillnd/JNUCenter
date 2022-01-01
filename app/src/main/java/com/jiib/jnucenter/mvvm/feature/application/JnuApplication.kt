package com.jiib.jnucenter.mvvm.feature.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager


/**
 *    전역접근을 위한 Application 클래스
 */
class JnuApplication : Application() {

    lateinit var notification_manager : NotificationManager
    private val NOTI_CHANNEL_ID = "101"

    override fun onCreate() {
        super.onCreate()

        // 노티 채널
        notification_manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                NOTI_CHANNEL_ID,
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