package com.example.pmob2.view.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.pmob2.R
import com.example.pmob2.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.processNextEventInCurrentThread

class HomeActivity : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth



    private lateinit var binding: ActivityHomeBinding

    private var email: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)



//        val textView = findViewById<TextView>(R.id.credential)

        val auth = Firebase.auth
        val user = auth.currentUser

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





// Inside onCreate() method
//        val sign_out_button = findViewById<Button>(R.id.signOut)
//        sign_out_button.setOnClickListener {
//            signOutAndStartSignInActivity()
//        }

        //

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