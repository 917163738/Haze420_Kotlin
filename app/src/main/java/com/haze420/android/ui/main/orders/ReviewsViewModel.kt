package com.haze420.android.ui.main.orders

import android.os.Build
import android.text.Html
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.haze420.android.model.Product
import com.haze420.android.model.ProductModel
import com.haze420.android.model.ReviewModel
import com.haze420.android.util.Utils

class ReviewsViewModel : ViewModel() {
    var productName = ObservableField<String>("")
    val reviewList = MutableLiveData<List<ReviewModel>>()
    init {

    }

    fun setProductName(name: String){
        productName.set(name)
    }

    fun getReviewAt(position: Int): String{
        val reviewContent = reviewList.value!!.get(position).review
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(reviewContent, Html.FROM_HTML_MODE_COMPACT).toString()
        } else {
            return Html.fromHtml(reviewContent).toString()
        }
    }
    fun getReviewerNameAt(position: Int): String{
        return reviewList.value!!.get(position).reviewer
    }

    fun getRateAt(position: Int): Float{
        val score = reviewList.value!!.get(position).rating
        return score.toFloat()
    }

    fun getCreatedAt(position: Int): String{
        val createdStr = reviewList.value!!.get(position).date_created
        val convertedStr = Utils.convertDateString(createdStr)
        return convertedStr
    }

    fun getAvatarURLAt(position: Int): String{
        val avatarMap = reviewList.value!!.get(position).reviewer_avatar_urls
        if (avatarMap.keys.contains("96")){
            return avatarMap.get("96")!!
        }else{
            return "https://secure.gravatar.com/avatar/de7ce1e196d811a19588d3d3fcf583d4?s=96&d=mm&r=g"
        }
    }
}
