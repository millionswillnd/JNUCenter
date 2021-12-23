package com.jiib.jnucenter.mvvm.feature.record

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.RecordRecyclerviewItemBinding
import com.jiib.jnucenter.mvvm.feature.lecture.LectureViewHolder
import com.jiib.jnucenter.mvvm.repository.model.database.room.Records
import kotlinx.coroutines.*
import java.lang.StringBuilder

class RecordAdapter(private val record_list: List<Records>,
                    private val check_list: ArrayList<Int>?,
                    private val setMediaPlayer : (String) -> MediaPlayer
                    ) : RecyclerView.Adapter<RecordAdapter.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.record_recyclerview_item,
                parent,
                false
            ),
            setMediaPlayer
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(record_list!!.get(position).record_title,
            record_list.get(position).id,
            check_list,
            record_list.get(position).record_url
            )
    }

    override fun getItemCount(): Int {
        return record_list.size
    }


    // 뷰홀더
    class ViewHolder(private val binding : RecordRecyclerviewItemBinding,
                     private val setMediaPlayer : (String) -> MediaPlayer) : RecyclerView.ViewHolder(binding.root){

        var id : Int? = null
        var path : String? = null
        var player : MediaPlayer? = null

        // 바인드
        fun bind(title: String,
                 id: Int,
                 check_list: ArrayList<Int>?,
                 path: String?,
                 ) {

            binding.recordItemTitle.text = title
            this.id = id
            player = setMediaPlayer(path!!)

            // 체크박스 삭제 체크 했을 경우 리스너. 액티비티의 어레이리스트 객체에 값을 넣어준다
            binding.checkBox.setOnClickListener {
                if(binding.checkBox.isChecked){
                    check_list?.add(id)
                }
                else {
                    check_list?.remove(id)
                }
            }

            // 재생 버튼을 눌렀을 시 동작. 액티비티의 빌더 객체에 선택한 아이템의 녹음 path를 세팅해주고 재생
            binding.playImageButton.setOnClickListener {

                CoroutineScope(Dispatchers.IO).launch {

                        // 만약 재생 중이면 정지
                        if (player!!.isPlaying){
                            player!!.apply {
                                reset()
                            }
                            withContext(Dispatchers.Main){
                                binding.checkBox.visibility = View.VISIBLE
                                binding.recordItemTitle.visibility = View.VISIBLE
                                binding.seekbar.visibility = View.GONE
                                binding.playImageButton.setImageResource(R.drawable.record_icon_play)
                            }
                        }
                        else {
                            CoroutineScope(Dispatchers.IO).launch {
                                // 재생
                                if (!(player!!.isPlaying)) player = setMediaPlayer(path!!)

                                player!!.apply {
                                    isLooping = false // 무한반복 방지
                                    start()
                                }
                                // 뷰 작업
                                withContext(Dispatchers.Main){
                                    binding.checkBox.visibility = View.GONE
                                    binding.recordItemTitle.visibility = View.GONE
                                    binding.seekbar.visibility = View.VISIBLE
                                    binding.playImageButton.setImageResource(R.drawable.record_icon_pause)

                                    // seek bar와 녹음파일 동기화 작업
                                    binding.seekbar.max = player!!.duration
                                    binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                                        override fun onProgressChanged(
                                            seekBar: SeekBar?,
                                            progress: Int,
                                            fromUser: Boolean
                                        ) {
                                            // 사용자가 움직일시 true
                                            if (fromUser) player!!.seekTo(progress)
                                        }

                                        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                                        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                                    })

                                    // seekbar를 1초에 한번씩 현재 재생 위치로 이동시켜준다
                                    while (player!!.isPlaying){
                                        delay(1000)
                                        binding.seekbar.setProgress(player!!.currentPosition)
                                    }
                                }
                            }
                        }
            }
        }

    }
  }
}