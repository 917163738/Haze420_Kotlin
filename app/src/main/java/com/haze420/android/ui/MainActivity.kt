package com.haze420.android.ui

import android.app.Dialog
import android.content.*
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.haze420.android.R
import com.haze420.android.model.Constants
import com.haze420.android.model.enums.ActionBarItemType
import com.haze420.android.model.persist.Prefs

import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseMainActivity(){

    lateinit var sharedViewModel: SharedViewModel
    lateinit var prefs: Prefs

    private lateinit var loadingDlg: Dialog

    private var mIsConnectedInternet: Boolean = false

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val notConnected = intent.getBooleanExtra(
                ConnectivityManager
                .EXTRA_NO_CONNECTIVITY, false)
            mIsConnectedInternet = !notConnected
            checkConnection()
        }
    }


    //region Activity LifeCycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var fragment = supportFragmentManager.findFragmentById(R.id.productsFragment) as ProductsFragment
        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        // Config action bar
        actionBarView.getSelectedItem().observe(this, Observer { clickedItem ->
            handleActionBarAction(clickedItem)
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

        // Init prefrence manager
        prefs = Prefs(this)

        // Init loading dialog
        initLoadingDialog()

    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
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
    //endregion

    //region private functions

    private fun handleActionBarAction(actionType: ActionBarItemType){
        when (actionType){
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
    }

    private fun initLoadingDialog(){
        loadingDlg = Dialog(this@MainActivity)
        loadingDlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDlg.setCancelable(false)
        loadingDlg.setContentView(R.layout.dialog_loading)
        loadingDlg.window.setBackgroundDrawableResource(android.R.color.transparent)
        val imgView = loadingDlg.findViewById<ImageView>(R.id.gifLoading)
        Glide.with(this@MainActivity)
            .load(R.drawable.loadinggif)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imgView)

    }
    //endregion

    //region public functions
    fun hideActionBarView(){
        actionBarView.visibility = View.GONE
    }
    fun showActionBarView(){
        actionBarView.visibility = View.VISIBLE
    }

    fun checkConnection(): Boolean {
        if (mIsConnectedInternet){
            return true
        }else{
            showError(Constants.ERR_NETWORK)
            return false
        }
    }

    fun showLoading(){
        loadingDlg.show()
    }

    fun hideLoading(){
        loadingDlg.dismiss()
    }

    fun showError(error: String, title: String = "Error", callback: (() -> Unit)? = null){
        showAlert(title, error, callback)
    }

    fun showNormalAlert(title: String, message: String, callback: (() -> Unit)? = null){
        showAlert(title, message, callback)
    }

    private fun showAlert(title: String, message: String, callback: (() -> Unit)?){
        val dialog = Dialog(this@MainActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_normal)
        dialog.findViewById<TextView>(R.id.txtTitle)!!.setText(title)
        dialog.findViewById<TextView>(R.id.txtMessage)!!.setText(message)
        dialog.findViewById<Button>(R.id.btnOK)!!.setOnClickListener {
            dialog.dismiss()
            if (callback != null){
                callback()
            }
        }
        dialog.show()
    }



    //endregion




}


