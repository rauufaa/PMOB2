package com.example.pmob2.view.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pmob2.R
import com.example.pmob2.databinding.ItemParkingLocationBinding
import com.example.pmob2.service.model.ParkingLocationModel
import com.example.pmob2.view.ui.DetailLocationActivity

class ParkingCarAdapter(private val datas: ArrayList<ParkingLocationModel>): RecyclerView.Adapter<ParkingCarAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemParkingLocationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: ParkingLocationModel){
            binding.textViewParkingName.text = data.nama
            binding.textViewSisa.text = data.jumlah
            binding.textViewLocation.text = data.alamat
            Log.w("Isi klik", "${binding}")
            binding.root.setOnClickListener{
                val detailLocationActivityIntent = Intent(it.context, DetailLocationActivity::class.java)
                detailLocationActivityIntent.putExtra("LOKASI_PARKIR", data.nama)
                detailLocationActivityIntent.putExtra("ID_PARKIRAN", data.id)
                detailLocationActivityIntent.putExtra("JENIS_PARKIR", "car_park")

                it.context.startActivity(detailLocationActivityIntent)
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parking_location, parent, false)
        val binding = ItemParkingLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParkingCarAdapter.ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locationModel = datas[position]
        holder.bind(locationModel)
    }
}