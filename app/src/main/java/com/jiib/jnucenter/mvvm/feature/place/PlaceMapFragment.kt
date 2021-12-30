package com.jiib.jnucenter.mvvm.feature.place

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.PlaceKakaomapFragmentBinding
import com.jiib.jnucenter.mvvm.utils.PlaceUtil
import net.daum.android.map.MapView
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint

class PlaceMapFragment : Fragment() {

    private var binding : PlaceKakaomapFragmentBinding? = null
    lateinit var place_viewmodel : PlaceViewModel

    // view가 초기화중이기에 여기서 작업 시 충돌 가능성
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.place_kakaomap_fragment, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        place_viewmodel = ViewModelProvider(requireActivity()).get(PlaceViewModel::class.java)

        // 카카오맵 세팅
        // 위치 정보를 답은 MapPoint 객체
        val map_point = MapPoint.mapPointWithGeoCoord(
            place_viewmodel.latitude.value!!.toDouble(),
            place_viewmodel.longitude.value!!.toDouble()
        )

        // 현재 위치 위경도 구하기
        place_viewmodel.getCurrentLocation(requireContext())

        // 커스텀 말풍선 어댑터 세팅
        binding?.kakaoMapview?.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater, place_viewmodel))

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
        binding?.kakaoMapview?.apply {
            binding?.kakaoMapview?.mapCenterPoint?.mapPointGeoCoord?.latitude
            binding?.kakaoMapview?.mapCenterPoint?.mapPointGeoCoord?.longitude

            // 클릭한 장소의 정보값으로 세팅한 뷰모델 변수들로 위치 설정
            setMapCenterPoint(map_point, true)
            // 줌 설정
            setZoomLevel(1, true)
            zoomIn(true)
            addPOIItem(marker)
        }
    }

    // 메모리 해제
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}


// 커스텀 말풍선 어댑터 클래스
class CustomBalloonAdapter(inflater: LayoutInflater, private val viewmodel :PlaceViewModel) : CalloutBalloonAdapter{

    // 걸리는 시간 구하기 위한 유틸 클래스
    val util = PlaceUtil()

    val balloon = inflater.inflate(R.layout.place_kakaomap_custom_balloon, null)
    val name : TextView = balloon.findViewById(R.id.place_name)
    val way : TextView = balloon.findViewById(R.id.place_way)
    val time : TextView = balloon.findViewById(R.id.place_time)

    // 마커 클릭시 표시할 말풍선 리턴
    override fun getCalloutBalloon(p0: MapPOIItem?): View {

        // 건물 이름과 장소 힌트
        name.text = viewmodel.place_name.value
        way.text = viewmodel.way.value

        // 걸리는 시간 구하기
        val dist = util.getDistance(viewmodel.current_latitutde.value!!.toDouble(),
            viewmodel.latitude.value!!.toDouble(),
            viewmodel.current_longitude.value!!.toDouble(),
            viewmodel.longitude.value!!.toDouble()
        )
        val taking_time = util.getTimeByDistance(dist)
        // 소수점 첫째자리에서 반올림해줘서 넣어준다.
        time.text = "걸어가면 ${String.format("%.0f", taking_time[0])}분, 뛰어가면 ${String.format("%.0f", taking_time[1])}분"

        return balloon
    }

    // 말풍선 클릭 시 표시할 뷰
    override fun getPressedCalloutBalloon(p0: MapPOIItem?): View {
        return balloon
    }
}