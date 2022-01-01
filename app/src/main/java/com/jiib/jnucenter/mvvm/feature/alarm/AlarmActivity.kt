package com.jiib.jnucenter.mvvm.feature.alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.ActivityAlarmBinding
import com.jiib.jnucenter.mvvm.feature.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *  알람 액티비티
 *  새로운 장학 게시글, 강의기한, 학식 알람 수신 설정 여부를 서버로 전송
 */
class AlarmActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAlarmBinding
    private lateinit var viewmodel : AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm)

        // 뷰모델 초기화
        viewmodel = ViewModelProvider(this).get(AlarmViewModel::class.java)

        // 서버에서 알람값을 받아와 ui에 세팅하는 로직
        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                viewmodel.getAlarmSettings()
            }

            withContext(Dispatchers.Main){
                binding.alarmProgressBar.show()
                job.join()
                binding.alarmProgressBar.hide()

            }
        }

        // retrofit으로 받은 알람 설정 정보를 담은 dto livedata를 observe
        viewmodel.alarm_list.observe(this, Observer {
            val lecture = it.lecture_alarm
            val scholarship = it.scholarship_alarm
            val food = it.food_alarm

            // 값이 1이면 off니까 switch를 off 상태로 만든다
            if (lecture == 1) binding.alarmLectureSwitch.toggle()
            if (scholarship == 1) binding.alarmScholarshipSwitch.toggle()
            if (food == 1) binding.alarmFoodSwitch.toggle()
        })

        // 알람 설정 변수들. 0=0ff / 1=on
        var lecture_checked : Boolean = true
        var scholarship_checked : Boolean = true
        var food_checked : Boolean = true

        // 스위치 버튼을 다 on으로 설정해놓는다(초깃값)
        binding.alarmLectureSwitch.toggle()
        binding.alarmScholarshipSwitch.toggle()
        binding.alarmFoodSwitch.toggle()

        // 설정값을 바꿀 시 체크 변수에 세팅
        binding.alarmLectureSwitch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked == true) lecture_checked = true
                else lecture_checked = false
            }
        })

        binding.alarmScholarshipSwitch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked == true) scholarship_checked = true
                else scholarship_checked = false
            }
        })

        binding.alarmFoodSwitch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked == true) food_checked = true
                else food_checked = false
            }
        })

        // 저장 버튼 리스너
        binding.alarmSaveButton.setOnClickListener {
            var lecture : Int? = null
            var scholarship : Int? = null
            var food : Int? = null

            if (lecture_checked == true) lecture = 0 else lecture = 1
            if (scholarship_checked == true) scholarship = 0 else scholarship = 1
            if (food_checked == true) food = 0 else food = 1

            CoroutineScope(Dispatchers.IO).launch {

                val job = launch{
                    viewmodel.setAlarmSettings(lecture, scholarship, food)
                }

                withContext(Dispatchers.Main){
                    job.join()
                    Toast.makeText(this@AlarmActivity, "설정이 완료됐습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 백 버튼 리스너
        binding.alarmBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}