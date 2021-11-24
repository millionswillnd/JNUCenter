package com.example.jnucenter.mvvm.feature.number

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jnucenter.R
import com.example.jnucenter.databinding.NumberRecyclerviewItemBinding
import com.example.jnucenter.mvvm.repository.network.retrofit.numbers.NumbersDTO

class NumberAdapter : PagingDataAdapter<NumbersDTO, NumberAdapter.NumberViewHolder>(DIFF_UTIL){

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

        fun bind(item : NumbersDTO){
            binding.departmentTitle.text = item.department_name
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<NumbersDTO>(){
            override fun areItemsTheSame(oldItem: NumbersDTO, newItem: NumbersDTO): Boolean {
                return (oldItem.department_number == newItem.department_number)
            }

            override fun areContentsTheSame(oldItem: NumbersDTO, newItem: NumbersDTO): Boolean {
                return (oldItem == newItem)
            }
        }
    }
}