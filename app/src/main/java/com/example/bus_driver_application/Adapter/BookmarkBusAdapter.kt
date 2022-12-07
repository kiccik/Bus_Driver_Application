package com.example.bus_driver_application.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_driver_application.databinding.ItemRecyclerBookmarkBusBinding

class BookmarkBusAdapter : RecyclerView.Adapter<BookmarkBusAdapter.MyBusList>(){
    private val list = listOf(1,2)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBusList {
        val view = ItemRecyclerBookmarkBusBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyBusList(view)
    }

    override fun onBindViewHolder(holder: MyBusList, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyBusList(private val binding: ItemRecyclerBookmarkBusBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(pos : Int){
            binding.textBusNum.text = "버스 $pos 번"
            binding.textRemainTime.text = "남은 시간 $pos 분"
        }
    }

}