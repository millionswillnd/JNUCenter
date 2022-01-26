package com.jiib.jnucenter.mvvm.feature.record

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.jiib.jnucenter.mvvm.repository.RecordRepository
import com.jiib.jnucenter.mvvm.repository.model.database.room.Records

/**
 *    녹음 액티비티 뷰모델
 */

class RecordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RecordRepository(application)
    private val context = getApplication<Application>().applicationContext

    val record_list : LiveData<List<Records>>
        get() = repository.record_list

    suspend fun saveRecord(title:String, time:String, url:String){
        repository.saveRecord(title, time, url)
    }

    suspend fun getAllRecords(){
        repository.getAllRecords()
    }

    suspend fun deleteRecord(id:Int){
        repository.deleteRecord(id)
    }

    //구글 로그인 유저 최근 로그인 계정 여부 리턴
    fun isUserSignedIn(context: Context) : Boolean {
        return repository.isUserSignedIn(context)
    }

    // 구글 로그인 intent 리턴
    fun googleSignIn(setActivityGClient : (GoogleSignInOptions) -> Unit): Intent? {
        return repository.googleSignIn(context, setActivityGClient)
    }

    // 구글드라이브 파일 업로드
    suspend fun uploadFileToGDrive(context: Context, id: Int){
        repository.uploadFileToGDrive(context, id)
    }

    // application 파라미터를 넣기 위한 뷰모델 팩토리
    class Factory(val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RecordViewModel(application) as T
        }
    }
}