package com.example.bus_driver_application.Adapter

import com.example.bus_driver_application.databinding.ItemRecyclerBookmarkStopBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookmarkAdapter : RecyclerView.Adapter<BookmarkAdapter.MyStopList>(){
    private val list = listOf(1,2,3)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyStopList {
        val view = ItemRecyclerBookmarkStopBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyStopList(view)
    }

    inner class MyStopList(private val binding: ItemRecyclerBookmarkStopBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(pos : Int){
            binding.textStopName.text = "버스정류장 $pos 번"
            binding.textStopDirection.text = "정류장 방면 $pos 번"
            binding.rvBus.apply {
                adapter = BookmarkBusAdapter()
                layoutManager = LinearLayoutManager(binding.rvBus.context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }
        }
    }

    override fun onBindViewHolder(holder: MyStopList, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}