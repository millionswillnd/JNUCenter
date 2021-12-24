package com.jiib.jnucenter.mvvm.feature.lecture

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.LectureRecyclerviewItemBinding
import com.jiib.jnucenter.mvvm.repository.network.retrofit.lecture.LectureDTO
import com.jiib.jnucenter.mvvm.utils.LectureUtil

class LectureAdapter(val lecture_util: LectureUtil, val lecture_date_list: List<LectureDTO>, val context: Context)
    : RecyclerView.Adapter<LectureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
        return LectureViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.lecture_recyclerview_item,
                parent,
                false
            ),
            lecture_util,
            context
        )
    }

    override fun getItemCount(): Int {
        return lecture_date_list.size
    }

    override fun onBindViewHolder(holder: LectureViewHolder, position: Int) {
        holder.bind(lecture_date_list.get(position))
    }
}


// 이너클래스로 구현할 경우 외부클래스 참조를 가지기에 top-level로 선언
 class LectureViewHolder(
     private val binding : LectureRecyclerviewItemBinding,
     private val lecture_util : LectureUtil,
     private val context: Context
     ):RecyclerView.ViewHolder(binding.root){

     fun bind(item: LectureDTO){
         // 제목 세팅
         binding.lectureCustomview.setTitle(item.lec_title)

         // 남은 기한 세팅. 현재 날짜와의 차이를 구해준다.
         val reserved_day = lecture_util.getReservedDate(item.lec_date).toString()
         binding.lectureCustomview.setDate("D-$reserved_day")

         // 배경색을 적용해준다. 만약 남은 일수가 2일 이하일 경우 빨간색으로 지정해준다.
         if(reserved_day.toInt() <= 2)
             binding.lectureCustomview.setBackColor(ContextCompat.getColor(context, R.color.lecture_custom_red))
         else binding.lectureCustomview.setBackColor(ContextCompat.getColor(context, R.color.lecture_custom_bright_green))

     }
 }