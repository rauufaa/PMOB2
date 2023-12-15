package com.example.pmob2.view.ui

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pmob2.R
import com.example.pmob2.service.model.ParkingLocationModel
import com.example.pmob2.view.adapter.ParkingHistoryAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ParkingHistoryActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_history)

        db = Firebase.firestore
        val reqData = ArrayList<ParkingLocationModel>()
        val dataset = arrayOf("January", "February", "March")
        val customAdapter = ParkingHistoryAdapter(dataset)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


        recyclerView.adapter = customAdapter

        db.collection("motor_park")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    val pinLoc = document.getGeoPoint("lokasi")
                    val data = ParkingLocationModel(document.id, document.data["jenis"].toString(),document.data["nama"].toString(), document.data["alamat"].toString(), document.data["jumlah"].toString(), pinLoc, document.data["harga"].toString())
                    reqData.add(data)







                    Log.d("Pesan", "${document.id} => ${document.data["nama"]}")
                }
                customAdapter.notifyDataSetChanged()


            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

    }
}