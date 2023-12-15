package com.example.pmob2.view.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import com.example.pmob2.R
import com.example.pmob2.databinding.ActivityDetailLocationBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date

class DetailLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailLocationBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var map: HashMap<String, Any>
    private lateinit var harga: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = Firebase.auth
        db = Firebase.firestore

        db.collection(intent.getStringExtra("JENIS_PARKIR").toString())
            .document(intent.getStringExtra("ID_PARKIRAN").toString())
            .get()
            .addOnSuccessListener {
                document->
                    binding.textViewBiaya.text = "Biaya :   ${document.data?.get("biaya").toString()}"
                    binding.textViewOperasional.text = "Jam Operasional :   ${document.data?.get("operasional").toString()}"
                    binding.textViewLocation.text = "Lokasi :   ${document.data?.get("alamat").toString()}"
                    binding.textViewSisaDetail.text = "Sisa : ${document.data?.get("jumlah")}"
                    harga = document.data?.get("biaya").toString()
//                    map["latitude"] = document.getGeoPoint("lokasi")?.latitude.toString()
//                    map["longitude"] = document.getGeoPoint("lokasi")?.longitude.toString()
                    binding.buttonArahLokasi.setOnClickListener {
                        var mode:String
                        if(intent.getStringExtra("JENIS_PARKIR").toString() == "motor_park"){
                            mode = "l"
                        }else mode="d"

                        val gmmIntentUri = Uri.parse("google.navigation:q=${document.getGeoPoint("lokasi")?.latitude.toString()},${document.getGeoPoint("lokasi")?.longitude.toString()}&mode=${mode}&avoid=tf")

                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

                        mapIntent.setPackage("com.google.android.apps.maps")

                        startActivity(mapIntent)
                    }

            }
        tambahData()

        binding.buttonBack3.setOnClickListener{
            finish()

        }
        if(intent.getBooleanExtra("homeActivity", false)){
            binding.buttonPesanParkir.isActivated = false
            binding.buttonPesanParkir.isEnabled = false
            binding.buttonPesanParkir.isClickable = false

        }





    }
    private fun tambahData(){
//        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
//        val pekerjaanRef: DatabaseReference = database.getReference("Pekerjaan")
        binding.buttonPesanParkir.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_pesan_parkir, null)

            MaterialAlertDialogBuilder(this)
                .setTitle("Pesan Parkir")
                .setView(dialogView)
                .setPositiveButton("Tambah") { dialog, _ ->
                    val platKendaraan = dialogView.findViewById<EditText>(R.id.editTextNoPlatKendaraan).text.toString()
                    val merkKendaraan = dialogView.findViewById<EditText>(R.id.editTextMerkKendaraan).text.toString()
                    val parkirData = HashMap<String, Any>()
                    parkirData["lokasiParkir"] = intent.getStringExtra("LOKASI_PARKIR").toString()
                    parkirData["noPlat"] = platKendaraan
                    parkirData["merkKendaraan"] = merkKendaraan
                    parkirData["on"] = true
                    parkirData["biaya"] = harga
                    parkirData["user"] = mAuth.currentUser?.email.toString()
                    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
                    val currentDate = sdf.format(Date())

                    parkirData["mulai"] = currentDate
                    parkirData["selesai"] = ""
                    parkirData["jenis"] = intent.getStringExtra("JENIS_PARKIR").toString()
                    parkirData["id_parkiran"] = intent.getStringExtra("ID_PARKIRAN").toString()


//                    val newPekerjaanRef = pekerjaanRef.push()
//                    newPekerjaanRef.setValue(pekerjaanData)

                    toFirebase(parkirData)
                    updateParkiran(intent.getStringExtra("JENIS_PARKIR").toString(), intent.getStringExtra("ID_PARKIRAN").toString())

                    dialog.dismiss()

                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }

                .show()

//            dialogView.findViewById<Button>(R.id.btnUpload).setOnClickListener {
//                choosePicture()
//            }
        }
    }

    private fun toFirebase(user: HashMap<String, Any>){
        db.collection("pesanan")
            .add(user)
            .addOnSuccessListener {
                val homeIntent = Intent(this, HomeActivity::class.java)
                startActivity(homeIntent)
                finishAffinity()
            }
    }

    private fun updateParkiran(jenis: String, id:String){
        db.collection(jenis)
            .document(id)
            .update("jumlah", FieldValue.increment(-1))
    }
}