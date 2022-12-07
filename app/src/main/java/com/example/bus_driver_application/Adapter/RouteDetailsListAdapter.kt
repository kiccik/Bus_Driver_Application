package com.example.bus_driver_application.Adapter

//import ac.kr.tukorea.bus_application.Data.Remote.DTO.RouteDetailsStopDTO
//import ac.kr.tukorea.bus_application.View.Activity.KakaoMapActivity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_driver_application.DB.Database.AppDatabase
import com.example.bus_driver_application.DTO.RouteDetailsStopDTO
import com.example.bus_driver_application.DTO.SearchRouteDTO
import com.example.bus_driver_application.View.KakaoMapActivity
import com.example.bus_driver_application.databinding.ItemRecyclerRouteBinding

class RouteDetailsListAdapter(
    private val items: ArrayList<RouteDetailsStopDTO>,
    checked_star: ArrayList<Boolean>,
    checked_alarm: ArrayList<Boolean>,
    item: SearchRouteDTO,
    db: AppDatabase
) : RecyclerView.Adapter<RouteDetailsListAdapter.MyRouteDetails>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRouteDetails {
        val view = ItemRecyclerRouteBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyRouteDetails(view)
    }

    override fun onBindViewHolder(holder: MyRouteDetails, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class MyRouteDetails(private val binding: ItemRecyclerRouteBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(pos : Int){
            val item = items.get(pos)

            when(item.stop_order){
                1 -> binding.upView.visibility = View.INVISIBLE
                items.size -> binding.downView.visibility = View.INVISIBLE
                else -> {
                    binding.upView.visibility = View.VISIBLE
                    binding.downView.visibility = View.VISIBLE
                }
            }

            if(item.stop_order != 1 && !item.updown.equals(items.get(item.stop_order - 2).updown)){
                binding.routeTurn.visibility = View.VISIBLE
                binding.arrowImage.visibility = View.INVISIBLE
            }
            else{
                binding.routeTurn.visibility = View.INVISIBLE
                binding.arrowImage.visibility = View.VISIBLE
            }

            binding.stopName.text = item.stop_name
            binding.mobileNo.text = item.mobile_no

            binding.root.setOnClickListener {
                var intent = Intent(binding.root.context, KakaoMapActivity::class.java)
                binding.root.context.startActivity(intent)
            }
        }
    }
}