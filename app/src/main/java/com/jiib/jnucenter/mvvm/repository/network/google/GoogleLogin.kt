package com.jiib.jnucenter.mvvm.repository.network.google

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.services.drive.DriveScopes

/**
 *   구글 로그인 클래스
 */

class GoogleLogin {

    // 유저가 마지막으로 구글에 sign in한 계정 여부를 리턴
    fun isUserSignedIn(context: Context) : Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        return (account != null)
    }


    // 구글 로그인
    fun googleSignIn(context: Context,
                     setActivityGClient : (GoogleSignInOptions) -> Unit): Intent? {

        // 로그인 관련 설정
        val sign_in_options = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            // 드라이브 api에서 다룰 파일 범위 설정
            .requestScopes(Scope(DriveScopes.DRIVE_FILE))
            .build()

        val google_client = GoogleSignIn.getClient(context, sign_in_options)
        val sign_in_intent = google_client?.signInIntent

        setActivityGClient(sign_in_options)

        return sign_in_intent
    }
}