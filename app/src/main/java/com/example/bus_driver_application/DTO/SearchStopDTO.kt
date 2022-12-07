package com.example.bus_driver_application.DTO

import com.google.gson.annotations.SerializedName

data class SearchStopDTO (
    @SerializedName("id")
    val id : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("gps_x")
    val gps_x : Double,
    @SerializedName("gps_y")
    val gps_y : Double,
    @SerializedName("region_name")
    val region_name : String,
    @SerializedName("mobile_no")
    val mobile_no : String
)