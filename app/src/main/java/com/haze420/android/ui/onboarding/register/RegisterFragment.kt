package com.haze420.android.ui.onboarding.register

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.haze420.android.R
import com.haze420.android.view.PasswordForm


class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
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
    }

}
