package com.jiib.jnucenter.mvvm.repository.network.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object{

        private var instance : Retrofit? = null
        // 추후 실제 서버 base url로 수정 + string에 넣자
        private const val BASE_URL = "http://172.30.1.48:8080"
        // aws에 올린 주소
        // private const val BASE_URL = "http://3.23.52.39:8080"

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

        var gson = GsonBuilder().setLenient().create()

        fun getInstance() : Retrofit {
            if(instance == null){
                instance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return instance!!
        }
    }
}