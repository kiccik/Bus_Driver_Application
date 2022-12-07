package com.example.bus_driver_application.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.bus_driver_application.DTO.SearchRouteDTO
import com.example.bus_driver_application.View.MainActivity
import com.example.bus_driver_application.View.SearchActivity
import com.example.bus_driver_application.databinding.ItemRecyclerSearchBusBinding

class SearchRouteListAdapter(
    private val items : ArrayList<SearchRouteDTO>,
    val c : Context,
    val vehicle_number : String) : Adapter<SearchRouteListAdapter.MyBusList>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBusList {
        val view = ItemRecyclerSearchBusBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyBusList(view)
    }

    override fun onBindViewHolder(holder: MyBusList, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class MyBusList(private val binding: ItemRecyclerSearchBusBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(pos : Int){
            val item = items.get(pos)

            binding.busName.text = item.name
            binding.busRegion.text = item.region + " | " + item.st_sta_nm + " - " + item.ed_sta_nm
            binding.busTime.text = item.up_first_time + " ~ " + item.up_last_time + " | " + item.peek + " ~ " + item.npeek + "분"

            binding.root.setOnClickListener {
                var activity = c as SearchActivity
                var intent = Intent(c, MainActivity::class.java)

                intent.putExtra("route_id", item.id)
                intent.putExtra("bus_name", item.name)
                intent.putExtra("vehicle_number", vehicle_number)
                activity.startActivity(intent)
                activity.finish()
            }
        }
    }
}