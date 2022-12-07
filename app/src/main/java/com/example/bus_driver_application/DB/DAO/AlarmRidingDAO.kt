package com.example.bus_driver_application.DB.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bus_driver_application.DB.Entity.AlarmRidingEntity

@Dao
interface AlarmRidingDAO {
    @Query("select * from alarm_riding_table ar")
    fun getAlarmRiding() : AlarmRidingEntity

    @Query("select not exists(select ar.id from alarm_riding_table ar)")
    fun isEmptyAlarmRiding() : Boolean

    @Insert
    fun insertAlarmRiding(vararg alarmRidingEntity: AlarmRidingEntity)

    @Delete
    fun deleteAlarmRiding(alarmRidingEntity: AlarmRidingEntity)
}