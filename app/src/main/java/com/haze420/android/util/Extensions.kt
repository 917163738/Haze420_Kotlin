package com.haze420.android.util

import android.util.Patterns
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.haze420.android.R

class Extensions {
}

fun Fragment.findMainNavController(): NavController =
    Navigation.findNavController(activity!!, R.id.nav_host_fragment)
fun String.isValidEmail() : Boolean{
    if (this != null && this != ""){
        return Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
    return false;
}
fun String.isValidPhone() : Boolean{
    if (this != null && this != ""){
        return Patterns.PHONE.matcher(this).matches()
    }
    return false;
}