package com.d3f.musclecarscatalog.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d3f.musclecarscatalog.models.CarModel


sealed class FragmentsData{
    class PassData(val carModel: CarModel): FragmentsData()
    class DefaultData: FragmentsData()
}

class TestViewModel: ViewModel() {
    val state: MutableLiveData<FragmentsData> = MutableLiveData<FragmentsData>().default(initialValue = FragmentsData.DefaultData())

    fun addData(carModel: CarModel) {
        state.postValue(FragmentsData.PassData(carModel))
    }
}