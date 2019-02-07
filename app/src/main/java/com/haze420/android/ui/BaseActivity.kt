package com.haze420.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.haze420.android.R
import com.haze420.android.view.main.DrawerLayout

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.haze420.android.model.MenuItemType


open class BaseActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        var fragment = supportFragmentManager.findFragmentById(R.id.productsFragment) as ProductsFragment
        mLifecycleRegistry = LifecycleRegistry(this) // Lifecycle register
        mLifecycleRegistry.markState(Lifecycle.State.CREATED) // Lifecycle register


    }

    public override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }
    fun hideKeyboard() {
        val inputManager = this
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val currentFocusedView = this.currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    interface ChangedMenuListener{
        fun onMenuChanged(type: MenuItemType)
    }
}

