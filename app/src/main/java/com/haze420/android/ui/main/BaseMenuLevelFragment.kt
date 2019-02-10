package com.haze420.android.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.haze420.android.R
import com.haze420.android.model.MenuItemType
import com.haze420.android.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

open class BaseMenuLevelFragment : Fragment(), MainActivity.ChangedMenuListener{

    protected lateinit var menuItemTypeFor : MenuItemType

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).addMenuChangedListner(this)
        Handler().postDelayed({
            (activity as MainActivity).slideMenuLayout.selectedMenuType.value = menuItemTypeFor
        }, 300)
    }
    override fun onPause() {
        (activity as MainActivity).removeMenuChangedListner(this)
        super.onPause()
    }
    // menu item changed listener
    override fun onMenuChanged(type: MenuItemType) {
        handleTransaction(type)
    }

    open fun handleTransaction(goto: MenuItemType){

    }
}
