package com.haze420.android.ui.main.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class InfoViewModel : ViewModel() {
    val isSubscribed = MutableLiveData<Boolean>()
    // TODO: Implement the ViewModel
    init {
        isSubscribed.value = true
    }
}
