package com.ksas.maintac.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksas.maintac.model.Rent
import com.ksas.maintac.repository.DatabaseHelperImpl
import kotlinx.coroutines.launch

class RentViewModel :
    ViewModel() {
    private lateinit var databaseHelperImpl: DatabaseHelperImpl
    private val _rent: MutableLiveData<List<Rent>> = MutableLiveData()
    val rent: LiveData<List<Rent>> = _rent

    fun setDataBasHelper(databaseHelperImpl: DatabaseHelperImpl) {
        this.databaseHelperImpl = databaseHelperImpl
    }


    fun insertRentDetails(rent: Rent) {
        viewModelScope.launch {
            databaseHelperImpl.insertRentDetails(rent = rent)
        }
    }

    fun getRentDetails() {
        viewModelScope.launch {
            val rentDetails = databaseHelperImpl.getRentDetails()
            Log.d("Viewmodel", rentDetails.toString())
            _rent.value = rentDetails
            Log.d("Viewmodel", _rent.value?.size.toString())
        }
    }
}