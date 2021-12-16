package com.jiib.jnucenter.mvvm.feature.record

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.ActivityRecordBinding

class RecordActivity : AppCompatActivity() {

    lateinit var binding : ActivityRecordBinding
    lateinit var viewmodel : RecordViewModel
    private val RECORD_PERMISSION_CODE = 101
    lateinit var recycler_view : RecyclerView
    lateinit var adapter : RecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_record)

        viewmodel = ViewModelProvider(this).get(RecordViewModel::class.java)

        // 리사이클러뷰 세팅
        adapter = RecordAdapter()
        recycler_view = binding.recordRecyclerview
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }



    override fun onResume() {
        super.onResume()

        // 권한 요청
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> { }

            else -> requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_PERMISSION_CODE)
        }
    }


    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            RECORD_PERMISSION_CODE -> {
                if(grantResults.isNotEmpty()){
                    // 유저가 승인하지 않았을 시 액티비티를 종료
                    if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                        finish()
                    }
                }
            }
        }
    }
}