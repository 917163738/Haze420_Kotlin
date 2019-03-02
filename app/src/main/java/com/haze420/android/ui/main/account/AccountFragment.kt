package com.haze420.android.ui.main.account

import android.app.Dialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

import com.haze420.android.R
import com.haze420.android.model.UserProfile
import com.haze420.android.model.apimodel.ProfileRequest
import com.haze420.android.model.enums.ActionBarItemType
import com.haze420.android.model.enums.SlideMenuType
import com.haze420.android.ui.main.BaseMenuLevelFragment
import com.haze420.android.webservice.core.RetrofitFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.form_email.view.*
import kotlinx.android.synthetic.main.form_location.*
import kotlinx.android.synthetic.main.form_password.view.*
import kotlinx.android.synthetic.main.form_phonenum.view.*
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AccountFragment : BaseMenuLevelFragment(){
    companion object {
        fun newInstance() = AccountFragment()
    }

    private lateinit var viewModel: AccountViewModel
    private var userProfile: UserProfile? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuItemTypeFor = SlideMenuType.Account
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // load user profile
        userProfile = mMainActivity.prefs.userProfile
        initUI()

        //Config action bar
        mMainActivity.actionBarView.config_AccountFragment()

        // observe log out action from actionbar
        mMainActivity.sharedViewModel.getSelectedActionbarItem().observe(this, Observer { clickedItem ->
            if (clickedItem == ActionBarItemType.LOGOUT){
                mMainActivity.sharedViewModel.setSelectedActionbarItem(null)
                view?.let{Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_loginFragment)}
            }
        })

        //Init viewModel
        viewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        imgCopy.setOnClickListener {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_link_copied)
            dialog.show()
            Handler().postDelayed({dialog.dismiss()}, 3000)
        }
        location.setOnClickListener {
            view?.let{
                Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_deliveryAddressFragment)
            }
        }
        btnBillingAddress.setOnClickListener {
            view?.let{
                Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_billingAddressFragment)
            }
        }
        btnSave.setOnClickListener {
            if (validateFiels()){
                updateProfile()
            }
        }
    }

    private fun updateProfile(){
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
            val email = userProfile!!.user_email
            val firstName = edtFirstName.text.toString()
            val lastname = edtLastName.text.toString()
            val birthday = userProfile!!.birthday
            val phoneNum = phoneForm.edtPhone.text.toString()
            val profileRequest = ProfileRequest(firstName, lastname,email!!,phoneNum,birthday!!)
            val request = service.updateProfile(profileRequest)
            try {
                // Wait for response
                val response = request.await()

                //Hide loading
                mMainActivity.hideLoading()

                // Handle response
                if (response.success){
                    userProfile!!.updateWithProfileRequest(profileRequest)
                    mMainActivity.prefs.userProfile = userProfile
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

    fun validateFiels(): Boolean{
        if (edtFirstName.text.toString().isNullOrEmpty()){
            mMainActivity.showError("First name cannot be empty.")
            return false
        }

        if (edtLastName.text.toString().isNullOrEmpty()){
            mMainActivity.showError("Last name cannot be empty.")
            return false
        }

        if (phoneForm.edtPhone.text.toString().isNullOrEmpty()){
            mMainActivity.showError("Phone number cannot be empty.")
            return false
        }
        return true
    }

    fun initUI(){
        userProfile?.let {profile ->
            edtFirstName.setText(profile.first_name)
            edtLastName.setText(profile.last_name)

            val requestOptions = RequestOptions.circleCropTransform()
                .placeholder(R.drawable.avatar)

            Glide.with(imgAvatar)
                .load(profile.avatar)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgAvatar)

            emailForm.edtEmail.isEnabled = false
            emailForm.edtEmail.setText(profile.user_email)
            emailForm.findViewById<ImageView>(R.id.imgEmailClear).visibility = View.GONE

            pwdForm.edtPassword.isEnabled = false
            pwdForm.edtPassword.setText(mMainActivity.prefs.password)

            phoneForm.edtPhone.setText(profile.phone)

            val deliveryAddressString = profile.delivery_address?.convertString()
            txtLocation.setText(deliveryAddressString)
        }
    }

    override fun handleTransaction(from: SlideMenuType, goto: SlideMenuType){
        Log.d("Test", "handleTransaction(goto: SlideMenuType) ------------------")
        if (goto == menuItemTypeFor) return // Filter actions for me.
        if (from != menuItemTypeFor) return // Filter actions for me.
        view?.let {
            if (goto == SlideMenuType.Products){
                if (!Navigation.findNavController(it).popBackStack(R.id.productsFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_productsFragment)
                }
            }else if (goto == SlideMenuType.Basket){
                if (!Navigation.findNavController(it).popBackStack(R.id.basketFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_basketFragment)
                }
            }else if (goto == SlideMenuType.SALE){
                if (!Navigation.findNavController(it).popBackStack(R.id.saleFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_saleFragment)
                }
            }else if (goto == SlideMenuType.Orders){
                if (!Navigation.findNavController(it).popBackStack(R.id.ordersFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_ordersFragment)
                }
            }else if (goto == SlideMenuType.Info){
                if (!Navigation.findNavController(it).popBackStack(R.id.infoFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_infoFragment)
                }
            }else if (goto == SlideMenuType.Followus){
                if (!Navigation.findNavController(it).popBackStack(R.id.followusFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_followusFragment)
                }
            }
        }
    }

}
