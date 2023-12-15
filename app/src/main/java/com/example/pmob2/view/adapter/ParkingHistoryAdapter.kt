package com.example.pmob2.view.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pmob2.R
import com.example.pmob2.databinding.ItemParkingHistoryBinding
import com.example.pmob2.service.model.ParkingHistoryModel
import com.example.pmob2.view.ui.DetailLocationActivity

class ParkingHistoryAdapter(private val datas: ArrayList<ParkingHistoryModel>) : RecyclerView.Adapter<ParkingHistoryAdapter.ViewHolder>() {
    class ViewHolder(private var binding: ItemParkingHistoryBinding) : RecyclerView.ViewHolder(binding.root){


        fun bind(data: ParkingHistoryModel){
            binding.textViewParkingName.text = data.lokasiParkir
            binding.textViewBiayaHistory.text = data.biaya
            binding.textViewMulai.text = data.mulai
            binding.textViewSelesai.text = "Selesai : "+data.selesai
            binding.textViewMerkHistory.text = data.merkKendaraan
            binding.textViewPlatKendaraanHistory.text = data.noPlat
//            binding.root.setOnClickListener{
//                val detailLocationActivityIntent = Intent(it.context, DetailLocationActivity::class.java)
//                detailLocationActivityIntent.putExtra("LOKASI_PARKIR", data.nama)
//                detailLocationActivityIntent.putExtra("ID_PARKIRAN", data.id)
//                detailLocationActivityIntent.putExtra("JENIS_PARKIR", "motor_park")
//                it.context.startActivity(detailLocationActivityIntent)
//            }
        }

//        init {
//            // Define click listener for the ViewHolder's View
//            textView = view.findViewById(R.id.textViewMulai)
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_parking_history, parent, false)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parking_history, parent, false)
        val binding = ItemParkingHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener{
            Log.w("Isi klik", "${binding}")
        }
        return ViewHolder(binding)
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locationModel = datas[position]
        holder.bind(locationModel)
    }
}