package com.example.jnucenter.mvvm.feature.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class IconViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return IconFirstFragment()
    }
}