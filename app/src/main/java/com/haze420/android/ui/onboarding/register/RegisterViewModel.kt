package com.haze420.android.ui.onboarding.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class RegisterViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val password = MutableLiveData<String>()
    init {
        password.value = ""
    }
}
