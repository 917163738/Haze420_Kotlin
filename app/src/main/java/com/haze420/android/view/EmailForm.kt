package com.haze420.android.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import com.haze420.android.R
import com.haze420.android.ui.onboarding.login.isValidEmail

/**
 * TODO: document your custom view class.
 */
class EmailForm : ConstraintLayout {
    var emailAdd = MutableLiveData<String>()
    var isValid = MutableLiveData<Boolean>()
    private fun updateValid(){
        if (emailAdd.value!!.length > 0){
            this.findViewById<ImageView>(R.id.imgEmailClear)?.visibility = View.VISIBLE
            if (emailAdd.value!!.isValidEmail()){
                isValid.value = true
                this.findViewById<TextView>(R.id.txtEmailError)?.visibility = View.GONE
                this.findViewById<ImageView>(R.id.imgEmailError)?.visibility = View.GONE
            }else{
                isValid.value = false
                this.findViewById<TextView>(R.id.txtEmailError)?.text = context.getString(R.string.str_invalid_email)
                this.findViewById<TextView>(R.id.txtEmailError)?.visibility = View.VISIBLE
                this.findViewById<ImageView>(R.id.imgEmailError)?.visibility = View.VISIBLE
            }
        }else{
            isValid.value = false
            this.findViewById<ImageView>(R.id.imgEmailClear)?.visibility =  View.GONE
            this.findViewById<TextView>(R.id.txtEmailError)?.text = context.getString(R.string.str_empty)
            this.findViewById<TextView>(R.id.txtEmailError)?.visibility = View.VISIBLE
            this.findViewById<ImageView>(R.id.imgEmailError)?.visibility = View.VISIBLE
        }
    }


    constructor(context: Context) : super(context) {
        init(context,null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
        isValid.value = false
        emailAdd.value = ""
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val rootView = context.getSystemService<LayoutInflater>()?.inflate(R.layout.form_email, this)
        rootView?.findViewById<EditText>(R.id.edtEmail)?.addTextChangedListener {
            emailAdd.value = it.toString()
            updateValid()
        }
        rootView?.findViewById<ImageView>(R.id.imgEmailClear)?.setOnClickListener {
            rootView.findViewById<EditText>(R.id.edtEmail).setText(R.string.blank)
        }
    }

    override fun onViewAdded(view: View?) {
        super.onViewAdded(view)
    }




}
