package com.example.pmob2.view.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.pmob2.R
import com.example.pmob2.databinding.ActivityDetailLocationBinding
import com.example.pmob2.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.processNextEventInCurrentThread
import java.text.SimpleDateFormat
import java.util.Date

class HomeActivity : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var idParkir: String
    private lateinit var idParkiran: String
    private lateinit var tipePark: String



    private lateinit var binding: ActivityHomeBinding

    private var email: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = Firebase.auth
        db = Firebase.firestore


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)



//        val textView = findViewById<TextView>(R.id.credential)

        val auth = Firebase.auth
        val user = auth.currentUser
        binding.textViewHalloUser.text = "Hallo, ${mAuth.currentUser?.displayName}"




        if (user != null) {
            val userName = user.displayName
//            textView.text = "Welcome, " + userName
        } else {
            // Handle the case where the user is not signed in
        }

//        findViewById<MaterialButton>(R.id.materialButton).setOnClickListener(){
//            val parkingHistory = Intent(this, ParkingHistoryActivity::class.java)
//            startActivity(parkingHistory)
//        }

        binding.materialButton.setOnClickListener(){
            val parkingHistoryActivity = Intent(this, ParkingHistoryActivity::class.java)
            startActivity(parkingHistoryActivity)
        }

//        findViewById<ShapeableImageView>(R.id.profile).setOnClickListener(){
//
//        }

        binding.profile.setOnClickListener(){
            val profileActivity = Intent(this, ProfileActivity::class.java)
            startActivity(profileActivity)
        }

        binding.buttonSearchParking.setOnClickListener(){
            val parkingLocationActivity = Intent(this, ParkingLocationActivity::class.java)
            startActivity(parkingLocationActivity)
        }

        db.collection("pesanan")
            .whereEqualTo("user", mAuth.currentUser?.email)
            .whereEqualTo("on", true)
            .get()
            .addOnSuccessListener {
                documents ->
                    if(!documents.isEmpty){


                        Log.d("Isis parkir", "${documents}")
                        for (document in documents){
                            tipePark = document.data["jenis"].toString()
                            idParkir = document.id
                            idParkiran = document.data["id_parkiran"].toString()
                            binding.buttonSearchParking.isActivated = false
                            binding.buttonSearchParking.isEnabled = false
                            binding.buttonSearchParking.isClickable = false
                            binding.textViewPlatKendaraan.text = document.data["noPlat"].toString()
                            binding.textViewMerkKendaraan.text = document.data["merkKendaraan"].toString()
                            binding.textViewParkingName.text = document.data["lokasiParkir"].toString()
                            binding.cardView.visibility = View.VISIBLE
                            binding.cardView2.visibility = View.GONE
                        }
                        alamatParkiran()
                    }
            }
            .addOnFailureListener{

            }



        selesaiParkir()



// Inside onCreate() method
//        val sign_out_button = findViewById<Button>(R.id.signOut)
//        sign_out_button.setOnClickListener {
//            signOutAndStartSignInActivity()
//        }

        //
        binding.cardView.setOnClickListener{
            val detailParkirIntent = Intent(this, DetailLocationActivity::class.java)
            detailParkirIntent.putExtra("JENIS_PARKIR", tipePark)
            detailParkirIntent.putExtra("ID_PARKIRAN", idParkiran)
            detailParkirIntent.putExtra("homeActivity", true)
            startActivity(detailParkirIntent)
        }
    }

    private fun alamatParkiran(){
        db.collection(tipePark).document(idParkiran)
            .get()
            .addOnSuccessListener {
                document ->
                 binding.textViewLocation.text = document.data?.get("alamat").toString()
            }
    }

    private fun selesaiParkir(){
        binding.buttonSelesaikan.setOnClickListener{
            db.collection("pesanan")
                .document(idParkir).update("on", false,
                                            "selesai", getNow())

                .addOnSuccessListener {
                    binding.buttonSearchParking.isActivated = true
                    binding.buttonSearchParking.isEnabled = true
                    binding.buttonSearchParking.isClickable = true

                    binding.cardView.visibility = View.GONE
                    binding.cardView2.visibility = View.VISIBLE

                }
                .addOnFailureListener{

                }
            db.collection(tipePark).document(idParkiran)
                .update("jumlah", FieldValue.increment(1))
                .addOnSuccessListener {
                    documents ->
                        Log.d("Isi parkiran", "${documents}")
                }
        }
    }

    private fun getNow() : String{
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        val currentDate = sdf.format(Date())
        return currentDate
    }


    private fun signOutAndStartSignInActivity() {
        mAuth.signOut()

        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            // Optional: Update UI or show a message to the user
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}