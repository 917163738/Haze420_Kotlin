package com.haze420.android.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.haze420.android.R
import com.haze420.android.model.enums.ActionBarItemType

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
                ActionBarItemType.SEARCH_CLOSE -> {
                    hideKeyboard()
                }
                ActionBarItemType.SEARCH -> {
                    if (slideMenuLayout.isMenuOpened.value!!){
                        slideMenuLayout.closeMenu()
                    }
                }
                ActionBarItemType.LOGOUT -> {
                    hideKeyboard()
                    if (slideMenuLayout.isMenuOpened.value!!){
                        slideMenuLayout.closeMenu()
                    }
                    sharedViewModel.setSelectedActionbarItem(ActionBarItemType.LOGOUT)
                }
                ActionBarItemType.SHARE -> {
                    hideKeyboard()
                    if (slideMenuLayout.isMenuOpened.value!!){
                        slideMenuLayout.closeMenu()
                    }
                    val shareText = "Enjoy Haze420. https://itunes.apple.com/go here."
                    val shareIntent = ShareCompat.IntentBuilder.from(this)
                        .setText(shareText)
                        .setType("text/plain")
                        .createChooserIntent()
                        .apply {
                            // https://android-developers.googleblog.com/2012/02/share-with-intents.html
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                // If we're on Lollipop, we can open the intent as a document
                                addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                            } else {
                                // Else, we will use the old CLEAR_WHEN_TASK_RESET flag
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                            }
                        }
                    startActivity(shareIntent)
                }

                ActionBarItemType.BACK -> {
                    hideKeyboard()
                    val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
                    navController.popBackStack()
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


