package com.haze420.android.view.main

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.widget.ImageViewCompat
import com.haze420.android.R
import com.haze420.android.model.MenuItemType

/**
 * TODO: document your custom view class.
 */
class MenuItemView : RelativeLayout {

    private var _typeString: String? = null // TODO: use a default from R.string...

    var listener: MenuItemViewListner? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.MenuItemView, defStyle, 0
        )
        _typeString = a.getString(
            R.styleable.MenuItemView_typestr)

        val rootView = context.getSystemService<LayoutInflater>()?.inflate(R.layout.item_slide_menu, this)

//        rootView?.findViewById<RelativeLayout>(R.id.itemview)?.setOnClickListener {
//            if (listener != null){
//                listener!!.onItemSelected(this)
//            }
//            setActive()
//        }

        if (_typeString == context.getString(R.string.s_product)){
            rootView?.setBackgroundColor(ContextCompat.getColor(context, R.color.product_menu_bg))
            rootView?.findViewById<TextView>(R.id.txtMenuTitle)?.setText(_typeString)
            rootView?.findViewById<ImageView>(R.id.imgMenuIcon)?.setImageResource(R.mipmap.product)
        }else if (_typeString == context.getString(R.string.s_basket)){
            rootView?.setBackgroundColor(ContextCompat.getColor(context, R.color.basket_menu_bg))
            rootView?.findViewById<TextView>(R.id.txtMenuTitle)?.setText(_typeString)
            rootView?.findViewById<ImageView>(R.id.imgMenuIcon)?.setImageResource(R.mipmap.shopping_basket_1)
        }else if (_typeString == context.getString(R.string.s_sale)){
            rootView?.setBackgroundColor(ContextCompat.getColor(context, R.color.sale_menu_bg))
            rootView?.findViewById<TextView>(R.id.txtMenuTitle)?.setText(_typeString)
            rootView?.findViewById<ImageView>(R.id.imgMenuIcon)?.setImageResource(R.mipmap.percent)
            rootView?.findViewById<View>(R.id.salemark)?.visibility = View.VISIBLE
        }else if (_typeString == context.getString(R.string.s_orders)){
            rootView?.setBackgroundColor(ContextCompat.getColor(context, R.color.orders_menu_bg))
            rootView?.findViewById<TextView>(R.id.txtMenuTitle)?.setText(_typeString)
            rootView?.findViewById<ImageView>(R.id.imgMenuIcon)?.setImageResource(R.mipmap.box_add)
        }else if (_typeString == context.getString(R.string.s_account)){
            rootView?.setBackgroundColor(ContextCompat.getColor(context, R.color.account_menu_bg))
            rootView?.findViewById<TextView>(R.id.txtMenuTitle)?.setText(_typeString)
            rootView?.findViewById<ImageView>(R.id.imgMenuIcon)?.setImageResource(R.mipmap.gas_mask)
        }else if (_typeString == context.getString(R.string.s_info)){
            rootView?.setBackgroundColor(ContextCompat.getColor(context, R.color.info_menu_bg))
            rootView?.findViewById<TextView>(R.id.txtMenuTitle)?.setText(_typeString)
            rootView?.findViewById<ImageView>(R.id.imgMenuIcon)?.setImageResource(R.mipmap.interface_information)
        }else if (_typeString == context.getString(R.string.s_follow)){
            rootView?.setBackgroundColor(ContextCompat.getColor(context, R.color.followus_menu_bg))
            rootView?.findViewById<TextView>(R.id.txtMenuTitle)?.setText(_typeString)
            rootView?.findViewById<ImageView>(R.id.imgMenuIcon)?.setImageResource(R.drawable.facebook1)
        }

    }

    fun setActive(isActive: Boolean = true){
        var colorSelected = ContextCompat.getColor(context, R.color.white)
        if (!isActive){
            colorSelected = ContextCompat.getColor(context, R.color.menu_inactive)
        }
        rootView?.findViewById<TextView>(R.id.txtMenuTitle)?.setTextColor(colorSelected)
        ImageViewCompat.setImageTintList(rootView?.findViewById<ImageView>(R.id.imgMenuIcon)!!, ColorStateList.valueOf(colorSelected))
    }

    interface MenuItemViewListner{
        fun onItemSelected(self: MenuItemView)
    }
}
