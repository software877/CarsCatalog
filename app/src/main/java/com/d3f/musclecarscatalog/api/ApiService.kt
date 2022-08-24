package com.d3f.musclecarscatalog.api

import com.d3f.musclecarscatalog.models.CarsResponse
import retrofit2.http.GET

interface ApiService {

    @GET("cars.json")
    suspend fun getCars(): CarsResponse

}