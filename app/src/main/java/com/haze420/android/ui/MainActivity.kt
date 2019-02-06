package com.haze420.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.haze420.android.R
import com.haze420.android.ui.main.ProductsFragment
import com.haze420.android.view.main.DrawerLayout

class MainActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var mLifecycleRegistry: LifecycleRegistry


    lateinit var drawerLayout : DrawerLayout
    lateinit var actionBarView : RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var fragment = supportFragmentManager.findFragmentById(R.id.productsFragment) as ProductsFragment
        mLifecycleRegistry = LifecycleRegistry(this) // Lifecycle register
        mLifecycleRegistry.markState(Lifecycle.State.CREATED) // Lifecycle register

        actionBarView = findViewById(R.id.actionBarView)

        drawerLayout = findViewById(R.id.drawerLayout)
        findViewById<Button>(R.id.btnMenu).setOnClickListener {
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
    }

    fun hideActionBarView(){
        actionBarView.visibility = View.GONE
    }
    fun showActionBarView(){
        actionBarView.visibility = View.VISIBLE
    }

    public override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }
}
