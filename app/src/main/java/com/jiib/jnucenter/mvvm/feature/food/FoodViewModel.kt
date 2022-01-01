package com.jiib.jnucenter.mvvm.feature.food

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiib.jnucenter.mvvm.repository.FoodRepository
import com.jiib.jnucenter.mvvm.repository.network.retrofit.food.FoodDTO

/**
 *    학식 뷰모델
 */
class FoodViewModel : ViewModel() {

    private val repository = FoodRepository()

    // 학식 식단 리스트 LiveData
    val food_list : MutableLiveData<List<FoodDTO>>
        get() = repository.food_list
    // 날짜
    val food_date : MutableLiveData<String>
        get() = repository.now_date


    // repository에 food api를 요청
    fun getFoodList(){
        repository.getFoodList()
    }

}