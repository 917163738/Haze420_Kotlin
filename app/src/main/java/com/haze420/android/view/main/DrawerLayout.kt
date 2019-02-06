package com.haze420.android.view.main

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.getSystemService
import androidx.lifecycle.MutableLiveData
import com.haze420.android.R
import kotlinx.android.synthetic.main.layout_drawlayer.view.*

/**
 * TODO: document your custom view class.
 */
class DrawerLayout : ConstraintLayout, MenuItemView.MenuItemViewListner {

    private lateinit var selectedMenuView: MenuItemView
    override fun onItemSelected(menuItemView: MenuItemView) {
        selectedMenuView.setActive(false)
        selectedMenuView = menuItemView;
    }

    private lateinit var productsMenuView : MenuItemView
    private lateinit var basketMenuView : MenuItemView
    private lateinit var salesMenuView : MenuItemView
    private lateinit var ordersMenuView : MenuItemView
    private lateinit var accountMenuView : MenuItemView
    private lateinit var infoMenuView : MenuItemView
    private lateinit var followusMenuView : MenuItemView

    var isMenuOpened = MutableLiveData<Boolean>()
    constructor(context: Context) : super(context) {
        init(context,null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val rootView = context.getSystemService<LayoutInflater>()?.inflate(R.layout.layout_drawlayer, this)
        isMenuOpened.value = false
        productsMenuView = rootView?.findViewById(R.id.products)!!
        productsMenuView.setActive()

        basketMenuView = rootView?.findViewById(R.id.basket)!!
        salesMenuView = rootView?.findViewById(R.id.sales)!!
        ordersMenuView = rootView?.findViewById(R.id.orders)!!
        accountMenuView = rootView?.findViewById(R.id.account)!!
        infoMenuView = rootView?.findViewById(R.id.info)!!
        followusMenuView = rootView?.findViewById(R.id.followus)!!

        productsMenuView.setOnClickListener {
            closeMenu()
        }
        basketMenuView.setOnClickListener {

            closeMenu()
        }
        salesMenuView.setOnClickListener {
            closeMenu()
        }

        ordersMenuView.setOnClickListener {
            closeMenu()
        }

        accountMenuView.setOnClickListener {
            closeMenu()
        }

        infoMenuView.setOnClickListener {
            closeMenu()
        }

        followusMenuView.setOnClickListener {
            closeMenu()
        }


        rootView.setOnClickListener{
            closeMenu()
        }
    }

    public fun openMenu(){
        hideMenuViews()
        this.visibility = View.VISIBLE
        animateOpening()
        android.os.Handler().postDelayed({
            isMenuOpened.value = true
        }, 370)
    }

    private fun hideMenuViews(){
//        productsMenuView.visibility = View.INVISIBLE
//        basketMenuView.visibility = View.INVISIBLE
//        salesMenuView.visibility = View.INVISIBLE
//        ordersMenuView.visibility = View.INVISIBLE
//        accountMenuView.visibility = View.INVISIBLE
//        infoMenuView.visibility = View.INVISIBLE
//        followusMenuView.visibility = View.INVISIBLE

        productsMenuView.alpha = 0f
        basketMenuView.alpha = 0f
        salesMenuView.alpha = 0f
        ordersMenuView.alpha = 0f
        accountMenuView.alpha = 0f
        infoMenuView.alpha = 0f
        followusMenuView.alpha = 0f
    }

    private fun animateOpening(){

        val anim1 = AnimationUtils.loadAnimation(context, R.anim.opening)
        val anim2 = AnimationUtils.loadAnimation(context, R.anim.opening)
        val anim3 = AnimationUtils.loadAnimation(context, R.anim.opening)
        val anim4 = AnimationUtils.loadAnimation(context, R.anim.opening)
        val anim5 = AnimationUtils.loadAnimation(context, R.anim.opening)
        val anim6 = AnimationUtils.loadAnimation(context, R.anim.opening)
        val anim7 = AnimationUtils.loadAnimation(context, R.anim.opening)

        productsMenuView.startAnimation(anim1)
        productsMenuView.alpha = 1.0f
        android.os.Handler().postDelayed({
            basketMenuView.startAnimation(anim2)
            basketMenuView.alpha = 1.0f
        }, 50)

        android.os.Handler().postDelayed({
            salesMenuView.startAnimation(anim3)
            salesMenuView.alpha = 1.0f
        }, 100)

        android.os.Handler().postDelayed({
            ordersMenuView.startAnimation(anim4)
            ordersMenuView.alpha = 1.0f
        }, 150)


        android.os.Handler().postDelayed({
            accountMenuView.startAnimation(anim5)
            accountMenuView.alpha = 1.0f
        }, 200)

        android.os.Handler().postDelayed({
            infoMenuView.startAnimation(anim6)
            infoMenuView.alpha = 1.0f
        }, 250)

        android.os.Handler().postDelayed({
            followusMenuView.startAnimation(anim7)
            followusMenuView.alpha = 1.0f
        }, 300)
    }

    public fun closeMenu(){
        animateClosing()
        android.os.Handler().postDelayed({
            this.visibility = View.GONE
            isMenuOpened.value = false
        }, 370)

    }

    private fun animateClosing(){
        val anim1 = AnimationUtils.loadAnimation(context, R.anim.closing)
        val anim2 = AnimationUtils.loadAnimation(context, R.anim.closing)
        val anim3 = AnimationUtils.loadAnimation(context, R.anim.closing)
        val anim4 = AnimationUtils.loadAnimation(context, R.anim.closing)
        val anim5 = AnimationUtils.loadAnimation(context, R.anim.closing)
        val anim6 = AnimationUtils.loadAnimation(context, R.anim.closing)
        val anim7 = AnimationUtils.loadAnimation(context, R.anim.closing)

        productsMenuView.startAnimation(anim1)
        android.os.Handler().postDelayed({
            basketMenuView.startAnimation(anim2)
        }, 50)
        android.os.Handler().postDelayed({
            salesMenuView.startAnimation(anim3)
        }, 100)
        android.os.Handler().postDelayed({
            ordersMenuView.startAnimation(anim4)
        }, 150)
        android.os.Handler().postDelayed({
            accountMenuView.startAnimation(anim5)
        }, 200)
        android.os.Handler().postDelayed({
            infoMenuView.startAnimation(anim6)
        }, 250)
        android.os.Handler().postDelayed({
            followusMenuView.startAnimation(anim7)
        }, 300)


    }
}
