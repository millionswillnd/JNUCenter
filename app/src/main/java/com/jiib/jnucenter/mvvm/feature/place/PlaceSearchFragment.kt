package com.jiib.jnucenter.mvvm.feature.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.PlaceRecyclerviewFragmentBinding
import com.jiib.jnucenter.mvvm.repository.network.retrofit.place.PlaceDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *   장소 검색 시 쓰일 프래그먼트
 */

class PlaceSearchFragment : Fragment() {

    private var binding : PlaceRecyclerviewFragmentBinding? = null
    private lateinit var place_viewmodel : PlaceViewModel
    private lateinit var adapter: PlaceAdapter
    private lateinit var recycler_view : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.place_recyclerview_fragment, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰모델 초기화
        place_viewmodel = ViewModelProvider(requireActivity()).get(PlaceViewModel::class.java)

        // 리사이클러뷰 세팅
        adapter = PlaceAdapter({name:String,latitude:String,longitude:String,way:String
            -> findClickListener(name, latitude, longitude, way)})
        recycler_view = binding?.placeRecyclerview!!
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )

        // 페이징 처리
        CoroutineScope(Dispatchers.IO).launch {
            place_viewmodel.getPlaces().collectLatest {
                adapter.submitData(it)
            }
        }
    }


    // 메모리 해제
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // 검색시 페이징 데이터를 받아와 어댑터를 update
    suspend fun updateAdapter(paging_data : PagingData<PlaceDTO>){
        adapter.submitData(paging_data)
    }

    // 어댑터에 넘겨줄 찾기 버튼 클릭 리스너
    private fun findClickListener(place_name: String, latitude: String, longitude: String, way: String){
        // 뷰모델 LiveData에 이름, 위도, 경도, 장소힌트 세팅
        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                place_viewmodel.latitude.postValue(latitude)
                place_viewmodel.longitude.postValue(longitude)
                place_viewmodel.place_name.postValue(place_name)
                place_viewmodel.way.postValue(way)
            }

            // MapFragment로 교체
            withContext(Dispatchers.Main){
                job.join()
                val activity = activity as PlaceActivity
                activity.changeFragment("map")
            }
        }
    }
}