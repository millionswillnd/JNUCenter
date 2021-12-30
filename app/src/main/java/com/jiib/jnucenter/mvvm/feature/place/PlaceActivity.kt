package com.jiib.jnucenter.mvvm.feature.place

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Base64.encode
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.ActivityPlaceBinding
import com.jiib.jnucenter.mvvm.feature.record.RecordViewModel
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
    // 권한설정 REQUEST CODE
    val LOCATION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_place)

        // 뷰모델 초기화
        viewmodel = ViewModelProvider(this, PlaceViewModel.Factory(application))
            .get(PlaceViewModel::class.java)

        // 프래그먼트 초기화
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



        // 현재 위치 권한 요청
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> { }

            else -> requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
        }
    }


    // 현재 위치 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            LOCATION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty()){
                    // 유저가 승인하지 않았을 시 액티비티를 종료
                    if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                        finish()
                    }
                }
            }
        }
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