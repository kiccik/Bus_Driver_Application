package com.example.bus_driver_application.View

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bus_driver_application.Adapter.SearchRouteListAdapter
import com.example.bus_driver_application.Client.RetrofitClient
import com.example.bus_driver_application.DTO.SearchRouteDTO
import com.example.bus_driver_application.Service.ApiService
import com.example.bus_driver_application.databinding.ActivitySearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    val apiService = RetrofitClient.getApiInstance().create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)

        var query = intent.getStringExtra("query")
        var vehicle_number = intent.getStringExtra("vehicle_number")

        setContentView(binding.root)

        binding.run {
            searchBarSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,androidx.appcompat.widget.SearchView.OnQueryTextListener
            {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    query = p0!!
                    getRouteList(query!!, vehicle_number!!)
                    return false
                }
                override fun onQueryTextChange(p0: String?): Boolean {
                    query = p0!!
                    getRouteList(query!!, vehicle_number!!)
                    return false
                }
            })

            searchBarSearch.setQuery(query, true)

            rvBusSearch.apply {
                layoutManager= LinearLayoutManager(context)
            }

        }
    }



    fun getRouteList(name : String, vehicle_number : String){
        apiService.getRouteList(name).enqueue(object : Callback<ArrayList<SearchRouteDTO>> {
            override fun onResponse(
                call: Call<ArrayList<SearchRouteDTO>>,
                response: Response<ArrayList<SearchRouteDTO>>,
            ) {
                if(response.isSuccessful && response.code() == 200){
                    binding.rvBusSearch.adapter = SearchRouteListAdapter(response.body()!!, this@SearchActivity, vehicle_number)
                    Log.d("retrofit2", response.body()!!.toString())
                }
            }

            override fun onFailure(call: Call<ArrayList<SearchRouteDTO>>, t: Throwable) {
                Log.d("retrofit2","failed" + t)
            }
        })
    }
}
