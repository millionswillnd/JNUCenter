package com.jiib.jnucenter.mvvm.feature.record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.RecordRecyclerviewItemBinding
import com.jiib.jnucenter.mvvm.feature.lecture.LectureViewHolder
import com.jiib.jnucenter.mvvm.repository.model.database.room.Records

class RecordAdapter(private val record_list: List<Records>, private val check_list: ArrayList<Int>?) : RecyclerView.Adapter<RecordAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.record_recyclerview_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(record_list!!.get(position).record_title, record_list.get(position).id, check_list)
    }

    override fun getItemCount(): Int {
        // 주입한 아이템 갯수로 리턴 ㅇㅇ 파라미터
        return record_list.size
    }


    class ViewHolder(private val binding : RecordRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root){

        var id : Int? = null

        fun bind(title: String, id:Int, check_list: ArrayList<Int>?){
            binding.recordItemTitle.text = title
            this.id = id

            // 체크박스 삭제 체크 했을 경우 리스너. 액티비티의 어레이리스트 객체에 값을 넣어준다
            binding.checkBox.setOnClickListener {
                if(binding.checkBox.isChecked){
                    check_list?.add(id)
                }
                else {
                    check_list?.remove(id)
                }
            }
        }

    }

}