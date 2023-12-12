package com.example.pmob2.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pmob2.R
import com.example.pmob2.view.adapter.ParkingHistoryAdapter

class ParkingHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_history)


        val dataset = arrayOf("January", "February", "March")
        val customAdapter = ParkingHistoryAdapter(dataset)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


        recyclerView.adapter = customAdapter

    }
}