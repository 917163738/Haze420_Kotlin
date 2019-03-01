package com.haze420.android.ui.onboarding

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation

import com.haze420.android.R
import com.haze420.android.model.apimodel.ForgotRequest
import com.haze420.android.ui.BaseFragment
import com.haze420.android.ui.MainActivity
import com.haze420.android.webservice.core.RetrofitFactory
import com.haze420.android.widget.onboarding.EmailForm
import kotlinx.android.synthetic.main.fragment_forgot.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ForgotFragment : BaseFragment() {

    companion object {
        fun newInstance() = ForgotFragment()
    }

    private lateinit var mViewModel: ForgotViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =  inflater.inflate(R.layout.fragment_forgot, container, false)
        rootView.findViewById<ImageView>(R.id.imgEmailClear).setOnClickListener({
            rootView.findViewById<EditText>(R.id.edtEmail).setText(R.string.blank)
        })
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(ForgotViewModel::class.java)
        btnSend.setOnClickListener({
            mMainActivity.hideKeyboard()
            forgotPwdAPI()
        })
        btnRegister.setOnClickListener({
            if (this@ForgotFragment.arguments!!.getBoolean("isFromLogin")){
                view?.let { Navigation.findNavController(it).navigate(R.id.action_forgotFragment_to_registerFragment) }
            }else{
                view?.let { Navigation.findNavController(it).popBackStack()}
            }
        })

        val emailForm = view?.findViewById<EmailForm>(R.id.emailForm)
        emailForm?.emailAdd?.observe(this, Observer {
            mViewModel.emailAddress.value = it
        })

        emailForm?.isValid?.observe(this, Observer {
            view?.findViewById<Button>(R.id.btnSend)?.isEnabled = it
        })
    }

    private fun forgotPwdAPI(){
        if (!mMainActivity.checkConnection()!!){
            return
        }
        mMainActivity.showLoading()
        val service = RetrofitFactory.makeOnboardingService()
        GlobalScope.launch(Dispatchers.Main){
            val forgotRequest = ForgotRequest(mViewModel.emailAddress.value!!)
            val request = service.forgotPwd(forgotRequest)
            try {
                val response = request.await()
                mMainActivity.hideLoading()
                if (response.success){
                    mMainActivity.showNormalAlert("Success",
                        "Reset Password link had been send to your email.")
                }else{
                    response.error?.let { it.message?.let { it1 -> mMainActivity.showError(it1) } }
                }

            }catch (e: HttpException){
                handleAPIError(e)

            }catch (e: Throwable){
                handleAPIError(e)
            }
        }
    }

}
