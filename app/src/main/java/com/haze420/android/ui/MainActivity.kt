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


class MainActivity : BaseActivity() {

    private var menuChangedListners : ArrayList<ChangedMenuListener> = ArrayList()

    lateinit var drawerLayout : DrawerLayout
    lateinit var actionBarView : RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var fragment = supportFragmentManager.findFragmentById(R.id.productsFragment) as ProductsFragment


        actionBarView = findViewById(R.id.actionBarView)

        drawerLayout = findViewById(R.id.drawerLayout)
        findViewById<Button>(R.id.btnMenu).setOnClickListener {
            hideKeyboard()
            if (drawerLayout.isMenuOpened.value!!){
                drawerLayout.closeMenu()
            }else{
                drawerLayout.openMenu()
            }
        }

        drawerLayout.isMenuOpened.observe(this, Observer {
            if (it){
                findViewById<Button>(R.id.btnMenu).background = ContextCompat.getDrawable(applicationContext, R.drawable.ic_close)
            }else{
                findViewById<Button>(R.id.btnMenu).background = ContextCompat.getDrawable(applicationContext, R.drawable.ic_menu)
            }
        })

        drawerLayout.selectedMenuType.observe(this, Observer {
            val selectedType = it
            if (menuChangedListners.count() > 0){
                menuChangedListners.forEach {
                    it.onMenuChanged(selectedType)
                }
            }
        })
    }

    override fun onBackPressed() {
        if (drawerLayout.isMenuOpened.value!!){
            drawerLayout.closeMenu()
        }else{
            super.onBackPressed()
        }
    }

    fun addMenuChangedListner(listner: ChangedMenuListener){
        menuChangedListners.add(listner)
    }

    fun removeMenuChangedListner(listner: ChangedMenuListener){
        menuChangedListners.remove(listner)
    }

    fun hideActionBarView(){
        actionBarView.visibility = View.GONE
    }
    fun showActionBarView(){
        actionBarView.visibility = View.VISIBLE
    }

    interface ChangedMenuListener{
        fun onMenuChanged(type: MenuItemType)
    }
}


