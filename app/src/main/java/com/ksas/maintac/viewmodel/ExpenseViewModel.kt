package com.ksas.maintac.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ksas.maintac.model.MonthlyExpenses
import com.ksas.maintac.utils.Utils
import kotlinx.coroutines.launch

class ExpenseViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    private val _expense = MutableLiveData<MonthlyExpenses>()
    val expense: LiveData<MonthlyExpenses> = _expense

    val billMap = mutableMapOf<String, List<String>>()


    fun getMonthlyExpenses(year: String, month: Int) {
        viewModelScope.launch {
            db.collection(Utils.MonthlyExpense)
                .whereEqualTo("Year", year)
                .whereEqualTo("Month", month)
                .get().addOnCompleteListener { querySnapshot ->
                    Log.d("test1", querySnapshot.result.documents.toString())
                    for (document in querySnapshot.result.documents) {
                        val data = document.toObject(MonthlyExpenses::class.java)
                        Log.d("test", data.toString())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("test", "Error getting documents: ", exception)
                }
        }
    }

    fun saveMonthlyBillMap(year: String, month: String) {
        billMap["userid"] = listOf(userId!!, "")
        db.collection(Utils.MonthlyExpense).document("$year$month").set(billMap)
            .addOnCompleteListener {
                Log.d("TESTEST", "Added")
                billMap.clear()
            }.addOnFailureListener {
                Log.d("TEST", it.message.toString())
            }
    }
}