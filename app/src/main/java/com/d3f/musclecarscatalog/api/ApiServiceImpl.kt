package com.d3f.musclecarscatalog.api

import com.d3f.musclecarscatalog.models.CarsResponse

class ApiServiceImpl: ApiService {

    private val apiService = getApi()

    private fun getApi(): ApiService {
        return RetrofitUtils.instance.retrofitDefault.create(ApiService::class.java)
    }

    override suspend fun getCars(): CarsResponse {
        return apiService.getCars()
    }


}