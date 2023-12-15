package com.example.pmob2.view.ui

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pmob2.R
import com.example.pmob2.databinding.ActivityParkingHistoryBinding
import com.example.pmob2.service.model.ParkingHistoryModel
import com.example.pmob2.service.model.ParkingLocationModel
import com.example.pmob2.view.adapter.ParkingHistoryAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ParkingHistoryActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityParkingHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParkingHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = Firebase.auth


        db = Firebase.firestore
        val reqData = ArrayList<ParkingHistoryModel>()
        val dataset = arrayOf("January", "February", "March")
        val customAdapter = ParkingHistoryAdapter(reqData)
        val recyclerView: RecyclerView = binding.recyclerView

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


        recyclerView.adapter = customAdapter

        db.collection("pesanan").whereEqualTo("user", mAuth.currentUser?.email.toString())
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    val pinLoc = document.getGeoPoint("lokasi")
                    val data = ParkingHistoryModel(document.id, document.data["jenis"].toString(),document.data["merkKendaraan"].toString(), document.data["noPlat"].toString(), document.data["mulai"].toString(),document.data["selesai"].toString(), document.data["user"].toString(),document.data["lokasiParkir"].toString(),document.data["biaya"].toString())
                    reqData.add(data)


                    Log.d("Pesan", "${document.id} => ${document.data["nama"]}")
                }
                customAdapter.notifyDataSetChanged()


            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

        binding.buttonBackHistory.setOnClickListener{
            finish()
        }
    }
}