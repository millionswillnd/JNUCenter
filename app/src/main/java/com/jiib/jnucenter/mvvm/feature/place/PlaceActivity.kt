package com.jiib.jnucenter.mvvm.feature.place

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Base64.encode
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.ActivityPlaceBinding
import com.jiib.jnucenter.mvvm.repository.network.retrofit.place.PlaceService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.daum.android.map.coord.MapCoordLatLng
import net.daum.mf.map.api.MapView
import java.lang.Exception
import java.security.MessageDigest
import java.security.Signature

class PlaceActivity : AppCompatActivity(), FragListener {

    lateinit var binding : ActivityPlaceBinding
    lateinit var viewmodel : PlaceViewModel
    lateinit var search_fragment : PlaceSearchFragment
    lateinit var kakaomap_fragment : PlaceMapFragment
    // 맵에서 검색 기능 사용 시 프래그먼트 전환을 위한 플래그
    var map_flag : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_place)

        viewmodel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        initFragment()

        // 검색 필터
        binding.placeSearchTitle.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {

                // 카카오 맵 프래그먼트에서 검색 기능 사용 경우
                if (map_flag == true){
                    changeFragment("search")
                    map_flag = false
                }

                lifecycleScope.launch(Dispatchers.IO) {
                    viewmodel.getPlaceByName(newText!!).collectLatest {
                        search_fragment.updateAdapter(it)
                    }
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.placeSearchTitle.clearFocus()
                return true
            }
        })
    }

    // 프래그먼트 초기 세팅
    private fun initFragment(){
        search_fragment = PlaceSearchFragment()
        kakaomap_fragment = PlaceMapFragment()
        commitFragment(search_fragment)
    }

    private fun commitFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.place_fragment_container, fragment)
            .commit()
    }

    // 프래그먼트 바꿀 시 사용
    override fun changeFragment(frag_name: String) {
        if (frag_name == "map"){
            commitFragment(kakaomap_fragment)
            map_flag = true
        }
        else commitFragment(search_fragment)
    }
}