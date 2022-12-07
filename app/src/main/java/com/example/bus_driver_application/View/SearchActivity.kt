package com.example.bus_driver_application.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bus_driver_application.Adapter.SearchRouteListAdapter
import com.example.bus_driver_application.Adapter.SearchStopListAdapter
import com.example.bus_driver_application.Client.RetrofitClient
import com.example.bus_driver_application.DTO.SearchRouteDTO
import com.example.bus_driver_application.DTO.SearchStopDTO
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

        binding.run {
            searchBus.setOnClickListener {
                searchBarSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,androidx.appcompat.widget.SearchView.OnQueryTextListener
                {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        query = p0!!
                        getRouteList(query!!)
                        return false
                    }
                    override fun onQueryTextChange(p0: String?): Boolean {
                        query = p0!!
                        getRouteList(query!!)
                        return false
                    }
                })
                rvBusSearch.visibility = View.VISIBLE
                rvStopSearch.visibility = View.GONE

                searchBarSearch.setQuery(query, true)

            }
            searchBus.callOnClick()

            searchStop.setOnClickListener {
                searchBarSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,androidx.appcompat.widget.SearchView.OnQueryTextListener
                {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        query = p0!!
                        getStopList(query!!)
                        return false
                    }
                    override fun onQueryTextChange(p0: String?): Boolean {
                        query = p0!!
                        getStopList(query!!)
                        return false
                    }
                })
                rvBusSearch.visibility = View.GONE
                rvStopSearch.visibility = View.VISIBLE

                searchBarSearch.setQuery(query, true)
            }

            rvBusSearch.apply {
                layoutManager= LinearLayoutManager(context)
            }

            rvStopSearch.apply {
                layoutManager = LinearLayoutManager(context)
            }
        }
        setContentView(binding.root)
    }

    fun getStopList(name : String){
        apiService.getStopList(name).enqueue(object : Callback<ArrayList<SearchStopDTO>> {
            override fun onResponse(
                call: Call<ArrayList<SearchStopDTO>>,
                response: Response<ArrayList<SearchStopDTO>>,
            ) {
                if(response.isSuccessful && response.code() == 200){
                    binding.rvStopSearch.adapter = SearchStopListAdapter(response.body()!!)
                    Log.d("retrofit2", response.body()!!.toString())
                }
            }

            override fun onFailure(call: Call<ArrayList<SearchStopDTO>>, t: Throwable) {
                Log.d("retrofit2","failed" + t)
            }
        })
    }

    fun getRouteList(name : String){
        apiService.getRouteList(name).enqueue(object : Callback<ArrayList<SearchRouteDTO>> {
            override fun onResponse(
                call: Call<ArrayList<SearchRouteDTO>>,
                response: Response<ArrayList<SearchRouteDTO>>,
            ) {
                if(response.isSuccessful && response.code() == 200){
                    binding.rvBusSearch.adapter = SearchRouteListAdapter(response.body()!!)
                    Log.d("retrofit2", response.body()!!.toString())
                }
            }

            override fun onFailure(call: Call<ArrayList<SearchRouteDTO>>, t: Throwable) {
                Log.d("retrofit2","failed" + t)
            }
        })
    }
}
