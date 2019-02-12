package com.haze420.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.*

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.haze420.android.model.enums.SlideMenuType
import com.haze420.android.widget.main.SlideMenuLayout


open class BaseMainActivity : AppCompatActivity(), LifecycleOwner, SlideMenuLayout.SlideMenuClickedListner  {

    private var slideMenuChangedListners : ArrayList<SlideMenuChangedListner> = ArrayList() // Fragment List to listen slideMenu

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

    // Slide Menu Layout Listener
    override fun onItemSelected(current: SlideMenuType, to: SlideMenuType) {
        if (slideMenuChangedListners.size > 0){
            slideMenuChangedListners.forEach { it.onMenuChanged(current, to) }
        }
    }

    // Interface: // Fragments will implement this interface to listen menuchanged
    interface SlideMenuChangedListner{
        fun onMenuChanged(from: SlideMenuType, to: SlideMenuType)
    }

    fun addMenuChangedListner(listner: SlideMenuChangedListner){
        slideMenuChangedListners.add(listner) // Register fragmennt to listen menu change
    }

    fun removeMenuChangedListner(listner: SlideMenuChangedListner){
        slideMenuChangedListners.remove(listner)
    }




}


