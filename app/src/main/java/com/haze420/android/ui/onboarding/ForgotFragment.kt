package com.haze420.android.ui.onboarding

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation

import com.haze420.android.R
import com.haze420.android.view.onboarding.EmailForm

class ForgotFragment : Fragment() {

    companion object {
        fun newInstance() = ForgotFragment()
    }

    private lateinit var viewModel: ForgotViewModel

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
        viewModel = ViewModelProviders.of(this).get(ForgotViewModel::class.java)
        view?.findViewById<Button>(R.id.btnSend)?.setOnClickListener({

        })
        view?.findViewById<Button>(R.id.btnRegister)?.setOnClickListener({
            if (this@ForgotFragment.arguments!!.getBoolean("isFromLogin")){
                view?.let { Navigation.findNavController(it).navigate(R.id.action_forgotFragment_to_registerFragment) }
            }else{
                view?.let { Navigation.findNavController(it).popBackStack()}
            }
        })

        val emailForm = view?.findViewById<EmailForm>(R.id.emailForm)
        emailForm?.emailAdd?.observe(this, Observer {
            viewModel.emailAddress.value = it
        })

        emailForm?.isValid?.observe(this, Observer {
            view?.findViewById<Button>(R.id.btnSend)?.isEnabled = it
        })
    }

}
