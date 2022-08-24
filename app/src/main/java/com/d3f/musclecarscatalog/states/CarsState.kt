package com.d3f.musclecarscatalog.states

import com.d3f.musclecarscatalog.models.CarsResponse

sealed class CarsState {
    class SuccessState(val carsResponse: CarsResponse): CarsState()
    class ErrorState: CarsState()
    class DefaultState: CarsState()
}