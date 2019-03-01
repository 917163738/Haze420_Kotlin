package com.haze420.android.ui

import android.util.Log
import androidx.fragment.app.Fragment


import com.haze420.android.model.Constants
import com.haze420.android.model.apimodel.ErrorResponse
import retrofit2.HttpException
import com.squareup.moshi.Moshi


//import com.haze420.android.model.LoginModel

open class BaseFragment : Fragment() {

    var mMainActivity: MainActivity? = null

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
            mMainActivity?.showNormalError(errMessage)
        }else if (e is Throwable){
            Log.e("BaseFragment", e.localizedMessage)
            mMainActivity?.showNormalError(Constants.ERR_SOMETHING_WRONG)
        }else{
            Log.e("BaseFragment", "Unknown")
            mMainActivity?.showNormalError(Constants.ERR_UNKNOWN)
        }
    }



}
