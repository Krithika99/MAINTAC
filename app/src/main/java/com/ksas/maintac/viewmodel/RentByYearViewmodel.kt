package com.ksas.maintac.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksas.maintac.model.Rent
import com.ksas.maintac.repository.DatabaseHelperImpl
import kotlinx.coroutines.launch

class RentByYearViewModel : ViewModel() {
    private lateinit var databaseHelperImpl: DatabaseHelperImpl
    private val _rent: MutableLiveData<List<Rent>> = MutableLiveData()
    val rent: LiveData<List<Rent>> = _rent

    fun setDatabaseHelperImpl(databaseHelperImpl: DatabaseHelperImpl) {
        this.databaseHelperImpl = databaseHelperImpl
    }

    fun getRentDetailsByYear(year: String) {
        viewModelScope.launch {
            val rentList = databaseHelperImpl.getRentDetailsByYear(year = year)
            _rent.value = rentList
        }
    }
}