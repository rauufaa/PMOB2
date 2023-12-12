package com.example.pmob2.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pmob2.R
import com.example.pmob2.databinding.ItemParkingLocationBinding

import com.example.pmob2.service.model.ParkingLocationModel

class ParkingMotorAdapter(private val datas: ArrayList<ParkingLocationModel>): RecyclerView.Adapter<ParkingMotorAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemParkingLocationBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data: ParkingLocationModel){
            binding.textViewParkingname.text = data.nama
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parking_location, parent, false)
        val binding = ItemParkingLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locationModel = datas[position]
        holder.bind(locationModel)

    }
}