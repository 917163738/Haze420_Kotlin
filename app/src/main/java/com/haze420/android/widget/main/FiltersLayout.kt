package com.haze420.android.widget.main

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.MutableLiveData
import com.haze420.android.R
import com.haze420.android.model.enums.ActionBarItemType
import com.haze420.android.model.enums.FilterType
import kotlinx.android.synthetic.main.actionbar.view.*
import kotlinx.android.synthetic.main.form_filter.*
import kotlinx.android.synthetic.main.form_filter.view.*
import kotlinx.android.synthetic.main.form_ratingbar.view.*
import java.util.logging.Handler

class FiltersLayout : ConstraintLayout{

    var isOpenedFilter = MutableLiveData<Boolean>()
    var selectedFilter = MutableLiveData<FilterType>()

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        val rootView = context.getSystemService<LayoutInflater>()?.inflate(R.layout.form_filter, this)
        isOpenedFilter.value = false
        selectedFilter.value = FilterType.MostPopular
        txtMostPopular.setOnClickListener {
            updateColors(FilterType.MostPopular)
            selectedFilter.value = FilterType.MostPopular
            closeFilter()
        }
        txtLowToHigh.setOnClickListener {
            updateColors(FilterType.LowToHigh)
            selectedFilter.value = FilterType.LowToHigh
            closeFilter()
        }
        txtHightToLow.setOnClickListener {
            updateColors(FilterType.HighToLow)
            selectedFilter.value = FilterType.HighToLow
            closeFilter()
        }
        txtTopRated.setOnClickListener {
            updateColors(FilterType.TopRated)
            selectedFilter.value = FilterType.TopRated
            closeFilter()
        }

    }

    fun openFilter(){
        updateColors(selectedFilter.value!!)
        layoutFilterContainer.alpha = 0.0f
        layoutFilterContainer.visibility = View.VISIBLE
        val anim1 = AnimationUtils.loadAnimation(context, R.anim.opening_filter)
        layoutFilterContainer.startAnimation(anim1)
        layoutFilterContainer.alpha = 1.0f
        isOpenedFilter.value = true
    }

    fun closeFilter(){

        val anim1 = AnimationUtils.loadAnimation(context, R.anim.closing_filter)
        layoutFilterContainer.startAnimation(anim1)
        android.os.Handler().postDelayed({layoutFilterContainer.visibility = View.GONE}, 150)
        isOpenedFilter.value = false
    }

    private fun updateColors(active: FilterType){
        when (active){
            FilterType.MostPopular -> {
                txtMostPopular.setTextColor(ContextCompat.getColor(this.context!!, R.color.red))
                txtLowToHigh.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
                txtHightToLow.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
                txtTopRated.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            }

            FilterType.LowToHigh -> {
                txtMostPopular.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
                txtLowToHigh.setTextColor(ContextCompat.getColor(this.context!!, R.color.red))
                txtHightToLow.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
                txtTopRated.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            }

            FilterType.HighToLow -> {
                txtMostPopular.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
                txtLowToHigh.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
                txtHightToLow.setTextColor(ContextCompat.getColor(this.context!!, R.color.red))
                txtTopRated.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            }

            FilterType.TopRated -> {
                txtMostPopular.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
                txtLowToHigh.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
                txtHightToLow.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
                txtTopRated.setTextColor(ContextCompat.getColor(this.context!!, R.color.red))
            }
        }

    }



}