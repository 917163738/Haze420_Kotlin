package com.haze420.android.ui.onboarding.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val emailAddress = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val showPassword = MutableLiveData<Boolean>()
    val isAllValid = MutableLiveData<Boolean>()
    init {
        emailAddress.value = ""
        password.value = ""
        showPassword.value = false
        isAllValid.value = false
    }

    fun updateValid(){
        if (emailAddress.value != null && password.value != null){
            if (emailAddress.value!!.isValidEmail() && password.value!!.length >= 8){
                isAllValid.value = true
            }
        }else{
            isAllValid.value = false
        }
    }

}
