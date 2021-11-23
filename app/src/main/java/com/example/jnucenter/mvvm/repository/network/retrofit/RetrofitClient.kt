package com.example.jnucenter.mvvm.repository.network.retrofit

import com.example.jnucenter.mvvm.repository.network.retrofit.weather.WeatherApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object{

        private var instance : Retrofit? = null
        // 추후 실제 서버 base url로 수정
        private const val BASE_URL = "http://172.30.1.37:8080"

        // 로깅용 인터셉터
        val interceptor = HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(15, TimeUnit.SECONDS)   // timeout 설정
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        fun getInstance() : Retrofit {
            if(instance == null){
                instance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return instance!!
        }
    }
}