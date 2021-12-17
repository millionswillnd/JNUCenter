package com.jiib.jnucenter.mvvm.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.jiib.jnucenter.mvvm.repository.model.database.room.Records
import com.jiib.jnucenter.mvvm.repository.model.database.room.RecordsDatabase

// 뷰모델에서 안드로이드 context를 건네주면 레이어 특성상 곤란하기에 Application을 건네준다
class RecordRepository(application: Application) {

    // room db 초기화
    private val record_db = RecordsDatabase.getInstance(application)
    private val record_dao = record_db!!.recordsDao()

    // 레코드 담은 라이브데이터
    private val records: LiveData<List<Records>> = record_dao.getAllRecords()

    // room db의 Record 테이블에 녹음 파일을 저장
    fun saveRecord(title: String, time: String, url: String){
        record_dao.insertRecord(null, title, time, url)
    }
}