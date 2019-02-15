package com.haze420.android.ui.main.orders

import androidx.lifecycle.ViewModel;
import com.haze420.android.model.Product

class ReviewsViewModel : ViewModel() {
    private lateinit var _productName : String

    init {

    }

    fun setProductName(name: String){
        _productName = name
    }

    fun getProductName(): String{
        return _productName
    }

    fun getReviewAt(position: Int): String{
        if (position % 2 == 0){
            return "Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello"
        }
        return "Good product nice \nwill buy again"
    }
    fun getReviewerNameAt(position: Int): String{
        return "Reviwer Reviewer" + position.toString()
    }

    fun getRateAt(position: Int): Float{
        val score = (position % 5) + 1
        return score.toFloat()
    }

    fun getCreatedAt(position: Int): String{
        return "12/23/2018"
    }
}
