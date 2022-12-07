package com.example.bus_driver_application.DTO

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchRouteDTO(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("st_sta_nm")
    val st_sta_nm : String,
    @SerializedName("ed_sta_nm")
    val ed_sta_nm : String,
    @SerializedName("up_first_time")
    val up_first_time : String,
    @SerializedName("up_last_time")
    val up_last_time : String,
    @SerializedName("peek_alloc")
    val peek : String,
    @SerializedName("npeek_alloc")
    val npeek :String,
    @SerializedName("region_name")
    val region : String
) : Serializable