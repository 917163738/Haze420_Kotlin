package com.haze420.android.ui.onboarding.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var emailAddress = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    private var _isValidEmail = MutableLiveData<Boolean>()
    private var _isValidPassword = MutableLiveData<Boolean>()
    val isAllValid = MutableLiveData<Boolean>()

    init {
        emailAddress.value = ""
        password.value = ""
        _isValidEmail.value = false
        _isValidPassword.value = false
    }

    public fun updateValidEmail(isValidEmail: Boolean){
        _isValidEmail.value = isValidEmail
        isAllValid.value = isValidEmail && _isValidPassword.value!!

    }
    public fun updateValidPwd(isValid: Boolean){
        _isValidPassword.value = isValid
        isAllValid.value = _isValidEmail.value!! && isValid
    }
}
