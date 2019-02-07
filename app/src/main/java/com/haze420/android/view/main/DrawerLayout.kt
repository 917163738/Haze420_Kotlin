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
import com.haze420.android.model.MenuItemType
import kotlinx.android.synthetic.main.layout_drawlayer.view.*

/**
 * TODO: document your custom view class.
 */
class DrawerLayout : ConstraintLayout, MenuItemView.MenuItemViewListner{


    private lateinit var productsMenuView : MenuItemView
    private lateinit var basketMenuView : MenuItemView
    private lateinit var salesMenuView : MenuItemView
    private lateinit var ordersMenuView : MenuItemView
    private lateinit var accountMenuView : MenuItemView
    private lateinit var infoMenuView : MenuItemView
    private lateinit var followusMenuView : MenuItemView
    private lateinit var shadowView: View

    var isMenuOpened = MutableLiveData<Boolean>()
    var selectedMenuType = MutableLiveData<MenuItemType>()

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
        selectedMenuType.value = MenuItemType.Products
        productsMenuView = rootView?.findViewById(R.id.products)!!
        productsMenuView.listener = this

        basketMenuView = rootView.findViewById(R.id.basket)!!
        basketMenuView.listener = this

        salesMenuView = rootView.findViewById(R.id.sales)!!
        salesMenuView.listener = this

        ordersMenuView = rootView.findViewById(R.id.orders)!!
        ordersMenuView.listener = this

        accountMenuView = rootView.findViewById(R.id.account)!!
        accountMenuView.listener = this

        infoMenuView = rootView.findViewById(R.id.info)!!
        infoMenuView.listener = this

        followusMenuView = rootView.findViewById(R.id.followus)!!
        followusMenuView.listener = this

        shadowView = rootView.findViewById(R.id.viewShadow)

        rootView.setOnClickListener{
            closeMenu()
        }
    }

    public fun openMenu(){
        activeMenuItem()
        hideMenuViews()
        this.visibility = View.VISIBLE
        animateOpening()
        android.os.Handler().postDelayed({
            isMenuOpened.value = true
            shadowView.visibility = View.VISIBLE
        }, 230)
    }

    public fun closeMenu(){
        shadowView.visibility = View.INVISIBLE
        animateClosing()
        android.os.Handler().postDelayed({
            this.visibility = View.GONE
            isMenuOpened.value = false
        }, 230)

    }

    private fun hideMenuViews(){
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
        }, 30)

        android.os.Handler().postDelayed({
            salesMenuView.startAnimation(anim3)
            salesMenuView.alpha = 1.0f
        }, 60)

        android.os.Handler().postDelayed({
            ordersMenuView.startAnimation(anim4)
            ordersMenuView.alpha = 1.0f
        }, 90)


        android.os.Handler().postDelayed({
            accountMenuView.startAnimation(anim5)
            accountMenuView.alpha = 1.0f
        }, 120)

        android.os.Handler().postDelayed({
            infoMenuView.startAnimation(anim6)
            infoMenuView.alpha = 1.0f
        }, 150)

        android.os.Handler().postDelayed({
            followusMenuView.startAnimation(anim7)
            followusMenuView.alpha = 1.0f
        }, 180)
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
        }, 30)
        android.os.Handler().postDelayed({
            salesMenuView.startAnimation(anim3)
        }, 60)
        android.os.Handler().postDelayed({
            ordersMenuView.startAnimation(anim4)
        }, 90)
        android.os.Handler().postDelayed({
            accountMenuView.startAnimation(anim5)
        }, 120)
        android.os.Handler().postDelayed({
            infoMenuView.startAnimation(anim6)
        }, 150)
        android.os.Handler().postDelayed({
            followusMenuView.startAnimation(anim7)
        }, 180)
    }

    private fun getItemByType(type: MenuItemType) : MenuItemView{
        if (type == MenuItemType.Products)
            return productsMenuView
        if (type == MenuItemType.Basket)
            return basketMenuView
        if (type == MenuItemType.SALE)
            return salesMenuView
        if (type == MenuItemType.Orders)
            return ordersMenuView
        if (type == MenuItemType.Account)
            return accountMenuView
        if (type == MenuItemType.Info)
            return infoMenuView
        if (type == MenuItemType.Followus)
            return followusMenuView
        return productsMenuView
    }

    private fun activeMenuItem(){
        productsMenuView.setActive(selectedMenuType.value!! == MenuItemType.Products)
        basketMenuView.setActive(selectedMenuType.value!! == MenuItemType.Basket)
        salesMenuView.setActive(selectedMenuType.value!! == MenuItemType.SALE)
        ordersMenuView.setActive(selectedMenuType.value!! == MenuItemType.Orders)
        accountMenuView.setActive(selectedMenuType.value!! == MenuItemType.Account)
        infoMenuView.setActive(selectedMenuType.value!! == MenuItemType.Info)
        followusMenuView.setActive(selectedMenuType.value!! == MenuItemType.Followus)

    }
    // MenuItemView listner implement
    override fun onItemSelected(type: MenuItemType) {
        if (selectedMenuType.value!! != type){
            getItemByType(selectedMenuType.value!!).setActive(false)
            selectedMenuType.value = type
            getItemByType(selectedMenuType.value!!).setActive(true)
            // Post event menut item changed
        }
        closeMenu()
    }
}
