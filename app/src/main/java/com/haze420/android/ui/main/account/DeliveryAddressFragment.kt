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

class DeliveryAddressFragment : Fragment() {

    companion object {
        fun newInstance() = DeliveryAddressFragment()
    }

    private lateinit var viewModel: DeliveryAddressViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delivery_address, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).actionBarView.config_DeliveryAddressFragment()
        viewModel = ViewModelProviders.of(this).get(DeliveryAddressViewModel::class.java)
        // TODO: Use the ViewModel
        view?.findViewById<View>(R.id.txtCountry)?.setOnClickListener {
            view?.let{Navigation.findNavController(it).navigate(R.id.action_deliveryAddressFragment_to_countriesFragment)}
        }
    }
}
