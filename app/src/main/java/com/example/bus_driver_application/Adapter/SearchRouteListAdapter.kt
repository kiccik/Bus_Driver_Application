package com.example.bus_driver_application.Adapter

//import ac.kr.tukorea.bus_application.Data.Remote.DTO.SearchRouteDTO
//import ac.kr.tukorea.bus_application.View.Activity.RouteDetailsActivity import ac.kr.tukorea.bus_application.databinding.ItemRecyclerSearchBusBinding
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.bus_driver_application.DTO.SearchRouteDTO
import com.example.bus_driver_application.View.RouteDetailsActivity
import com.example.bus_driver_application.databinding.ItemRecyclerSearchBusBinding

class SearchRouteListAdapter(private val items : ArrayList<SearchRouteDTO>) : Adapter<SearchRouteListAdapter.MyBusList>(){
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
                var intent = Intent(binding.root.context, RouteDetailsActivity::class.java)
                intent.putExtra("item", item)
                binding.root.context.startActivity(intent)
            }
        }
    }

}