package com.adambulette.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adambulette.network.apiRequest.LoginRequest
import com.adambulette.network.dataModel.LoginModel
import com.adambulette.repository.ApiRepository
import com.adambulette.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: ApiRepository) : ViewModel(){

    private val _loginLiveData: MutableLiveData<Resource<LoginModel>> = MutableLiveData()
    val loginData: LiveData<Resource<LoginModel>>
        get() = _loginLiveData

    fun userLogin(body: LoginRequest) = viewModelScope.launch {
        _loginLiveData.value = Resource.Loading
        _loginLiveData.value = repository.userLogin(body)

    }
}
