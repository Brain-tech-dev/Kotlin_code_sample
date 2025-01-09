package com.adambulette.network.apiRequest

data class LoginRequest(
    val username: String, val password: String, val companyId: String, val vehicle_no: String
)