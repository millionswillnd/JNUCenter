package com.jiib.jnucenter.mvvm.feature.record

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.ActivityRecordBinding
import com.jiib.jnucenter.mvvm.feature.main.MainActivity
import com.jiib.jnucenter.mvvm.utils.RecordUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.notify
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer

/**
 *   녹음 관련 액티비티
 */

class RecordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecordBinding
    private lateinit var viewmodel : RecordViewModel
    private val RECORD_PERMISSION_CODE = 101
    private lateinit var recycler_view : RecyclerView
    private lateinit var adapter : RecordAdapter
    private var recorder : MediaRecorder? = null
    /*
     *  녹음 관련 변수들
     */
    private var state: RecordState? = RecordState.BEFORE_RECORDING
    lateinit var file_path : String
    private var record_util : RecordUtil? = RecordUtil()
    private var timer : Timer? = null
    private var dialog: Dialog? = null
    private var check_list : ArrayList<Int>? = null
    /*
     *  재생 관련 코드
     */
    private var media_player : MediaPlayer? = null
    private var record_path : StringBuffer? = StringBuffer("")
    /*
     *  구글로그인 + 드라이브 관련
     */
    private var result_launcher : ActivityResultLauncher<Intent>? = null
    private var google_sign_client : GoogleSignInClient? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_record)

        // 뷰모델
        viewmodel = ViewModelProvider(this, RecordViewModel.Factory(application))
            .get(RecordViewModel::class.java)

        // 다중 삭제 id list
        check_list = ArrayList<Int>()

        // 리사이클러뷰 세팅
        CoroutineScope(Dispatchers.IO).launch {
            viewmodel.getAllRecords()
        }

        viewmodel.record_list.observe(this, androidx.lifecycle.Observer {
            adapter = RecordAdapter(
                it,
                check_list,
                {path:String -> changeRecordPath(path)} )
            recycler_view = binding.recordRecyclerview
            recycler_view.adapter = adapter
            recycler_view.layoutManager = LinearLayoutManager(
                this@RecordActivity,
                RecyclerView.VERTICAL,
                false)
        })

        // 다중 삭제 구현
        binding.recordSaveButton.setOnClickListener {
            // 체크 갯수가 0인 경우
            if (check_list!!.isEmpty()){
                Toast.makeText(this, getString(R.string.delete_message), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // db에서 해당 row 삭제하고, 내부저장소에서도 파일 삭제한 후 ui 업데이트
            CoroutineScope(Dispatchers.IO).launch {
                val job = launch {
                    for(id in check_list!!){
                            viewmodel.deleteRecord(id)
                    }
                }

                withContext(Dispatchers.Main){
                    job.join()
                    Toast.makeText(this@RecordActivity, getString(R.string.deleted_message), Toast.LENGTH_LONG).show()
                    launch(Dispatchers.IO) {
                        viewmodel.getAllRecords()
                    }
                }
            }
        }

        // 녹음파일을 저장할 내부저장소 파일 경로
        val directory = ContextWrapper(this).getDir(getString(R.string.record_dir), Context.MODE_PRIVATE)

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
                        file_path = File(directory, System.currentTimeMillis().toString()).canonicalPath
                        record_util?.setRecordSettings(recorder!!, file_path)
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
                                    // 새로운 녹음 리스트로 livedata 세팅
                                    viewmodel.getAllRecords()
                                }

                                withContext(Dispatchers.Main){
                                    job.join()
                                    binding.recordTime.text = "00:00:00"
                                    dismiss()
                                    Toast.makeText(
                                        this@RecordActivity
                                        , getString(R.string.recorded_message)
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

        // startActivityForResult deprecated, 대체
        // 장점 : 더 이상 리퀘스트 코드 안받아도 된다
        result_launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                // 구글드라이브에 체크된 녹음 파일들을 업로드
                CoroutineScope(Dispatchers.IO).launch {
                    viewmodel.getAllRecords()
                    if (check_list?.isEmpty()!!){
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@RecordActivity, getString(R.string.upload_message), Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        val job = launch {
                            for(id in check_list!!){
                                viewmodel.uploadFileToGDrive(this@RecordActivity, id)
                            }
                        }

                        withContext(Dispatchers.Main){
                            job.join()
                            Toast.makeText(this@RecordActivity, getString(R.string.upload_message), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            else {
                Log.d("구글 로그인 api : ", "실패")
            }
        }

        // 구글 드라이브 백업 버튼
        binding.recordGoogleBackup.setOnClickListener{
            // 구글에 로그아웃 되어있다면 로그인한다
            if (viewmodel.isUserSignedIn(this) == false){
                val sign_in_intent =
                    viewmodel.googleSignIn({gso: GoogleSignInOptions ->
                        assignGoogleClient(gso)})
                result_launcher!!.launch(sign_in_intent)
            } else {
                // 구글드라이브에 체크된 녹음 파일들을 업로드
                    CoroutineScope(Dispatchers.IO).launch {
                        if (check_list?.isEmpty()!!){
                            withContext(Dispatchers.Main){
                                Toast.makeText(this@RecordActivity, getString(R.string.upload_message), Toast.LENGTH_SHORT).show()
                            }
                        }
                        else {
                            val job = launch {
                                for(id in check_list!!){
                                    viewmodel.uploadFileToGDrive(this@RecordActivity, id)
                                }
                            }

                            withContext(Dispatchers.Main){
                                job.join()
                                Toast.makeText(this@RecordActivity, getString(R.string.uploaded_message), Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }

        // 백버튼 클릭리스너
        binding.recordBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onRestart() {
        super.onRestart()
        if (recorder == null) MediaRecorder()
        if (state == null) state = RecordState.BEFORE_RECORDING
        if (record_util == null) record_util = RecordUtil()
        if (check_list == null) check_list = ArrayList<Int>()
        if (record_path == null) record_path = StringBuffer("")
    }

    override fun onPause() {
        // 구글 로그아웃
        super.onPause()
        google_sign_client?.signOut()
    }

    override fun onStop() {
        super.onStop()
        // 구글 로그아웃
        google_sign_client?.signOut()
        // 메모리 해제
        recorder = null
        state = null
        record_util = null
        timer = null
        dialog = null
        check_list = null
        record_path = null
    }


    override fun onDestroy() {
        super.onDestroy()
        // 구글 로그아웃
        google_sign_client?.signOut()
        // 메모리 해제
        recorder = null
        state = null
        record_util = null
        timer = null
        dialog = null
        check_list = null
        record_path = null
        google_sign_client = null
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

    // adpater에서 재생 버튼 누를 시 새로운 값으로 다시 변수들 세팅
    private fun changeRecordPath(path : String): MediaPlayer? {
        record_path?.setLength(0)
        record_path?.append(path)
        media_player = MediaPlayer.create(this@RecordActivity, Uri.parse(record_path.toString()))

        return media_player
    }

    // 리파지토리 레이어에서 구글 로그인 api 수행시 액티비티에 GSO 객체를 할당 하기 위한 함수
    private fun assignGoogleClient(gso : GoogleSignInOptions){
        google_sign_client = GoogleSignIn.getClient(this, gso)
    }
}