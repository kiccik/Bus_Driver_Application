package com.example.bus_driver_application.Service

import com.example.bus_driver_application.BuildConfig
import com.example.bus_driver_application.DTO.BusInfoDTO
import com.example.bus_driver_application.DTO.BusUpdateDTO
import com.example.bus_driver_application.DTO.RouteDetailsStopDTO
import com.example.bus_driver_application.DTO.SearchRouteDTO
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
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

    @POST(BuildConfig.ENDPOINT_POST_BUS)
    fun postBus(
        @Body busInfoDTO: BusInfoDTO
    ) : Call<String>

    @DELETE(BuildConfig.ENDPOINT_DELETE_BUS)
    fun deleteBus(
        @Query("routeid")
        routeid : Int,
        @Query("busid")
        busid : String
    ) : Call<String>

    @PUT(BuildConfig.ENDPOINT_PUT_BUS)
    fun putBus(
        @Body busUpdateDTO: BusUpdateDTO
    ) : Call<String>
}