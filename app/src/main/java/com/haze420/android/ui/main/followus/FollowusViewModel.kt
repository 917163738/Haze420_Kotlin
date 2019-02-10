package com.haze420.android.ui.main.followus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class FollowusViewModel : ViewModel() {
    val isSubscribed = MutableLiveData<Boolean>()
    // TODO: Implement the ViewModel
    init {
        isSubscribed.value = true
    }
}
