package com.example.bus_driver_application.Adapter

//import ac.kr.tukorea.bus_application.Data.Remote.DTO.SearchStopDTO import ac.kr.tukorea.bus_application.databinding.ItemRecyclerSearchStopBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_driver_application.DTO.SearchStopDTO
import com.example.bus_driver_application.databinding.ItemRecyclerSearchStopBinding

class SearchStopListAdapter(private val items : MutableList<SearchStopDTO>) : RecyclerView.Adapter<SearchStopListAdapter.MyStopList>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyStopList {
        val view = ItemRecyclerSearchStopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyStopList(view)
    }

    override fun onBindViewHolder(holder: MyStopList, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class MyStopList(private val binding: ItemRecyclerSearchStopBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(pos : Int){
            binding.stopName.text = items.get(pos).name
            binding.stopRegion.text = items.get(pos).region_name

            binding.stopBookmarkCb.setOnCheckedChangeListener { compoundButton, b ->

            }
        }
    }
}