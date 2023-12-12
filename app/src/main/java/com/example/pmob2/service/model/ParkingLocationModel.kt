package com.example.pmob2.service.model

import com.google.firebase.firestore.GeoPoint

data class ParkingLocationModel(
    val id: String = "",
    val nama: String = "",
    val lokasi: GeoPoint?,
    val biaya: Int = 1
)
