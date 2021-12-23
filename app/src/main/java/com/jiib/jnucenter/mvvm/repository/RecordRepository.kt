package com.jiib.jnucenter.mvvm.repository

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.jiib.jnucenter.mvvm.repository.model.database.room.Records
import com.jiib.jnucenter.mvvm.repository.model.database.room.RecordsDatabase
import com.jiib.jnucenter.mvvm.repository.network.google.GoogleDrive
import com.jiib.jnucenter.mvvm.repository.network.google.GoogleLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

// 뷰모델에서 안드로이드 context를 건네주면 레이어 특성상 곤란하기에 Application을 건네준다
class RecordRepository(application: Application) {

    // room db 초기화
    private val record_db = RecordsDatabase.getInstance(application)
    private val record_dao = record_db!!.recordsDao()

    // 구글 로그인, 드라이브
    private val google_login = GoogleLogin()
    private val google_drive = GoogleDrive()

    // 레코드 담은 라이브데이터
    val record_list : MutableLiveData<List<Records>> = MutableLiveData()

    // room db의 Record 테이블에 녹음 파일을 저장
    fun saveRecord(title: String, time: String, url: String){
        record_dao.insertRecord(null, title, time, url)
    }

    // 모든 녹음파일을 select
    fun getAllRecords(){
        val list = record_dao.getAllRecords()
        record_list.postValue(list)
    }

    // 특정 녹음파일을 db와 내부저장소에서 삭제한다
    fun deleteRecord(id:Int){
        // 둘의 순서 주의
        val path = record_dao.getRecordPath(id)
        record_dao.deleteRecord(id)

        // 내부저장소에서도 삭제
        val file = File(path)
        if (file.exists()){
            if (file.delete()) Log.d("파일 삭제 :", "성공")
            else Log.d("파일 삭제 :", "실패")
        } else Log.d("파일 삭제 :", "파일이 없습니다")
    }

    //구글 로그인 유저 최근 로그인 계정 여부 리턴
    fun isUserSignedIn(context: Context) : Boolean {
        return google_login.isUserSignedIn(context)
    }

    // 구글 로그인 intent 리턴
    fun googleSignIn(context: Context, setActivityGClient : (GoogleSignInOptions) -> Unit): Intent? {
        return google_login.googleSignIn(context, setActivityGClient)
    }

    // 구글 드라이브에 파일 업로드
    fun uploadFileToGDrive(context: Context, id: Int){
        // 특정 id를 가진 컬럼의 path
        val path = record_dao.getRecordPath(id)
        google_drive.uploadFileToGDrive(context, path)
    }
}