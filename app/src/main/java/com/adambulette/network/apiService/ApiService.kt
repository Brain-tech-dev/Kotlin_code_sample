package com.adambulette.network.apiService

import com.adambulette.network.apiRequest.ForgotPasswordSentMailRequest
import com.adambulette.network.apiRequest.LoginRequest
import com.adambulette.network.dataModel.DataModel
import com.adambulette.network.dataModel.LoginModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginModel
}