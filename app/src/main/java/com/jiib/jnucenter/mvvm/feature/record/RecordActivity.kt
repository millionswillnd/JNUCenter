package com.jiib.jnucenter.mvvm.feature.record

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.ActivityRecordBinding
import com.jiib.jnucenter.mvvm.utils.RecordUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*
import kotlin.concurrent.timer

class RecordActivity : AppCompatActivity() {

    lateinit var binding : ActivityRecordBinding
    lateinit var viewmodel : RecordViewModel
    private val RECORD_PERMISSION_CODE = 101
    lateinit var recycler_view : RecyclerView
    lateinit var adapter : RecordAdapter
    private var recorder : MediaRecorder? = null
    // 녹음 전 상태로 state 세팅
    private var state = RecordState.BEFORE_RECORDING
    lateinit var file_path : String
    private var record_util = RecordUtil()
    private var timer : Timer? = null
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_record)

        viewmodel = ViewModelProvider(this, RecordViewModel.Factory(application))
            .get(RecordViewModel::class.java)

        // 리사이클러뷰 세팅
        adapter = RecordAdapter()
        recycler_view = binding.recordRecyclerview
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // 녹음파일을 저장할 내부저장소 파일 경로
        val directory = ContextWrapper(this).getDir("recordDir", Context.MODE_PRIVATE)

        // 녹음 권한 요청
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> { }

            else -> requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_PERMISSION_CODE)
        }

        // 녹음을 위한 미디어레코더 객체 + 설정
        recorder = MediaRecorder()

        var count = 0; var seconds = 0; var hours = 0; var minutes = 0;
        var s = StringBuffer()
        var m = StringBuffer()

        // 녹음 버튼 리스너
        binding.recordMicImageview.setOnClickListener {
            when(state){
                // 재생 전. 클릭 시 재생
                RecordState.BEFORE_RECORDING -> {
                    recorder!!.apply {
                        // 세팅
                        file_path = File(directory, System.currentTimeMillis().toString()).absolutePath
                        record_util.setRecordSettings(recorder!!, file_path)
                        // 녹음 시작
                        prepare()
                        start()
                        // 아이콘과 상태 변경
                        binding.recordMicImageview.setImageResource(R.drawable.record_icon_stop)
                        state = RecordState.ON_RECORDING
                        // 타이머 시작 + count를 해준다.
                        timer = timer(period = 1000){
                            CoroutineScope(Dispatchers.IO).launch {
                                val job = launch {
                                    count++
                                    hours = count/3600
                                    minutes = (count - hours*3600)/60
                                    seconds = (count - hours*3600) - minutes*60
                                }

                                withContext(Dispatchers.Main){
                                    job.join()

                                    if (seconds < 10) s = StringBuffer("0$seconds") else s = StringBuffer("$seconds")
                                    if (minutes < 10) m = StringBuffer("0$minutes") else m = StringBuffer("$minutes")
                                    binding.recordTime.text = "${hours}:${m}:$s"
                                }
                            }
                        }
                    }
                }

                // 재생 중. 클릭시 저장
                RecordState.ON_RECORDING -> {
                    // 녹음기능 정지 후 리셋
                    recorder!!.apply {
                        stop()
                        reset()
                    }

                    // 커스텀 다이얼로그 띄우기
                    dialog = Dialog(this)
                    dialog!!.apply {
                        requestWindowFeature(Window.FEATURE_NO_TITLE)
                        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        setContentView(R.layout.record_custom_dialog)

                        // 저장 버튼 리스너
                        findViewById<Button>(R.id.bt_yes).setOnClickListener {
                            CoroutineScope(Dispatchers.IO).launch {
                                // room db 테이블에 해당 녹음 저장
                                val job = launch {
                                    viewmodel.saveRecord(
                                        findViewById<EditText>(R.id.title_et).text.toString(),
                                        binding.recordTime.text.toString(),
                                        file_path)
                                }

                                withContext(Dispatchers.Main){
                                    job.join()
                                    binding.recordTime.text = "00:00:00"
                                    dismiss()
                                    Toast.makeText(this@RecordActivity
                                        , "녹음을 저장했습니다!"
                                        , Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                        }

                        // 취소 버튼 리스너
                        findViewById<Button>(R.id.bt_no).setOnClickListener {
                            dismiss()
                        }

                        show()
                    }

                    binding.recordMicImageview.setImageResource(R.drawable.record_icon_mic)

                    state = RecordState.BEFORE_RECORDING
                    count = 0; hours = 0; minutes = 0; seconds = 0
                    timer!!.cancel()


                }
            }
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