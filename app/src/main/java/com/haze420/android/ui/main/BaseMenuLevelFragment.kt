package com.haze420.android.ui.main

import androidx.fragment.app.Fragment

import com.haze420.android.model.enums.SlideMenuType
import com.haze420.android.ui.BaseFragment
import com.haze420.android.ui.BaseMainActivity
import com.haze420.android.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

open class BaseMenuLevelFragment : BaseFragment(), BaseMainActivity.SlideMenuChangedListner{

    protected lateinit var menuItemTypeFor : SlideMenuType

    override fun onResume() {
        super.onResume()
        mMainActivity.addMenuChangedListner(this)
        mMainActivity.slideMenuLayout.setActiveMenu(menuItemTypeFor)
    }
    override fun onPause() {
        mMainActivity.removeMenuChangedListner(this)
        super.onPause()
    }
    // menu item changed listener
    override fun onMenuChanged(from: SlideMenuType, to: SlideMenuType) {
        handleTransaction(from, to)
    }

    open fun handleTransaction(from: SlideMenuType, goto: SlideMenuType){

    }
}
