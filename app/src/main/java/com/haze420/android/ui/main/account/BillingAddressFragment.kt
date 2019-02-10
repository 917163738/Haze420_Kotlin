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
import kotlinx.android.synthetic.main.fragment_billing_address.*

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

        val mainAct = activity as MainActivity
        //Config action bar
        mainAct.actionBarView.config_BillingAddressFragment()

        // observe log out action from actionbar
        mainAct.viewModel.getSelectedActionbarItem().observe(this, Observer { clickedItem ->
            if (clickedItem == ActionBarItemType.LOGOUT){
                view?.let{Navigation.findNavController(it).navigate(R.id.action_billingAddressFragment_to_loginFragment)}
            }
        })

        // init viewModel
        viewModel = ViewModelProviders.of(this).get(BillingAddressViewModel::class.java)

        txtCountry.setOnClickListener {
            view?.let{
                val act = BillingAddressFragmentDirections.actionBillingAddressFragmentToCountriesFragment()
                Navigation.findNavController(it).navigate(act)}
        }


        // TODO: Use the ViewModel
    }



}
