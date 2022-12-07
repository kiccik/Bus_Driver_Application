package com.example.bus_driver_application.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bus_driver_application.Adapter.RouteDetailsListAdapter
import com.example.bus_driver_application.Client.RetrofitClient
import com.example.bus_driver_application.DB.Database.AppDatabase
import com.example.bus_driver_application.DTO.RouteDetailsStopDTO
import com.example.bus_driver_application.DTO.SearchRouteDTO
import com.example.bus_driver_application.Service.ApiService
import com.example.bus_driver_application.databinding.ActivityRouteDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RouteDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRouteDetailsBinding
    var turnPosition : Int? = null
    val apiService = RetrofitClient.getApiInstance().create(ApiService::class.java)
    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRouteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(binding.root.context)

        var item : SearchRouteDTO = intent.getSerializableExtra("item") as SearchRouteDTO

        binding.run {
            routeBusName.text = item!!.name
            routeStartEndStop.text = item!!.st_sta_nm + " - " + item!!.ed_sta_nm
            routeStartEndTime.text = item!!.up_first_time + " ~ " + item!!.up_last_time + " | " + item!!.peek + " ~ " + item!!.npeek + "분"

            rvRouteList.apply{
                this.layoutManager = LinearLayoutManager(context)
            }

            getRouteDetails(item)

            routeStartStop.apply {
                this.text = item!!.ed_sta_nm + " 방면"
                this.setOnClickListener {
                    rvRouteList.smoothScrollToPosition(0)
                }
            }

            routeEndStop.apply {
                this.text = item!!.st_sta_nm + " 방면"
                this.setOnClickListener {
                    rvRouteList.smoothScrollToPosition(turnPosition!!)
                }
            }
        }

    }

    fun getRouteDetails(item : SearchRouteDTO){
        apiService.getRouteDetailsList(item.id).enqueue(object :
            Callback<ArrayList<RouteDetailsStopDTO>> {
            override fun onResponse(
                call: Call<ArrayList<RouteDetailsStopDTO>>,
                response: Response<ArrayList<RouteDetailsStopDTO>>,
            ) {
                if(response.isSuccessful && response.code() == 200){
                    Thread(Runnable {
                        var routeid : Int?
                        var stopid : Int?
                        var checked_star = arrayListOf<Boolean>()
                        var checked_alarm = arrayListOf<Boolean>()
                        var body = response.body()!!

                        if(!db.alarmRidingDao().isEmptyAlarmRiding()) {
                            var data = db.alarmRidingDao().getAlarmRiding()

                            routeid = data.route_id
                            stopid = data.stop_id

                            for (i: Int in 0 until body!!.size) {
                                if (response.body()!![i].stop_id == stopid && item.id == routeid)
                                    checked_alarm!!.add(i, true)
                                else
                                    checked_alarm!!.add(i, false)
                            }
                        }

                        else{
                            for (i: Int in 0 until body!!.size) {
                                checked_alarm!!.add(i, false)
                            }
                        }

                        if(!db.bookmarkDao().isEmptyBookmark()) {
                            for (i: Int in 0 until body!!.size) {
                                if (db.bookmarkDao().isExistsBookmarkByRouteId(item.id, response.body()!![i].stop_id))
                                    checked_star!!.add(i, true)
                                else
                                    checked_star!!.add(i, false)
                            }
                        }
                        else{
                            for (i: Int in 0 until body!!.size) {
                                checked_star!!.add(i, false)
                            }
                        }

                        runOnUiThread {
                            binding.rvRouteList.adapter = RouteDetailsListAdapter(body,checked_star, checked_alarm, item, db)
                        }
                    }).start()

                    for (i : Int in 1 until response.body()!!.size){
                        if(!response.body()!!.get(i).updown.equals(response.body()!!.get(i - 1).updown)){
                            turnPosition = i
                            break
                        }
                    }
                    Log.d("retrofit2", response.body()!!.toString())
                }
            }

            override fun onFailure(call: Call<ArrayList<RouteDetailsStopDTO>>, t: Throwable) {
                Log.d("retrofit2","failed" + t)
            }
        })
    }
}