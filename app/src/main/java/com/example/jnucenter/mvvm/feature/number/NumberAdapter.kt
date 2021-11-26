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
import com.example.jnucenter.mvvm.utils.TextUtils
import java.security.Permission

class NumberAdapter(val context: Context, val textUtils: TextUtils)
    : PagingDataAdapter<NumbersDTO, NumberAdapter.NumberViewHolder>(DIFF_UTIL){


    // 초성, 초깃값에 의미는 없다
    var first_consonant: Char = 'n'
    // 코틀린은 char에 숫자가 안되기에 a를 초기점으로 지정
    var before_consonant: Char = 'a'
    var before_item : NumbersDTO? = null


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

            // 첫번째 아이템이 아닌 경우, 전 아이템의 초성을 구한다
            if(layoutPosition != 0) {
                before_item = getItem(layoutPosition -1)
                before_consonant = textUtils.getFirstConsonant(before_item?.department_name)
            }

            // 아이템의 초성을 할당
            first_consonant = textUtils.getFirstConsonant(item.department_name)

            // 이전 아이템의 초성과 일치하지 않거나, 시작 아이템인경우 자음 섹션을 추가
            if((first_consonant != before_consonant) || (layoutPosition == 0)){
                binding.consonantConst.visibility = View.VISIBLE
                binding.consonant.text = item.consonant
                before_consonant = first_consonant
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