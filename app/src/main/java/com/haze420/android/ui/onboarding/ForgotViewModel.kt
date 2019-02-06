package com.haze420.android.ui.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class ForgotViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var emailAddress = MutableLiveData<String>()
    init {
        emailAddress.value = ""
    }
}
