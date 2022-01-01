package com.jiib.jnucenter.mvvm.feature.place

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.PlaceRecyclerviewItemBinding
import com.jiib.jnucenter.mvvm.repository.network.retrofit.place.PlaceDTO


/**
 *    PlaceSearchFragment에서 쓰일 RecyclerView 어댑터
 */

class PlaceAdapter(private val find_listener: (String, String, String, String) -> Unit)
    : PagingDataAdapter<PlaceDTO, PlaceAdapter.PlaceViewHolder>(PlaceAdapter.DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(
            DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.place_recyclerview_item,
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, find_listener)
        }
    }

    // 메모리릭 피하기 위해 중첩클래스로 선언
    class PlaceViewHolder(private val binding:PlaceRecyclerviewItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlaceDTO?, find_listener: (String, String, String, String) -> Unit){
            // 이름 세팅
            binding.placeRecyclerviewTitle.text = item?.school_department
            // 찾기 버튼 클릭리스너
            binding.placeLocationButton.setOnClickListener {
                // 위도 경도 구하기
                val temp = item!!.school_location.split(", ")
                val latitude = temp[0]
                val longitude = temp[1]
                // 뷰모델에 장소정보 세팅하고 PlaceMapFragment로 전환
                find_listener(item.school_places, latitude, longitude, item.school_way)
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<PlaceDTO>(){
            override fun areContentsTheSame(oldItem: PlaceDTO, newItem: PlaceDTO): Boolean {
                return (oldItem == newItem)
            }

            override fun areItemsTheSame(oldItem: PlaceDTO, newItem: PlaceDTO): Boolean {
                return (oldItem.school_department == newItem.school_department)
            }
        }
    }
}