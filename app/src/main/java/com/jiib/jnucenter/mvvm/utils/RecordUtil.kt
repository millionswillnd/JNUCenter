package com.jiib.jnucenter.mvvm.utils

import android.app.Dialog
import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri

class RecordUtil {

    // 레코드 사용 설정
    fun setRecordSettings(recorder: MediaRecorder, file_path: String){
        recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(file_path)
        }
    }

}
