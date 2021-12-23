package com.jiib.jnucenter.mvvm.feature.place

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.PlaceKakaomapFragmentBinding
import net.daum.android.map.MapView
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint

class PlaceMapFragment : Fragment() {

    lateinit var binding : PlaceKakaomapFragmentBinding
    lateinit var place_viewmodel : PlaceViewModel
    private var kakao_map_view : MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.place_kakaomap_fragment, container, false)
        place_viewmodel = ViewModelProvider(requireActivity()).get(PlaceViewModel::class.java)

        // 카카오맵 세팅
        // 위치 정보를 답은 MapPoint 객체
        val map_point = MapPoint.mapPointWithGeoCoord(
            place_viewmodel.latitude.value!!.toDouble(),
            place_viewmodel.longitude.value!!.toDouble()
        )
        // 커스텀 말풍선 어댑터 세팅
        binding.kakaoMapview.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater, place_viewmodel))
        // 지도 위에 정보를 표시할 POI 객체
        val marker = MapPOIItem()
        marker.apply {
            itemName = place_viewmodel.place_name.value
            tag = 0
            mapPoint = map_point
            markerType = MapPOIItem.MarkerType.BluePin
            selectedMarkerType = MapPOIItem.MarkerType.RedPin
        }
        // 세팅
        binding.kakaoMapview.apply {
            // 클릭한 장소의 정보값으로 세팅한 뷰모델 변수들로 위치 설정
            setMapCenterPoint(map_point, true)
            // 줌 설정
            setZoomLevel(1, true)
            zoomIn(true)
            addPOIItem(marker)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}


// 커스텀 말풍선 어댑터 클래스
class CustomBalloonAdapter(inflater: LayoutInflater, private val viewmodel :PlaceViewModel) : CalloutBalloonAdapter{
    val balloon = inflater.inflate(R.layout.place_kakaomap_custom_balloon, null)
    val name : TextView = balloon.findViewById(R.id.place_name)
    val way : TextView = balloon.findViewById(R.id.place_way)

    // 마커 클릭시 표시할 말풍선 리턴
    override fun getCalloutBalloon(p0: MapPOIItem?): View {
        name.text = viewmodel.place_name.value
        way.text = viewmodel.way.value
        return balloon
    }

    // 말풍선 클릭 시 표시할 뷰
    override fun getPressedCalloutBalloon(p0: MapPOIItem?): View {
        return balloon
    }
}