package com.example.bus_driver_application.Client


import com.example.bus_driver_application.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance : Retrofit? = null

    fun getApiInstance() : Retrofit{
        if(instance == null){
            instance = Retrofit.Builder()
                .baseUrl(BuildConfig.URL_BUS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }
}