package com.example.bus_driver_application.DTO

data class BusUpdateDTO (
    val route_id : Int,
    val bus_id : String,
    val gps_x : Double,
    val gps_y : Double
)