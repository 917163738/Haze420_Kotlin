package com.haze420.android.ui.onboarding.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener


import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.haze420.android.R
import com.haze420.android.view.EmailForm
import com.haze420.android.view.PasswordForm
import java.util.regex.Pattern

//import com.haze420.android.model.LoginModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        view?.findViewById<Button>(R.id.btnForgot)?.setOnClickListener {
            view?.let { Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_ForgotFragment) }

        }
        view?.findViewById<Button>(R.id.btnRegister)?.setOnClickListener {
            view?.let { Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_RegisterFragment) }

        }

        val pwdForm = view?.findViewById<PasswordForm>(R.id.pwdForm)
        pwdForm?.password?.observe(this, Observer {
            loginViewModel.password.value = it
        })

        pwdForm?.isValid?.observe(this, Observer {
            loginViewModel.updateValidPwd(it)
        })

        val emailForm = view?.findViewById<EmailForm>(R.id.emailForm)
        emailForm?.emailAdd?.observe(this, Observer {
            loginViewModel.emailAddress.value = it
        })

        emailForm?.isValid?.observe(this, Observer {
            loginViewModel.updateValidEmail(it)
        })

        loginViewModel.isAllValid.observe(this, Observer {
            view?.findViewById<Button>(R.id.btnLogin)?.isEnabled = it
        })

    }

}

fun String.isValidEmail() : Boolean{
    if (this != null && this != ""){
        val EMAIL_STRING =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        val p = Pattern.compile(EMAIL_STRING)
        return Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
    return false;
}
