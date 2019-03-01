package com.haze420.android.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment


import com.haze420.android.model.Constants
import com.haze420.android.model.apimodel.ErrorResponse
import retrofit2.HttpException
import com.squareup.moshi.Moshi


//import com.haze420.android.model.LoginModel

open class BaseFragment : Fragment() {

    var mMainActivity: MainActivity? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mMainActivity = activity as MainActivity
    }

    fun handleAPIError(e: Any){
        mMainActivity?.hideLoading()
        if (e is HttpException){
            var errMessage = e.message()
            val errBody = e.response().errorBody()?.string()
            if (errBody != null){
                val moshi = Moshi.Builder().build()
                val jsonAdapter = moshi.adapter<ErrorResponse>(ErrorResponse::class.java!!)
                val loginRes = jsonAdapter.fromJson(errBody)
                if (loginRes?.error?.message != null){
                    errMessage = loginRes?.error?.message!!
                }
            }
            mMainActivity?.showError(errMessage)
        }else if (e is Throwable){
            Log.e("BaseFragment", e.localizedMessage)
            mMainActivity?.showError(Constants.ERR_SOMETHING_WRONG)
        }else{
            Log.e("BaseFragment", "Unknown")
            mMainActivity?.showError(Constants.ERR_UNKNOWN)
        }
    }



}
