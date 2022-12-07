package com.example.bus_driver_application.Service

import com.example.bus_driver_application.BuildConfig
import com.example.bus_driver_application.DTO.RouteDetailsStopDTO
import com.example.bus_driver_application.DTO.SearchRouteDTO
import com.example.bus_driver_application.DTO.SearchStopDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(BuildConfig.ENDPOINT_GET_STOP_LIST)
    fun getStopList(
        @Query("name")
        name : String
    ) : Call<ArrayList<SearchStopDTO>>

    @GET(BuildConfig.ENDPOINT_GET_ROUTE_LIST)
    fun getRouteList(
        @Query("name")
        name : String
    ) : Call<ArrayList<SearchRouteDTO>>

    @GET(BuildConfig.ENDPOINT_GET_ROUTE_DETAILS_LIST)
    fun getRouteDetailsList(
        @Query("route_id")
        route_id : Int
    ) : Call<ArrayList<RouteDetailsStopDTO>>
}