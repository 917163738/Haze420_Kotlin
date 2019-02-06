package com.haze420.android.ui.onboarding.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class RegisterViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var emailAddress = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var phoneNum = MutableLiveData<String>()
    var birthday = MutableLiveData<String>()

    private var _isValidEmail = MutableLiveData<Boolean>()
    private var _isValidPassword = MutableLiveData<Boolean>()
    private var _isValidPhonenum = MutableLiveData<Boolean>()
    private var _isValidBirth = MutableLiveData<Boolean>()

    val isAllValid = MutableLiveData<Boolean>()

    init {
        emailAddress.value = ""
        password.value = ""
        phoneNum.value = ""
        birthday.value = "Day/Month/Year"

        _isValidEmail.value = false
        _isValidPassword.value = false
        _isValidPhonenum.value = false
        _isValidBirth.value = false
    }

    public fun updateValidEmail(isValidEmail: Boolean){
        _isValidEmail.value = isValidEmail
        isAllValid.value = _isValidEmail.value!! && _isValidPassword.value!!
                && _isValidPhonenum.value!! && _isValidBirth.value!!

    }
    public fun updateValidPwd(isValid: Boolean){
        _isValidPassword.value = isValid
        isAllValid.value = _isValidEmail.value!! && _isValidPassword.value!!
                && _isValidPhonenum.value!! && _isValidBirth.value!!
    }

    public fun updateValidPhone(isValid: Boolean){
        _isValidPhonenum.value = isValid
        isAllValid.value = _isValidEmail.value!! && _isValidPassword.value!!
                && _isValidPhonenum.value!! && _isValidBirth.value!!
    }

    public fun updateValidBirth(isValid: Boolean){
        _isValidBirth.value = isValid
        isAllValid.value = _isValidEmail.value!! && _isValidPassword.value!!
                && _isValidPhonenum.value!! && _isValidBirth.value!!
    }
}
