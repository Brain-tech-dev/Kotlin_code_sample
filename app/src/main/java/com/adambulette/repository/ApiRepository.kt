package com.adambulette.repository

import com.adambulette.network.apiRequest.LoginRequest
import com.adambulette.network.apiService.ApiService
import com.adambulette.utils.BaseRepository

class ApiRepository(private val api: ApiService): BaseRepository() {
    suspend fun userLogin(requestBody: LoginRequest) = safeApiCall {
        api.login(requestBody)
    }
}
