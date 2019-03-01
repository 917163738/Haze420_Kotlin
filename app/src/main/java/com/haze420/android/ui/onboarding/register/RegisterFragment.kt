package com.haze420.android.ui.onboarding.register

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.haze420.android.R
import com.haze420.android.model.apimodel.SignupRequest
import com.haze420.android.ui.BaseFragment
import com.haze420.android.ui.MainActivity
import com.haze420.android.webservice.core.RetrofitFactory
import com.haze420.android.widget.onboarding.BirthForm
import com.haze420.android.widget.onboarding.EmailForm
import com.haze420.android.widget.onboarding.PasswordForm
import com.haze420.android.widget.onboarding.PhonenumForm
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.*


class RegisterFragment : BaseFragment(), DatePickerDialog.OnDateSetListener {

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val birth = String.format("%02d/%02d/%04d", dayOfMonth, month, year)
        mViewModel.birthday.value = birth
    }

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var mViewModel: RegisterViewModel
    private var mDatePicker: DatePickerDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel
        btnForgot.setOnClickListener {
            view?.let {
                if (!Navigation.findNavController(it).popBackStack(R.id.forgotFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_forgotFragment)
                }
            }
        }
        btnLogin.setOnClickListener {
            view?.let {
                Navigation.findNavController(it).popBackStack(R.id.loginFragment, false)
            }
        }
        btnRegister.setOnClickListener {
            register()
        }
        birthForm.setOnClickListener({
            showDatePicker()
        })

        mViewModel.birthday.observe(this, Observer {
            mMainActivity.hideKeyboard()
            view?.findViewById<BirthForm>(R.id.birthForm)?.updateBirthday(it)
        })

        val pwdForm = view?.findViewById<PasswordForm>(R.id.pwdForm)
        pwdForm?.password?.observe(this, Observer {
            mViewModel.password.value = it
        })

        pwdForm?.isValid?.observe(this, Observer {
            mViewModel.updateValidPwd(it)
        })

        val emailForm = view?.findViewById<EmailForm>(R.id.emailForm)
        emailForm?.emailAdd?.observe(this, Observer {
            mViewModel.emailAddress.value = it
        })

        emailForm?.isValid?.observe(this, Observer {
            mViewModel.updateValidEmail(it)
        })

        val phoneForm = view?.findViewById<PhonenumForm>(R.id.phoneForm)
        phoneForm?.phoneNum?.observe(this, Observer {
            mViewModel.phoneNum.value = it
        })

        phoneForm?.isValid?.observe(this, Observer {
            mViewModel.updateValidPhone(it)
        })

        birthForm.isValid?.observe(this, Observer {
            mViewModel.updateValidBirth(it)
        })

        mViewModel.isAllValid.observe(this, Observer {
            view?.findViewById<Button>(R.id.btnRegister)?.isEnabled = it
        })
    }

    fun showDatePicker(){
        val now = Calendar.getInstance()
        if (mDatePicker == null) {
            mViewModel.birthday.value = getString(R.string.hint_birth)
            mDatePicker = DatePickerDialog(
                context!!, this, now.get(Calendar.YEAR) - 18, now.get(Calendar.MONTH), now.get(
                    Calendar.DAY_OF_MONTH
                )
            )
        }else {
            mDatePicker?.updateDate(now.get(Calendar.YEAR) - 18,
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH))
        }

        mDatePicker?.show()
    }

    private fun register(){
        if (!mMainActivity.checkConnection()!!){
            return
        }
        mMainActivity.showLoading()
        val service = RetrofitFactory.makeOnboardingService()
        GlobalScope.launch(Dispatchers.Main){
            val signUpRequest = SignupRequest(mViewModel.emailAddress.value!!, mViewModel.password.value!!,
                mViewModel.phoneNum.toString(), mViewModel.birthday.toString())
            val request = service.register(signUpRequest)
            try {
                val response = request.await()
                mMainActivity.hideLoading()
                if (response.success){
                    mMainActivity.showNormalAlert("Success",
                        "Successfully registered. Please log in with your credentials.")
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
