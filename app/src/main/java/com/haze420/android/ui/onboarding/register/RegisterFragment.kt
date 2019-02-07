package com.haze420.android.ui.onboarding.register

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.haze420.android.R
import com.haze420.android.ui.MainActivity
import com.haze420.android.view.onboarding.BirthForm
import com.haze420.android.view.onboarding.EmailForm
import com.haze420.android.view.onboarding.PasswordForm
import com.haze420.android.view.onboarding.PhonenumForm
import java.util.*


class RegisterFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var mainAct : MainActivity
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val birth = String.format("%02d/%02d/%04d", dayOfMonth, month, year)
        viewModel.birthday.value = birth
    }

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel
    private var datePickerDialog: DatePickerDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        mainAct = activity as MainActivity
        // TODO: Use the ViewModel
        view?.findViewById<Button>(R.id.btnForgot)?.setOnClickListener {
            view?.let {
                if (!Navigation.findNavController(it).popBackStack(R.id.forgotFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_forgotFragment)
                }
            }
        }
        view?.findViewById<Button>(R.id.btnLogin)?.setOnClickListener {
            view?.let {
                Navigation.findNavController(it).popBackStack(R.id.loginFragment, false)
            }
        }
        view?.findViewById<BirthForm>(R.id.birthForm)?.setOnClickListener({
            showDatePicker()
        })

        viewModel.birthday.observe(this, Observer {
            mainAct.hideKeyboard()
            view?.findViewById<BirthForm>(R.id.birthForm)?.updateBirthday(it)
        })

        val pwdForm = view?.findViewById<PasswordForm>(R.id.pwdForm)
        pwdForm?.password?.observe(this, Observer {
            viewModel.password.value = it
        })

        pwdForm?.isValid?.observe(this, Observer {
            viewModel.updateValidPwd(it)
        })

        val emailForm = view?.findViewById<EmailForm>(R.id.emailForm)
        emailForm?.emailAdd?.observe(this, Observer {
            viewModel.emailAddress.value = it
        })

        emailForm?.isValid?.observe(this, Observer {
            viewModel.updateValidEmail(it)
        })

        val phoneForm = view?.findViewById<PhonenumForm>(R.id.phoneForm)
        phoneForm?.phoneNum?.observe(this, Observer {
            viewModel.phoneNum.value = it
        })

        phoneForm?.isValid?.observe(this, Observer {
            viewModel.updateValidPhone(it)
        })

        view?.findViewById<BirthForm>(R.id.birthForm)?.isValid?.observe(this, Observer {
            viewModel.updateValidBirth(it)
        })

        viewModel.isAllValid.observe(this, Observer {
            view?.findViewById<Button>(R.id.btnRegister)?.isEnabled = it
        })
    }

    fun showDatePicker(){
        val now = Calendar.getInstance()
        if (datePickerDialog == null) {
            viewModel.birthday.value = getString(R.string.hint_birth)
            datePickerDialog = DatePickerDialog(
                context!!, this, now.get(Calendar.YEAR) - 18, now.get(Calendar.MONTH), now.get(
                    Calendar.DAY_OF_MONTH
                )
            )
        }else {
            datePickerDialog?.updateDate(now.get(Calendar.YEAR) - 18,
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH))
        }

        datePickerDialog?.show()
    }

}
