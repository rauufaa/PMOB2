package com.example.pmob2.service.model

import com.google.firebase.firestore.GeoPoint

data class ParkingLocationModel(
    val id: String = "",
    val jenis: String = "",
    val nama: String = "",
    val alamat: String = "",
    val jumlah: String = "",
    val lokasi: GeoPoint?,
    val biaya: String = "",
    val idParkiran: String = ""
)
