package com.ksas.maintac.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ksas.maintac.model.Flat
import com.ksas.maintac.model.Owner
import kotlinx.coroutines.launch

class AddViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    private var _flats: MutableList<Flat> = mutableListOf()

    private val _flatsLivedata = MutableLiveData<List<Flat>>()
    val flatsLivedata: LiveData<List<Flat>> get() = _flatsLivedata

    fun fetchOwnerDetails() {
        viewModelScope.launch {
            db.collection("OwnerDetails").get().addOnCompleteListener { querySnapshot ->
                _flats.clear()
                for (document in querySnapshot.result.documents) {
                    // Access the document data here
                    val flatNo = document.getString("flatNo")!!
                    val ownerName = document.getString("ownerName")!!
                    val tenant = document.getString("tenant")!!
                    val flat = Flat(flatNo = flatNo, ownerName, tenant)
                    _flats.add(flat)

                    // Do something with the retrieved data
                    println("Flat No: $flatNo, Owner Name: $ownerName, Tenant: $tenant")
                }
                _flatsLivedata.value = _flats
            }
        }
    }

    fun storeOwnerDetails(mOwner: String, mFlat: String, mTenant: String) {
        val owner = Owner(mFlat, mOwner, mTenant)
        if (userId != null) {
            db.collection("OwnerDetails").document(mFlat).set(owner).addOnCompleteListener {
                Log.d("TEST", "Added!!")
            }.addOnFailureListener {
                Log.d("TEST", it.message.toString())
            }
        }

    }
}
