package com.example.androidlesson23.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlesson23.source.api.Resource
import com.example.androidlesson23.source.api.repositories.FirebaseAuthRepository
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val firebaseAuthRepository: FirebaseAuthRepository) :
    ViewModel() {

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> get() = _isLogin

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun login(email: String, password: String) {
        _loading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val result = firebaseAuthRepository.loginUser(email, password)
            when (result) {
                is Resource.Success<AuthResult> -> {
                    _isLogin.postValue(true)
                }
                is Resource.Error<AuthResult> -> {
                    _error.postValue(result.errorMessage ?: "Unknown error")
                }
            }
            _loading.postValue(false)
        }
    }

    fun register(email: String, password: String) {
        _loading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val result = firebaseAuthRepository.registerUser(email, password)
            when (result) {
                is Resource.Success<AuthResult> -> {
                    _isLogin.postValue(true)
                }
                is Resource.Error<AuthResult> -> {
                    _error.postValue(result.errorMessage ?: "Unknown error")
                }
            }
            _loading.postValue(false)
        }
    }
}