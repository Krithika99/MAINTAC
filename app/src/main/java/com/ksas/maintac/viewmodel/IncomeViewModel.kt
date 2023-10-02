package com.ksas.maintac.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ksas.maintac.response.Response
import com.ksas.maintac.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class IncomeViewModel() : ViewModel() {
    private val db = Firebase.firestore
    val ownerIncomeMap = mutableMapOf<String, List<String>>()
    private val _responseLivedata: MutableLiveData<Response<String>> = MutableLiveData()
    val responseLivedata: LiveData<Response<String>> = _responseLivedata
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    fun saveMonthlyIncomeMap(year: String, month: String) {
        ownerIncomeMap["userid"] = listOf(userId!!, "userid")
        db.collection(Utils.MonthlyIncome).document("$year$month").set(ownerIncomeMap)
            .addOnCompleteListener {
                Log.d("incomeVM", "Added")
                _responseLivedata.value = Response.Success("Added successfully!!!")
                ownerIncomeMap.clear()
            }.addOnFailureListener {
                Log.d("incomeVM", it.message.toString())
                _responseLivedata.value = Response.Failure(it)
            }
    }


    fun getMonthlyIncome(year: String, month: Int) {
        viewModelScope.launch {
            db.collection(Utils.MonthlyIncome)
                .document("$year$month")
                .get().addOnCompleteListener { querySnapshot ->
                    Log.d("test1", querySnapshot.result.data.toString())
                }
                .addOnFailureListener { exception ->
                    Log.e("test", "Error getting documents: ", exception)
                }
        }
    }

    fun updateFieldTest() {
        val documentRef = db.collection(Utils.MonthlyIncome).document("202310")
        viewModelScope.launch {
            val documentSnapshot = documentRef.get().await()

            try {
                val map = documentSnapshot.data
                if (map != null) {
                    val updatedMap = map.toMutableMap()
                    updatedMap["Ms Krithika"] = listOf("400", "Bank")

                    documentRef.set(updatedMap).await()
                }
            } catch (e: Exception) {
                Log.d("updateTest", e.message!!)
            }

        }

    }


}