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
import kotlinx.android.synthetic.main.actionbar.view.*
import kotlinx.android.synthetic.main.form_ratingbar.view.*
import java.util.logging.Handler

class CustomRatingBar : ConstraintLayout{

    var selectedScore = MutableLiveData<Int>()

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
        val rootView = context.getSystemService<LayoutInflater>()?.inflate(R.layout.form_ratingbar, this)
        selectedScore.value = 5
        imgStar1.setOnClickListener {
            imgStar1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            imgStar2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_deactive))
            imgStar3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_deactive))
            imgStar4.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_deactive))
            imgStar5.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_deactive))
            selectedScore.value = 1
        }
        imgStar2.setOnClickListener {
            imgStar1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            imgStar2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            imgStar3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_deactive))
            imgStar4.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_deactive))
            imgStar5.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_deactive))
            selectedScore.value = 2
        }
        imgStar3.setOnClickListener {
            imgStar1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            imgStar2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            imgStar3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            imgStar4.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_deactive))
            imgStar5.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_deactive))
            selectedScore.value = 3
        }
        imgStar4.setOnClickListener {
            imgStar1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            imgStar2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            imgStar3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            imgStar4.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            imgStar5.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_deactive))
            selectedScore.value = 4
        }
        imgStar5.setOnClickListener {
            imgStar1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            imgStar2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            imgStar3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            imgStar4.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            imgStar5.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
            selectedScore.value = 5
        }
    }
}