package com.ksas.maintac.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ksas.maintac.response.Response
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    private val _incomeState: MutableState<DocumentSnapshot?> = mutableStateOf(null)
    private val _expenseState: MutableState<DocumentSnapshot?> = mutableStateOf(null)
    private val _response: MutableLiveData<Response<String>> = MutableLiveData()

    val incomeState: State<DocumentSnapshot?> = _incomeState
    val expenseState: State<DocumentSnapshot?> = _expenseState
    val response: LiveData<Response<String>> = _response

    fun showIncome(year: String?, month: String?) {
        viewModelScope.launch {
            db.collection("Monthly Income").document("$year$month").get()
                .addOnCompleteListener { querySnapShot ->
                    _incomeState.value = querySnapShot.result
                    _response.value = Response.Success("$userId")
                }.addOnFailureListener {
                    _response.value = Response.Failure(it)
                }
        }

    }


    fun showExpense(year: String?, month: String?) {
        viewModelScope.launch {
            db.collection("Monthly Expense").document("$year$month").get()
                .addOnCompleteListener { querySnapshot ->
                    _expenseState.value = querySnapshot.result
                    _response.value = Response.Success("$userId")
                }.addOnFailureListener {
                    _response.value = Response.Failure(it)
                }
        }
    }

    fun editDetails(incomeExpenseState: DocumentSnapshot?, year: String, month: String) {

    }

}