package com.haze420.android.ui.main

import androidx.fragment.app.Fragment

import com.haze420.android.model.SlideMenuType
import com.haze420.android.ui.BaseMainActivity
import com.haze420.android.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

open class BaseMenuLevelFragment : Fragment(), BaseMainActivity.SlideMenuChangedListner{

    protected lateinit var menuItemTypeFor : SlideMenuType

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).addMenuChangedListner(this)
        (activity as MainActivity).slideMenuLayout.setActiveMenu(menuItemTypeFor)
    }
    override fun onPause() {
        (activity as MainActivity).removeMenuChangedListner(this)
        super.onPause()
    }
    // menu item changed listener
    override fun onMenuChanged(from: SlideMenuType, to: SlideMenuType) {
        handleTransaction(from, to)
    }

    open fun handleTransaction(from: SlideMenuType, goto: SlideMenuType){

    }
}
