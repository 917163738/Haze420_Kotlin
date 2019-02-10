package com.haze420.android.ui.main.account

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.haze420.android.R
import com.haze420.android.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

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
        (activity as MainActivity).actionBarView.config_BillingAddressFragment()
        viewModel = ViewModelProviders.of(this).get(BillingAddressViewModel::class.java)

        view?.findViewById<View>(R.id.txtCountry)?.setOnClickListener {
            view?.let{
                val act = BillingAddressFragmentDirections.actionBillingAddressFragmentToCountriesFragment()

                Navigation.findNavController(it).navigate(act)}
        }


        // TODO: Use the ViewModel
    }



}
