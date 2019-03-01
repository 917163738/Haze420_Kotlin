package com.haze420.android.ui.onboarding.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText


import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.haze420.android.BuildConfig
import com.haze420.android.R
import com.haze420.android.model.apimodel.LoginRequest
import com.haze420.android.ui.MainActivity
import com.haze420.android.webservice.core.RetrofitFactory
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import com.haze420.android.ui.BaseFragment


//import com.haze420.android.model.LoginModel

class LoginFragment : BaseFragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var mLoginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mMainActivity?.hideActionBarView()
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        btnForgot.setOnClickListener {
            view?.let { Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_ForgotFragment) }
        }
        btnRegister.setOnClickListener {
            mMainActivity?.hideKeyboard()
            view?.let { Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_RegisterFragment) }
        }

        btnLogin.setOnClickListener {
            mMainActivity?.hideKeyboard()
            login()
        }

        pwdForm?.password?.observe(this, Observer {
            mLoginViewModel.password.value = it
        })

        pwdForm?.isValid?.observe(this, Observer {
            mLoginViewModel?.updateValidPwd(it)
        })

        emailForm?.emailAdd?.observe(this, Observer {
            mLoginViewModel.emailAddress.value = it
        })

        emailForm?.isValid?.observe(this, Observer {
            mLoginViewModel.updateValidEmail(it)
        })

        mLoginViewModel.isAllValid.observe(this, Observer {
            btnLogin.isEnabled = it
        })
        if (BuildConfig.DEBUG){
            emailForm?.findViewById<EditText>(R.id.edtEmail)?.setText("testaccount3@gmail.com")
            pwdForm?.findViewById<EditText>(R.id.edtPassword)?.setText("00000000t")
        }
    }

    private fun login(){
        if (!mMainActivity?.checkConnection()!!){
            return
        }
        mMainActivity?.showLoading()
        val service = RetrofitFactory.makeOnboardingService()
        GlobalScope.launch(Dispatchers.Main){
            val loginRequestBody = LoginRequest(mLoginViewModel.emailAddress.value!!, mLoginViewModel.password.value!!)
            val request = service.login(loginRequestBody)
            try {
                // Wait for response
                val response = request.await()

                //Hide loading
                mMainActivity?.hideLoading()

                // Handle response
                if (response.success){
                    mMainActivity?.prefs?.token = response.data!!.jwt_token!!
                    gotoHome()
                }else{
                    response.error?.let { it.message?.let { it1 -> mMainActivity?.showError(it1) } }
                }

            }catch (e: HttpException){
                handleAPIError(e)

            }catch (e: Throwable){
                handleAPIError(e)
            }
        }
    }

    private fun gotoHome(){
        view?.let { Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_productsFragment) }
    }
}
