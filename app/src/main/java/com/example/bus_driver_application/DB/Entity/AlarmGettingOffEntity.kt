package com.example.bus_driver_application.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_get_off_table")
data class AlarmGettingOffEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val route_id : Int,
    val route_name : String,
    val gps_x : Double,
    val gps_y : Double
){
    constructor(route_id : Int,
                route_name : String,
                gps_x : Double,
                gps_y : Double) : this(0, route_id, route_name, gps_x, gps_y)
}
