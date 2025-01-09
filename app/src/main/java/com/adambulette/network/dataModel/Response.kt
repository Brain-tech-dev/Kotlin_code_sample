package com.adambulette.network.dataModel

//Login Model
data class LoginModel(
    val code: Int,
    val driver_id: Int,
    val message: String,
    val name: String,
    val status: Boolean,
    val token: String?,
    val user_name: String,
    val vehicle_no: String
)

// This is a Common Model For Logout , Forgot Password Sent Email, Verify Otp, Change Password
data class DataModel(
    val message: String,
    val status: Boolean
)