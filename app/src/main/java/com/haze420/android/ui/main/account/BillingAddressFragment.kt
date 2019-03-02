package com.haze420.android.ui.main.account

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.haze420.android.R
import com.haze420.android.model.BillingAddress
import com.haze420.android.model.DeliveryAddress
import com.haze420.android.model.UserProfile
import com.haze420.android.model.enums.ActionBarItemType
import com.haze420.android.ui.BaseFragment
import com.haze420.android.ui.MainActivity
import com.haze420.android.webservice.core.RetrofitFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_billing_address.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException


class BillingAddressFragment : BaseFragment() {

    companion object {
        fun newInstance() = BillingAddressFragment()
    }

    private lateinit var viewModel: BillingAddressViewModel
    private var userProfile: UserProfile? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_billing_address, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userProfile = mMainActivity.prefs.userProfile
        initUI()

        mMainActivity.sharedViewModel.getSelectedCountry().observe(viewLifecycleOwner, Observer {
            it?.let {
                mMainActivity.sharedViewModel.setSelectedCountry(null)
                txtCountry.setText(it)
            }
        })

        //Config action bar
        mMainActivity.actionBarView.config_BillingAddressFragment()

        // observe log out action from actionbar
        mMainActivity.sharedViewModel.getSelectedActionbarItem().observe(this, Observer { clickedItem ->
            if (clickedItem == ActionBarItemType.LOGOUT){
                mMainActivity.sharedViewModel.setSelectedActionbarItem(null)
                view?.let{Navigation.findNavController(it).navigate(R.id.action_billingAddressFragment_to_loginFragment)}
            }
        })

        // init sharedViewModel
        viewModel = ViewModelProviders.of(this).get(BillingAddressViewModel::class.java)

        txtCountry.setOnClickListener {
            mMainActivity.hideKeyboard()
            view?.let{
//                val act = BillingAddressFragmentDirections.actionBillingAddressFragmentToCountriesFragment()
//                Navigation.findNavController(it).navigate(act)}
                Navigation.findNavController(it).navigate(R.id.action_billingAddressFragment_to_countriesFragment)}
        }

        btnSaveAddress.setOnClickListener {
            if (validateFields()){
                updateAddress()
            }
        }
    }

    private fun updateAddress(){
        if (!mMainActivity.checkConnection()!!){
            return
        }
        mMainActivity.showLoading()
        val token = mMainActivity.prefs.token
        if (token == ""){
            mMainActivity.showUnauthError()
            return
        }
        val service = RetrofitFactory.makeHomeServiceService(token)
        GlobalScope.launch(Dispatchers.Main){
            val country = txtCountry.text.toString()
            val postcode = edtPostcode.text.toString()
            val street = edtStreet.text.toString()
            val city = edtCity.text.toString()
            val state = edtCounty.text.toString()
            val firstName = edtFirstName.text.toString()
            val lastName = edtLastName.text.toString()
            val phone = edtPhone.text.toString()

            val billingAddress = BillingAddress(firstName, lastName, street, "", city, postcode,
                country, state, phone)
            val request = service.updateBillingAddress(billingAddress)
            try {
                // Wait for response
                val response = request.await()

                //Hide loading
                mMainActivity.hideLoading()

                // Handle response
                if (response.success){
                    userProfile!!.billing_address = billingAddress
                    mMainActivity.prefs.userProfile = userProfile
                    findNavController().popBackStack()
                }else{
                    response.error?.let { it.message?.let { it1 -> mMainActivity.showError(it1) } }
                }

            }catch (e: HttpException){
                handleAPIError(e)

            }catch (e: Throwable){
                handleAPIError(e)
            }
        }
    }

    fun initUI() {
        userProfile?.let { profile ->
            profile.billing_address?.let {billingAddress ->
                billingAddress.country?.let {
                    txtCountry.setText(it)
                }
                billingAddress.postcode?.let {
                    edtPostcode.setText(it)
                }

                billingAddress.address_1?.let {
                    edtStreet.setText(it)
                }

                billingAddress.city?.let {
                    edtCity.setText(it)
                }

                billingAddress.state?.let {
                    edtCounty.setText(it)
                }

                billingAddress.first_name?.let {
                    edtFirstName.setText(it)
                }

                billingAddress.last_name?.let {
                    edtLastName.setText(it)
                }
                billingAddress.phone?.let {
                    edtPhone.setText(it)
                }
            }
        }
    }

    fun validateFields(): Boolean{
        if (txtCountry.text.toString().isNullOrEmpty()){
            mMainActivity.showError("Please select country.")
            return false
        }
        if (edtPostcode.text.toString().isNullOrEmpty()){
            mMainActivity.showError("Postcode cannot be empty.")
            return false
        }

        if (edtStreet.text.toString().isNullOrEmpty()){
            mMainActivity.showError("Street cannot be empty.")
            return false
        }

        if (edtCity.text.toString().isNullOrEmpty()){
            mMainActivity.showError("Town/City cannot be empty.")
            return false
        }

        if (edtCounty.text.toString().isNullOrEmpty()){
            mMainActivity.showError("County cannot be empty.")
            return false
        }

        if (edtFirstName.text.toString().isNullOrEmpty()){
            mMainActivity.showError("First name cannot be empty.")
            return false
        }

        if (edtLastName.text.toString().isNullOrEmpty()){
            mMainActivity.showError("Last name cannot be empty.")
            return false
        }

        if (edtPhone.text.toString().isNullOrEmpty()){
            mMainActivity.showError("Billing phone cannot be empty.")
            return false
        }

        return true
    }

}
