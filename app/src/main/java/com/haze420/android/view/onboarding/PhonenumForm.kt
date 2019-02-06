package com.haze420.android.view.onboarding

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haze420.android.R
import com.haze420.android.ui.onboarding.login.isValidPhone

/**
 * TODO: document your custom view class.
 */
class PhonenumForm : ConstraintLayout {
    var phoneNum = MutableLiveData<String>()
    private var _isValid = MutableLiveData<Boolean>()
    val isValid : LiveData<Boolean>
        get() = _isValid
    private fun updateValid(){
        if (phoneNum.value!!.length > 0){
            this.findViewById<ImageView>(R.id.imgClear)?.visibility = View.VISIBLE
            _isValid.value = phoneNum.value!!.isValidPhone()
//            if (phoneNum.value!!.isValidPhone()){
//                _isValid.value = true
//                this.findViewById<TextView>(R.id.txtError)?.visibility = View.GONE
//                this.findViewById<ImageView>(R.id.imgError)?.visibility = View.GONE
//            }else{
//                _isValid.value = false
//                this.findViewById<TextView>(R.id.txtError)?.text = context.getString(R.string.str_invalid_email)
//                this.findViewById<TextView>(R.id.txtError)?.visibility = View.VISIBLE
//                this.findViewById<ImageView>(R.id.imgError)?.visibility = View.VISIBLE
//            }
        }else{
            _isValid.value = false
            this.findViewById<ImageView>(R.id.imgClear)?.visibility =  View.GONE
//            this.findViewById<TextView>(R.id.txtError)?.text = context.getString(R.string.str_empty)
//            this.findViewById<TextView>(R.id.txtError)?.visibility = View.VISIBLE
//            this.findViewById<ImageView>(R.id.imgError)?.visibility = View.VISIBLE
        }
    }


    constructor(context: Context) : super(context) {
        init(context,null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
        _isValid.value = false
        phoneNum.value = ""
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val rootView = context.getSystemService<LayoutInflater>()?.inflate(R.layout.form_phonenum, this)
        rootView?.findViewById<EditText>(R.id.edtPhone)?.addTextChangedListener {
            phoneNum.value = it.toString()
            updateValid()
        }
        rootView?.findViewById<ImageView>(R.id.imgClear)?.setOnClickListener {
            rootView.findViewById<EditText>(R.id.edtPhone).setText(R.string.blank)
        }
    }

    override fun onViewAdded(view: View?) {
        super.onViewAdded(view)
    }




}
