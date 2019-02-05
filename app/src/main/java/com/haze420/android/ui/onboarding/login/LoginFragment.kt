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
import com.haze420.android.R
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
        rootView.findViewById<ImageView>(R.id.imgEmailClear).setOnClickListener({
            rootView.findViewById<EditText>(R.id.edtEmail).setText(R.string.blank)
        })
        rootView.findViewById<Button>(R.id.btnLogin).isEnabled = false
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        view?.findViewById<EditText>(R.id.edtEmail)?.addTextChangedListener {
            loginViewModel.emailAddress.value = it.toString()
            loginViewModel.updateValid()
        }

        view?.findViewById<ImageView>(R.id.imgEye)?.setOnClickListener({
            loginViewModel.showPassword.value = !loginViewModel.showPassword.value!!
        })

        view?.findViewById<EditText>(R.id.edtPassword)?.addTextChangedListener {
            loginViewModel.password.value = it.toString()
            loginViewModel.updateValid()
        }

        loginViewModel.emailAddress.observe(this, Observer{
            if (it.length > 0){
                view?.findViewById<ImageView>(R.id.imgEmailClear)?.visibility = View.VISIBLE
                if (it.isValidEmail()){
                    view?.findViewById<TextView>(R.id.txtEmailError)?.visibility = View.GONE
                    view?.findViewById<ImageView>(R.id.imgEmailError)?.visibility = View.GONE
                }else{
                    view?.findViewById<TextView>(R.id.txtEmailError)?.text = getString(R.string.str_invalid_email)
                    view?.findViewById<TextView>(R.id.txtEmailError)?.visibility = View.VISIBLE
                    view?.findViewById<ImageView>(R.id.imgEmailError)?.visibility = View.VISIBLE
                }
            }else{
                view?.findViewById<ImageView>(R.id.imgEmailClear)?.visibility =  View.GONE
                view?.findViewById<TextView>(R.id.txtEmailError)?.text = getString(R.string.str_empty)
                view?.findViewById<TextView>(R.id.txtEmailError)?.visibility = View.VISIBLE
                view?.findViewById<ImageView>(R.id.imgEmailError)?.visibility = View.VISIBLE
            }
        })

        loginViewModel.password.observe(this, Observer {
            if (it.length > 0){
                view?.findViewById<ImageView>(R.id.imgEye)?.visibility = View.VISIBLE
                if (it.length > 8){
                    view?.findViewById<TextView>(R.id.txtPwdError)?.visibility = View.GONE
                    view?.findViewById<ImageView>(R.id.imgPwdError)?.visibility = View.GONE
                }else{
                    view?.findViewById<TextView>(R.id.txtPwdError)?.text = getString(R.string.str_password_length)
                    view?.findViewById<TextView>(R.id.txtPwdError)?.visibility = View.VISIBLE
                    view?.findViewById<ImageView>(R.id.imgPwdError)?.visibility = View.VISIBLE
                }
            }else{
                view?.findViewById<ImageView>(R.id.imgEye)?.visibility = View.GONE
                view?.findViewById<TextView>(R.id.txtPwdError)?.text = getString(R.string.str_password_length)
                view?.findViewById<TextView>(R.id.txtPwdError)?.visibility = View.VISIBLE
                view?.findViewById<ImageView>(R.id.imgPwdError)?.visibility = View.VISIBLE
            }
        })
        loginViewModel.showPassword.observe(this, Observer {
            if (!it){
                view?.findViewById<EditText>(R.id.edtPassword)?.transformationMethod = PasswordTransformationMethod()
                view?.findViewById<ImageView>(R.id.imgEye)?.setImageResource(R.drawable.eye)
            }else{
                view?.findViewById<EditText>(R.id.edtPassword)?.transformationMethod = null
                view?.findViewById<ImageView>(R.id.imgEye)?.setImageResource(R.drawable.eye_blocked)
            }
        })

        loginViewModel.isAllValid.observe(this, Observer {
            if (it){
                view?.findViewById<Button>(R.id.btnLogin)?.isEnabled = true
            }else{
                view?.findViewById<Button>(R.id.btnLogin)?.isEnabled = false
            }
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
