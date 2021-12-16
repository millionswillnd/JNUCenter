package com.jiib.jnucenter.mvvm.feature.record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jiib.jnucenter.R

class RecordAdapter() : RecyclerView.Adapter<RecordAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.record_recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 저기 뷰홀더에서 초기화한 값 가지고 이리저리 하셈 ㅇㅇ 데이터세팅하라구
        holder
    }

    override fun getItemCount(): Int {
        // 주입한 아이템 갯수로 리턴 ㅇㅇ 파라미터
        return 3
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        // 여기서 뷰 초기화하셈 ㅇㅇ 텍스트뷰 얻어오고
    }

}