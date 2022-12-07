package com.example.bus_driver_application.View

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bus_driver_application.Adapter.RouteDetailsListAdapter
import com.example.bus_driver_application.Client.RetrofitClient
import com.example.bus_driver_application.DTO.RouteDetailsStopDTO
import com.example.bus_driver_application.Service.ApiService
import com.example.bus_driver_application.databinding.ActivityRouteDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RouteDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRouteDetailsBinding
    var turnPosition : Int? = null
    val apiService = RetrofitClient.getApiInstance().create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRouteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var routeid = intent.getIntExtra("route_id", 0)
        var bus_name = intent.getStringExtra("bus_name")
        var vehicle_number = intent.getStringExtra("vehicle_number")

        binding.run {
            rvRouteList.apply{
                this.layoutManager = LinearLayoutManager(context)
            }

            getRouteDetails(routeid, bus_name!!, vehicle_number!!)

        }

    }

    fun getRouteDetails(route_id : Int, bus_name : String, vehicle_number : String){
        apiService.getRouteDetailsList(route_id).enqueue(object :
            Callback<ArrayList<RouteDetailsStopDTO>> {
            override fun onResponse(
                call: Call<ArrayList<RouteDetailsStopDTO>>,
                response: Response<ArrayList<RouteDetailsStopDTO>>,
            ) {
                if(response.isSuccessful && response.code() == 200){
                    var body = response.body()!!

                    binding.rvRouteList.adapter = RouteDetailsListAdapter(body, this@RouteDetailsActivity, route_id, bus_name, vehicle_number)

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