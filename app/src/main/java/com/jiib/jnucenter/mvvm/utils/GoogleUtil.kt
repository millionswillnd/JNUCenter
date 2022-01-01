package com.jiib.jnucenter.mvvm.utils

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn


/**
 *    구글 로그인 + 드라이브 유틸 클래스
 */

class GoogleUtil {

    // 유저가 마지막으로 구글에 sign in한 계정 여부를 리턴
    fun isUserSignedIn(context: Context) : Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        return (account != null)
    }


}