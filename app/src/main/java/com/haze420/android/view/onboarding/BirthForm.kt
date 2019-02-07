package com.haze420.android.view.onboarding

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haze420.android.R
import java.util.*

/**
 * TODO: document your custom view class.
 */
class BirthForm : ConstraintLayout{
    var birthStrData = MutableLiveData<String>()
    set(value) {
        field = value
        this.findViewById<TextView>(R.id.txtBirth).setText(value.value)
    }
    private var _isValid = MutableLiveData<Boolean>()
    val isValid : LiveData<Boolean>
        get() = _isValid



    public fun updateBirthday(birthday: String){
        this.birthStrData.value = birthday
        val age18 = Calendar.getInstance().get(Calendar.YEAR) - 18
        var year = age18 + 100
        if (birthday.contains("2") || birthday.contains("1")){
            val arrayOfComp = birthday.split("/")
            year = arrayOfComp[2].toInt()
        }
        if (!birthStrData.value!!.contains("Day")){
            this.findViewById<TextView>(R.id.txtBirth)?.setTextColor(ContextCompat.getColor(context, R.color.white))
            if (year <= age18){
                _isValid.value = true
                this.findViewById<TextView>(R.id.txtError)?.visibility = View.GONE
                this.findViewById<ImageView>(R.id.imgError)?.visibility = View.GONE
            }else{
                _isValid.value = false
                this.findViewById<TextView>(R.id.txtError)?.text = context.getString(R.string.birth_error)
                this.findViewById<TextView>(R.id.txtError)?.visibility = View.VISIBLE
                this.findViewById<ImageView>(R.id.imgError)?.visibility = View.VISIBLE
            }
        }else{
            _isValid.value = false
            this.findViewById<TextView>(R.id.txtError)?.visibility = View.GONE
            this.findViewById<ImageView>(R.id.imgError)?.visibility = View.GONE
            this.findViewById<TextView>(R.id.txtBirth)?.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
        this.findViewById<TextView>(R.id.txtBirth)?.setText(birthday)
    }

    public


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
        _isValid.value = false
        birthStrData.value = context.getString(R.string.hint_birth)
        val rootView = context.getSystemService<LayoutInflater>()?.inflate(R.layout.form_birthday, this)

//            birthStrData.value = it.toString()
//            updateValid()
    }



}
