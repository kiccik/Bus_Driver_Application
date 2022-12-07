package com.example.bus_driver_application.DB.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bus_driver_application.DB.DAO.AlarmGettingOffDAO
import com.example.bus_driver_application.DB.DAO.AlarmRidingDAO
import com.example.bus_driver_application.DB.DAO.BookmarkDAO
import com.example.bus_driver_application.DB.Entity.AlarmGettingOffEntity
import com.example.bus_driver_application.DB.Entity.AlarmRidingEntity
import com.example.bus_driver_application.DB.Entity.BookmarkEntity

@Database(entities =
    [BookmarkEntity::class,
    AlarmGettingOffEntity::class,
    AlarmRidingEntity::class
    ], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao() : BookmarkDAO
    abstract fun alarmGettingOffDao() : AlarmGettingOffDAO
    abstract fun alarmRidingDao() : AlarmRidingDAO

    companion object{
        fun getInstance(context: Context) : AppDatabase {
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "busappdb").fallbackToDestructiveMigration().build()
        }
    }
}