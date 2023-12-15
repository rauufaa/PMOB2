package com.example.pmob2.view.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pmob2.R
import com.example.pmob2.service.model.ParkingLocationModel
import com.example.pmob2.view.adapter.ParkingMotorAdapter
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MotorParkingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MotorParkingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val reqData = ArrayList<ParkingLocationModel>()


        db = FirebaseFirestore.getInstance()
        val view = inflater.inflate(R.layout.fragment_motor_parking, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_motor_parking)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ParkingMotorAdapter(reqData)
        recyclerView.adapter = adapter
//        db.collection("parking_locaton")
//            .addSnapshotListener{value, e->
//
//                if (e != null) {
//                    Log.w(TAG, "Listen failed.", e)
//                    return@addSnapshotListener
//                }
//
//                val cities = ArrayList<String>()
//
//                for (doc in value!!) {
//                    val pinLoc = doc.getGeoPoint("lokasi")
//                    val data = ParkingLocationModel(doc.id,doc.data["nama"].toString(), pinLoc)
//                    reqData.add(data)
//                    Log.w("data", "Listen failed. ${data}")
////                    doc.getString("nama")?.let {
////                        cities.add(it)
////                    }
//
//                }
//                adapter.notifyDataSetChanged()
//
//            }
        db.collection("motor_park")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    val pinLoc = document.getGeoPoint("lokasi")
                    val data = ParkingLocationModel(document.id, document.data["jenis"].toString(),document.data["nama"].toString(), document.data["alamat"].toString(), document.data["jumlah"].toString(), pinLoc, document.data["harga"].toString())
                    reqData.add(data)







                    Log.d("Pesan", "${document.id} => ${document.data["nama"]}")
                }
                adapter.notifyDataSetChanged()


            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }



//        Log.d("Pesan", "${getData()} =>")

        return view
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MotorParkingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MotorParkingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}