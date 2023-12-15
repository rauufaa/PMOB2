package com.example.pmob2.service.model

import com.google.firebase.firestore.GeoPoint

data class ParkingHistoryModel(
    val id: String = "",
    val jenis: String = "",
    val merkKendaraan: String = "",
    val noPlat: String = "",
    val mulai: String = "",
    val selesai: String = "",
    val user: String = "",
    val lokasiParkir: String = "",
    val biaya: String = "" ,

)
