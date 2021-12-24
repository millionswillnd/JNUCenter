package com.jiib.jnucenter.mvvm.feature.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

class PlaceSearchFragment : Fragment() {

    private var binding : PlaceRecyclerviewFragmentBinding? = null
    lateinit var place_viewmodel : PlaceViewModel
    lateinit var adapter: PlaceAdapter
    lateinit var recycler_view : RecyclerView

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

        place_viewmodel = ViewModelProvider(requireActivity()).get(PlaceViewModel::class.java)

        // 리사이클러뷰 세팅
        adapter = PlaceAdapter({name:String,latitude:String,longitude:String,way:String -> findClickListener(name, latitude, longitude, way)})
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

    // 검색시 페이징 데이터를 액티비티에서 받아와 update
    suspend fun updateAdapter(paging_data : PagingData<PlaceDTO>){
        adapter.submitData(paging_data)
    }

    // 어댑터에 넘겨줄 찾기 버튼 클릭 리스너
    // 뷰모델에 장소 정보를 세팅하고 kakaomap 프래그먼트로 교체
    private fun findClickListener(place_name: String, latitude: String, longitude: String, way: String){

        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                place_viewmodel.latitude.postValue(latitude)
                place_viewmodel.longitude.postValue(longitude)
                place_viewmodel.place_name.postValue(place_name)
                place_viewmodel.way.postValue(way)
            }

            withContext(Dispatchers.Main){
                job.join()
                val activity = activity as PlaceActivity
                activity.changeFragment("map")
            }
        }
    }
}