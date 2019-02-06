package com.haze420.android.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.haze420.android.R
import com.haze420.android.ui.onboarding.register.RegisterViewModel

/**
 * TODO: document your custom view class.
 */
class PasswordForm : ConstraintLayout {
    var password = MutableLiveData<String>()
        get() = field
        set(value) {
            field = value

        }
    var isValid = MutableLiveData<Boolean>()
        get() = field

    private var _showPassword : Boolean = false
    private fun updateValid(){
        if (password.value != null && password.value!!.length >= 8){
            isValid.value = true
            this.findViewById<ImageView>(R.id.imgEye)?.visibility = View.VISIBLE
            this.findViewById<TextView>(R.id.txtPwdError)?.visibility = View.GONE
            this.findViewById<ImageView>(R.id.imgPwdError)?.visibility = View.GONE
        }else{
            isValid.value = false
            this.findViewById<ImageView>(R.id.imgEye)?.visibility = View.GONE
            this.findViewById<TextView>(R.id.txtPwdError)?.text = context.getString(R.string.str_password_length)
            this.findViewById<TextView>(R.id.txtPwdError)?.visibility = View.VISIBLE
            this.findViewById<ImageView>(R.id.imgPwdError)?.visibility = View.VISIBLE
        }
    }


    constructor(context: Context) : super(context) {
        init(context,null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
        isValid.value = false
        password.value = ""
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val rootView = context.getSystemService<LayoutInflater>()?.inflate(R.layout.form_password, this)
        rootView?.findViewById<EditText>(R.id.edtPassword)?.addTextChangedListener {
            password.value = it.toString()
            updateValid()
        }
        rootView?.findViewById<ImageView>(R.id.imgEye)?.setOnClickListener {
            if (_showPassword){
                rootView?.findViewById<EditText>(R.id.edtPassword)?.transformationMethod = PasswordTransformationMethod()
                rootView?.findViewById<ImageView>(R.id.imgEye)?.setImageResource(R.drawable.eye)
            }else{
                rootView?.findViewById<EditText>(R.id.edtPassword)?.transformationMethod = null
                rootView?.findViewById<ImageView>(R.id.imgEye)?.setImageResource(R.drawable.eye_blocked)
            }
            _showPassword = !_showPassword
        }
    }

    override fun onViewAdded(view: View?) {
        super.onViewAdded(view)
    }




}
