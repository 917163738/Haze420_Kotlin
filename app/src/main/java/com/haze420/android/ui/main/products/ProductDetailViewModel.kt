package com.haze420.android.ui.main.products

import android.view.View
import androidx.databinding.*
import androidx.lifecycle.ViewModel;
import com.haze420.android.model.Product

class ProductDetailViewModel internal constructor(
    private val _product: Product
): ViewModel() {
    var productName = ObservableField<String>("")
    var shouldVisible: ObservableInt = ObservableInt(View.VISIBLE)
    init {
        productName.set(_product.name + " reviews")
    }

    fun getProduct() = _product

    // Data binding
    fun checkLevelImage(index: Int): Boolean{  // index will be 2 or 3
        return index == 2
    }

    fun getReviewCount(): String{
        val reviewCnt = _product.review_count
        return reviewCnt.toString() + " reviews"
    }


}
