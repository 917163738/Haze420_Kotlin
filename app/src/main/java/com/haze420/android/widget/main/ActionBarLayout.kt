package com.haze420.android.widget.main

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.getSystemService
import androidx.lifecycle.MutableLiveData
import com.haze420.android.R
import com.haze420.android.model.enums.ActionBarItemType
import kotlinx.android.synthetic.main.actionbar.view.*

class ActionBarLayout : ConstraintLayout {

    private val _selectedItem = MutableLiveData<ActionBarItemType>()

    fun getSelectedItem(): MutableLiveData<ActionBarItemType>{
        return _selectedItem
    }
    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        val rootView = context.getSystemService<LayoutInflater>()?.inflate(R.layout.actionbar, this)
        imgMenu.setOnClickListener{
            _selectedItem.value = ActionBarItemType.MENU_CLOSE
        }
        imgBack.setOnClickListener{
            _selectedItem.value = ActionBarItemType.BACK
        }
        imgCoupon.setOnClickListener{
            _selectedItem.value = ActionBarItemType.COUPON
        }
        imgSearch.setOnClickListener{
//            TODO() Show search bar with animation
            _selectedItem.value = ActionBarItemType.SEARCH
        }
        imgSearchClose.setOnClickListener{
            //            TODO() Hinde search bar with animation
            _selectedItem.value = ActionBarItemType.SEARCH_CLOSE
        }
        imgShare.setOnClickListener{
            _selectedItem.value = ActionBarItemType.SHARE
        }

        imgLogout.setOnClickListener{
            _selectedItem.value = ActionBarItemType.LOGOUT
        }
    }

    fun config_ProductsFragment(){
        configure_Menu_Search()
    }
    fun config_BasketFragment(){
        configure_Menu_Coupon()
    }

    fun config_SALEFragment(){
        configure_Menu_Search()
    }

    fun config_OrdersFragment(){
        configure_Menu_Search()
    }

    fun config_AccountFragment(){
        configure_Menu_Logout()
    }

    fun config_InfoFragment(){
        configure_Menuonly()
    }

    fun config_FollowusFragment(){
        configure_Menuonly()
    }

    // Account fragments
    fun config_DeliveryAddressFragment(){
        configure_Back_Logout()
    }

    fun config_BillingAddressFragment(){
        configure_Back_Logout()
    }

    fun config_CountriesFragment(){
        configure_Back_Logout()
    }
    // --------------

    private fun configure_Menu_Search(){
        imgMenu.visibility = View.VISIBLE
        imgSearch.visibility = View.VISIBLE
        imgBack.visibility = View.INVISIBLE
        imgCoupon.visibility = View.INVISIBLE
        imgLogout.visibility = View.INVISIBLE
        imgShare.visibility = View.INVISIBLE
        layoutSearch.visibility = View.INVISIBLE
    }

    private fun configure_Menu_Coupon(){
        imgMenu.visibility = View.VISIBLE
        imgSearch.visibility = View.INVISIBLE
        imgBack.visibility = View.INVISIBLE
        imgCoupon.visibility = View.VISIBLE
        imgLogout.visibility = View.INVISIBLE
        imgShare.visibility = View.INVISIBLE
        layoutSearch.visibility = View.INVISIBLE
    }
    private fun configure_Menuonly(){
        imgMenu.visibility = View.VISIBLE
        imgSearch.visibility = View.INVISIBLE
        imgBack.visibility = View.INVISIBLE
        imgCoupon.visibility = View.INVISIBLE
        imgLogout.visibility = View.INVISIBLE
        imgShare.visibility = View.INVISIBLE
        layoutSearch.visibility = View.INVISIBLE
    }
    private fun configure_Menu_Logout(){
        imgMenu.visibility = View.VISIBLE
        imgSearch.visibility = View.INVISIBLE
        imgBack.visibility = View.INVISIBLE
        imgCoupon.visibility = View.INVISIBLE
        imgLogout.visibility = View.VISIBLE
        imgShare.visibility = View.INVISIBLE
        layoutSearch.visibility = View.INVISIBLE
    }

    private fun configure_Back_Logout(){
        imgMenu.visibility = View.INVISIBLE
        imgSearch.visibility = View.INVISIBLE
        imgBack.visibility = View.VISIBLE
        imgCoupon.visibility = View.INVISIBLE
        imgLogout.visibility = View.VISIBLE
        imgShare.visibility = View.INVISIBLE
        layoutSearch.visibility = View.INVISIBLE
    }

    private fun configure_Back_Share(){
        imgMenu.visibility = View.INVISIBLE
        imgSearch.visibility = View.INVISIBLE
        imgBack.visibility = View.VISIBLE
        imgCoupon.visibility = View.INVISIBLE
        imgLogout.visibility = View.INVISIBLE
        imgShare.visibility = View.VISIBLE
        layoutSearch.visibility = View.INVISIBLE
    }
}