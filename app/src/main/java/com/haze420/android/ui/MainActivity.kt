package com.haze420.android.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.haze420.android.R
import com.haze420.android.model.ActionBarItemType

import com.haze420.android.model.MenuItemType
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    lateinit var viewModel: SharedViewModel

    private var menuChangedListners : ArrayList<ChangedMenuListener> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var fragment = supportFragmentManager.findFragmentById(R.id.productsFragment) as ProductsFragment
        viewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        actionBarView.getSelectedItem().observe(this, Observer { clickedItem ->
            when (clickedItem){
                ActionBarItemType.MENU_CLOSE -> {
                    hideKeyboard()
                    if (slideMenuLayout.isMenuOpened.value!!){
                        slideMenuLayout.closeMenu()
                    }else{
                        slideMenuLayout.openMenu()
                    }
                }
                ActionBarItemType.LOGOUT -> {
                    hideKeyboard()
                    viewModel.setSelectedActionbarItem(clickedItem)  // Fragments will observer this value
                }
            }
        })

        slideMenuLayout.isMenuOpened.observe(this, Observer {
            if (it){
                imgMenu.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_close))
            }else{
                imgMenu.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_menu))
            }
        })

        slideMenuLayout.selectedMenuType.observe(this, Observer {
            val selectedType = it
            if (menuChangedListners.count() > 0){
                menuChangedListners.forEach {
                    it.onMenuChanged(selectedType)
                }
            }
        })
    }

    override fun onBackPressed() {
        if (slideMenuLayout.isMenuOpened.value!!){
            slideMenuLayout.closeMenu()
        }else{
            super.onBackPressed()
        }
    }

    fun addMenuChangedListner(listner: ChangedMenuListener){
        menuChangedListners.add(listner) // Register fragmennt to listen menu change
//        TODO() Should move to shared ViewModel
    }

    fun removeMenuChangedListner(listner: ChangedMenuListener){
        menuChangedListners.remove(listner)
        //        TODO() Should move to shared ViewModel
    }

    fun hideActionBarView(){
        actionBarView.visibility = View.GONE
    }
    fun showActionBarView(){
        actionBarView.visibility = View.VISIBLE
    }

    interface ChangedMenuListener{
        fun onMenuChanged(type: MenuItemType)
        //        TODO() Should move to shared ViewModel
    }
}


