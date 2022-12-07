package com.example.bus_driver_application.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_riding_table")
data class AlarmRidingEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val route_id : Int,
    val route_name : String,
    val stop_id : Int,
    val stop_name : String
){
    constructor(route_id : Int,
                route_name : String,
                stop_id : Int,
                stop_name : String) : this(0, route_id, route_name, stop_id, stop_name)
}
