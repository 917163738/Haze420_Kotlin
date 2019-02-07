package com.haze420.android.ui.main.account

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.haze420.android.R

class BillingAddressFragment : Fragment() {

    companion object {
        fun newInstance() = BillingAddressFragment()
    }

    private lateinit var viewModel: BillingAddressViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_billing_address, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BillingAddressViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
