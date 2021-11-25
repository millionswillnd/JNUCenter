package com.example.jnucenter.mvvm.feature.number

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jnucenter.R
import com.example.jnucenter.databinding.NumberRecyclerviewItemBinding
import com.example.jnucenter.mvvm.repository.network.retrofit.numbers.NumbersDTO
import java.security.Permission

class NumberAdapter(val context: Context)
    : PagingDataAdapter<NumbersDTO, NumberAdapter.NumberViewHolder>(DIFF_UTIL){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        return NumberViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.number_recyclerview_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }


    inner class NumberViewHolder(
        private val binding : NumberRecyclerviewItemBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : NumbersDTO) {
            // 앞 자음이 바뀌는 경우 자음 섹션 추가
            if(item.is_first == "1"){
                binding.consonantConst.visibility = View.VISIBLE
                binding.consonant.text = item.consonant
            } else binding.consonantConst.visibility = View.GONE

            binding.departmentNumber.text = item.department_number
            binding.departmentTitle.text = item.department_name

            // 전화번호 다이얼 리스너
            binding.departmentNumber.setOnClickListener{
                val dial_number = item.department_number.removeRange(3,4)
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:062${dial_number}"))
                context.startActivity(intent)
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<NumbersDTO>(){
            override fun areItemsTheSame(oldItem: NumbersDTO, newItem: NumbersDTO): Boolean {
                return (oldItem.department_name == newItem.department_name)
            }

            override fun areContentsTheSame(oldItem: NumbersDTO, newItem: NumbersDTO): Boolean {
                return (oldItem == newItem)
            }
        }
    }
}