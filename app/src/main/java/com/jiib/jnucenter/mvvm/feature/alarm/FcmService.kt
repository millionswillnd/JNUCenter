package com.jiib.jnucenter.mvvm.feature.alarm

import android.app.PendingIntent
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jiib.jnucenter.R
import com.jiib.jnucenter.mvvm.feature.application.JnuApplication
import com.jiib.jnucenter.mvvm.feature.food.FoodActivity
import com.jiib.jnucenter.mvvm.feature.lecture.LectureDateActivity
import com.jiib.jnucenter.mvvm.feature.main.MainActivity


/**
 *    FCM 관련 코드 (토큰 서버 전송 / FCM 수신 콜백)
 */
class FcmService : FirebaseMessagingService() {

    private val NOTI_CHANNEL_ID = 101

    // 클라우드 서버에 등록되었을 때 token을 서버로 전송
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        val alarm_viewmodel = AlarmViewModel()
            alarm_viewmodel.sendFcmToken(p0)
    }

    // 수신 콜백
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        var intent: Intent? = null

        // notification 알람 커스텀뷰
        val content_view = RemoteViews(packageName, R.layout.fcm_custom_noti)
        content_view.setTextViewText(R.id.fcm_title_tv, p0.data.get(getString(R.string.title)))
        content_view.setTextViewText(R.id.fcm_body_tv, p0.data.get(getString(R.string.body)))

        // 알람 클릭시 강의 기한 액티비티 or 학식 액티비티로 이동
        // 학식인 경우
        if(p0.data.get(getString(R.string.title))!!.contains(getString(R.string.menu))){
            intent = Intent(applicationContext, FoodActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            content_view.setImageViewResource(R.id.fcm_icon, R.drawable.main_icon_color_food)
        }
        // 장학인 경우
        else if (p0.data.get(getString(R.string.title))!!.contains(getString(R.string.scholarship))){
            intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            content_view.setImageViewResource(R.id.fcm_icon, R.drawable.fcm_icon_scholarship)
        }
        // 강의기한인 경우
        else {
            intent = Intent(applicationContext, LectureDateActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            content_view.setImageViewResource(R.id.fcm_icon, R.drawable.main_icon_color_calendar)
        }

        // 펜딩인텐트
        val pending_intent = PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // 전역변수 접근
        val app = applicationContext as JnuApplication

        val notification = NotificationCompat.Builder(this, NOTI_CHANNEL_ID.toString())
            .setContent(content_view)
            .setSmallIcon(R.drawable.main_icon_calendar)
            .setShowWhen(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(pending_intent, true)
            .build()

        app.notification_manager.notify(NOTI_CHANNEL_ID, notification)
    }
}