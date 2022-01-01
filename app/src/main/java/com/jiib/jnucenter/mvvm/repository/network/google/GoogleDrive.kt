package com.jiib.jnucenter.mvvm.repository.network.google

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.jiib.jnucenter.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

/**
 *   구글드라이브 관련 클래스
 */

class GoogleDrive {

    // 구글 드라이브 객체 리턴
    fun getGoogleDriveService(context: Context) : Drive? {
        GoogleSignIn.getLastSignedInAccount(context)?.let { google_account ->
            // 로그인한 계정의 구글 드라이브 접근 퍼미션 얻기
            val credential = GoogleAccountCredential.usingOAuth2(context, listOf(DriveScopes.DRIVE_FILE))
            credential.selectedAccount = google_account.account

            return Drive.Builder(
                AndroidHttp.newCompatibleTransport(),
                JacksonFactory.getDefaultInstance(),
                credential
            ).setApplicationName(context.getString(R.string.app_name)).build()
        }
        return null
    }


    // 구글 드라이브에 파일을 업로드
    fun uploadFileToGDrive(context: Context, path: String){
        getGoogleDriveService(context)?.let { google_drive_service ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // 기기 내부저장소의 파일
                    val record_file = File(path)

                    // 구글드라이브에 올라가는 파일 형식 세팅
                    val gfile = com.google.api.services.drive.model.File()
                    gfile.name = record_file.name
                    val file_content = FileContent("audio/x-aac", record_file)
                    google_drive_service.Files().create(gfile, file_content).execute()
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        } ?: Log.d("Googel Drive Api", ": 업로드 실패")
    }




}