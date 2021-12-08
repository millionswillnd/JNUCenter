package com.jiib.jnucenter.mvvm.feature.alarm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jiib.jnucenter.mvvm.repository.AlarmRepository

class FcmService : FirebaseMessagingService() {

    // 클라우드 서버에 등록되었을 때 호출. 파라미터가 토큰
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        // token을 서버로 전송한다
        val repository = AlarmRepository()
        repository.postFcmToken(p0)
    }

    // 클라우드 서버에서 메시지를 전송하면 자동으로 호출됨
    // 여기서 메시지를 처리해서 사용자에게 알림을 보낸다
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        // 수신한 메시지를 처리
    }
}