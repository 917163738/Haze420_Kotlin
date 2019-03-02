package com.haze420.android.ui.main.account

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.haze420.android.R
import com.haze420.android.model.DeliveryAddress
import com.haze420.android.model.UserProfile
import com.haze420.android.model.enums.ActionBarItemType
import com.haze420.android.ui.BaseFragment
import com.haze420.android.webservice.core.RetrofitFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_delivery_address.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DeliveryAddressFragment : BaseFragment() {

    companion object {
        fun newInstance() = DeliveryAddressFragment()
    }

    private lateinit var viewModel: DeliveryAddressViewModel

    private var userProfile: UserProfile? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delivery_address, container, false)
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
        mMainActivity.actionBarView.config_DeliveryAddressFragment()

        // observe log out action from actionbar
        mMainActivity.sharedViewModel.getSelectedActionbarItem().observe(this, Observer { clickedItem ->
            if (clickedItem == ActionBarItemType.LOGOUT){
                mMainActivity.sharedViewModel.setSelectedActionbarItem(null)
                view?.let{Navigation.findNavController(it).navigate(R.id.action_deliveryAddressFragment_to_loginFragment)}
            }
        })

        //Init ViewModel

        viewModel = ViewModelProviders.of(this).get(DeliveryAddressViewModel::class.java)
        // TODO: Use the ViewModel
        txtCountry.setOnClickListener {
            mMainActivity.hideKeyboard()
            view?.let{Navigation.findNavController(it).navigate(R.id.action_deliveryAddressFragment_to_countriesFragment)}
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
            var additionalInst = edtComments.text.toString()
            if (additionalInst.isNullOrEmpty()){
                additionalInst = ""
            }

            val deliveryAddressRequest = DeliveryAddress(street, "", city, postcode,
                country, state, additionalInst)
            val request = service.updateDeliveryAddress(deliveryAddressRequest)
            try {
                // Wait for response
                val response = request.await()

                //Hide loading
                mMainActivity.hideLoading()

                // Handle response
                if (response.success){
                    userProfile!!.delivery_address = deliveryAddressRequest
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
            profile.delivery_address?.let { deliveryAddress ->
                deliveryAddress.country?.let {
                    txtCountry.setText(it)
                }
                deliveryAddress.postcode?.let {
                    edtPostcode.setText(it)
                }

                deliveryAddress.address_1?.let {
                    edtStreet.setText(it)
                }

                deliveryAddress.city?.let {
                    edtCity.setText(it)
                }

                deliveryAddress.state?.let {
                    edtCounty.setText(it)
                }

                deliveryAddress.additional_instruction?.let {
                    edtComments.setText(it)
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

        return true
    }
}
