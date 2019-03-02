package com.haze420.android.ui.main.products

import android.os.Build
import android.text.Html
import android.view.View
import androidx.databinding.*
import androidx.lifecycle.ViewModel;
import com.haze420.android.model.Product
import com.haze420.android.model.ProductModel

class ProductDetailViewModel internal constructor(
    private val _product: ProductModel
): ViewModel() {
    var productName = ObservableField<String>("")
    var shouldVisible: ObservableInt = ObservableInt(View.GONE)
    init {
        productName.set(_product.name)
        if (_product.sale_price == ""){
            shouldVisible.set(View.GONE)
        }else{
            shouldVisible.set(View.VISIBLE)
        }
    }

    fun getProduct() = _product



    fun getProductDescription(): String{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(_product.description, Html.FROM_HTML_MODE_COMPACT).toString()
        } else {
            return Html.fromHtml(_product.description).toString()
        }

    }

    fun getRealPrice(): String{
        return "£" + _product.price
    }

    fun getGrayPrice(): String{
        return "£" + _product.sale_price
    }

    // Data binding
    fun checkLevelImage(index: Int): Boolean{  // index will be 2 or 3
        return index == 2
    }

    fun getReviewCount(): String{
        val reviewCnt = _product.rating_count
        return reviewCnt.toString() + " reviews"
    }

    fun getRating(): Float{
        return _product.average_rating.toFloat()
    }


}
