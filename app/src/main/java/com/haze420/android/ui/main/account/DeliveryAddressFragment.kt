package com.haze420.android.ui.main.account

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation

import com.haze420.android.R
import com.haze420.android.model.ActionBarItemType
import com.haze420.android.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_delivery_address.*

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

        val mainActivity = activity as MainActivity
        //Config action bar
        mainActivity.actionBarView.config_DeliveryAddressFragment()

        // observe log out action from actionbar
        mainActivity.sharedViewModel.getSelectedActionbarItem().observe(this, Observer { clickedItem ->
            if (clickedItem == ActionBarItemType.LOGOUT){
                mainActivity.sharedViewModel.setSelectedActionbarItem(null)
                view?.let{Navigation.findNavController(it).navigate(R.id.action_deliveryAddressFragment_to_loginFragment)}
            }
        })

        //Init ViewModel

        viewModel = ViewModelProviders.of(this).get(DeliveryAddressViewModel::class.java)
        // TODO: Use the ViewModel
        txtCountry.setOnClickListener {
            view?.let{Navigation.findNavController(it).navigate(R.id.action_deliveryAddressFragment_to_countriesFragment)}
        }
    }
}
