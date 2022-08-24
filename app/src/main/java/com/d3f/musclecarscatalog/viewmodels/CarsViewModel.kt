package com.d3f.musclecarscatalog.viewmodels

import androidx.lifecycle.MutableLiveData
import com.d3f.musclecarscatalog.api.ApiServiceImpl
import com.d3f.musclecarscatalog.states.CarsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CarsViewModel: CoroutineScope {
    val state: MutableLiveData<CarsState> = MutableLiveData<CarsState>().default(initialValue = CarsState.DefaultState())
    private val apiService = ApiServiceImpl()

    fun getCars() {
        state.set(newValue = CarsState.DefaultState())

        launch {
            try {
                val cars = apiService.getCars()
                state.postValue(CarsState.SuccessState(cars))
            } catch (e: Exception) {
                state.postValue(CarsState.ErrorState())
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.IO


}

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { value = initialValue }
fun <T> MutableLiveData<T>.set(newValue: T) = apply { setValue(newValue) }