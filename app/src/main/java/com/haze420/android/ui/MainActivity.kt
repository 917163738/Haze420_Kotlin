package com.haze420.android.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.haze420.android.R
import com.haze420.android.model.ActionBarItemType

import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseMainActivity(){

    lateinit var sharedViewModel: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var fragment = supportFragmentManager.findFragmentById(R.id.productsFragment) as ProductsFragment
        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        // Config action bar
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
                    hideKeyboardAndCloseMenu()
                    sharedViewModel.setSelectedActionbarItem(ActionBarItemType.LOGOUT)
                }
            }
        })


        //Config slide menu
        slideMenuLayout.listener = this
        slideMenuLayout.isMenuOpened.observe(this, Observer {
            if (it){
                imgMenu.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_close))
            }else{
                imgMenu.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_menu))
            }
        })
    }

    private fun hideKeyboardAndCloseMenu(){
        hideKeyboard()
        if (slideMenuLayout.isMenuOpened.value!!){
            slideMenuLayout.closeMenu()
        }
    }

    override fun onBackPressed() {
        if (slideMenuLayout.isMenuOpened.value!!){
            slideMenuLayout.closeMenu()
        }else{
//            val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//            if (navController.currentDestination?.id == R.id.registerFragment){
//                val homeIntent = Intent(Intent.ACTION_MAIN, null)
//                homeIntent.addCategory(Intent.CATEGORY_HOME)
//                homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
//                startActivity(homeIntent)
//            }else{
                super.onBackPressed()
//            }
        }
    }

    fun hideActionBarView(){
        actionBarView.visibility = View.GONE
    }
    fun showActionBarView(){
        actionBarView.visibility = View.VISIBLE
    }


}


