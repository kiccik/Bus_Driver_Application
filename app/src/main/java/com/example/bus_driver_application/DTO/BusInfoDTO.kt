package com.example.bus_driver_application.DTO

data class BusInfoDTO(
    val id : Long,
    val route_id : Int,
    val bus_id : String,
    val init_order : Int,
    val gps_x : Double,
    val gps_y : Double,
    val current_stop : Int
)
