package com.jiib.jnucenter.mvvm.feature.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import com.jiib.jnucenter.R
import com.jiib.jnucenter.mvvm.feature.application.JnuApplication
import com.jiib.jnucenter.mvvm.feature.lecture.LectureDateActivity
import com.jiib.jnucenter.mvvm.feature.main.MainActivity
import com.jiib.jnucenter.mvvm.repository.AlarmRepository

class FcmService : FirebaseMessagingService() {

    // 클라우드 서버에 등록되었을 때 호출. 파라미터가 토큰
    // token을 서버로 전송
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        val repository = AlarmRepository()
        repository.postFcmToken(p0)
    }

    // 백엔드에서 notification을 json 프로퍼티로 주지 않을 시
    // 아래 메소드에서 포그라운드 뿐 아니라 백그라운드 메시지도 핸들 가능
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)


        // 알람 클릭시 강의 기한 액티비티로 이동하는 펜딩인텐트
        // 플래그 -> 새로운 태스크의 루트 액티비티로 + 전에 있던 old 액티비티들 다 날리기
        val intent = Intent(applicationContext, LectureDateActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pending_intent = PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val app = applicationContext as JnuApplication

        val notification = NotificationCompat.Builder(this, "101")
            .setContentTitle(p0.data.get("title"))
            .setContentText(p0.data.get("body"))
            .setSmallIcon(R.drawable.main_icon_calendar)
            .setShowWhen(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(pending_intent, true)
            .build()

        app.notification_manager.notify(101, notification)
    }
}