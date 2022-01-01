package com.jiib.jnucenter.mvvm.feature.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 *   메인액티비티에서 쓸 아이콘 ViewPager
 */
class IconViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa){
    override fun getItemCount(): Int {
        return 3
    }

    // 기능 확장시 2,3도 맞는 프래그먼트를 만들어서 넣어주자
    override fun createFragment(position: Int): Fragment {
        when(position){
            1 -> return IconFirstFragment()
            2 -> return IconFirstFragment()
            3 -> return IconFirstFragment()
        }

        return IconFirstFragment()
    }
}