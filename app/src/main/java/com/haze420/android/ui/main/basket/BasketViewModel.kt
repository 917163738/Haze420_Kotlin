package com.haze420.android.ui.main.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.haze420.android.model.BasketItem
import com.haze420.android.model.enums.CATEGORY
import com.haze420.android.model.repositories.BasketRepository
import com.haze420.android.model.repositories.ProductsRepository

class BasketViewModel : ViewModel() {
    private  val _repository = BasketRepository()
    val imageLink = "https://haze420.co.uk/wp-content/uploads/2018/12/girl-scout-cookies__primary_31a7.jpg"

    init {
        refresh()
    }

    fun getBasketItemList(): LiveData<List<BasketItem>>{
        return _repository.getBasketList()
    }

    private fun refresh(){
        // TODO: make it public when it's needed from fragment
        _repository.trigerFetchList()
    }

    // data binding fragment
    fun onClickPay(){

    }
    // for data binding for cell
    fun getImageURLAt(position: Int): String{
        val url = imageLink
        return url + ""
    }

    fun onClickPlusAt(position: Int){

    }

    fun onClickMinusAt(position: Int){

    }

    fun onClickDeleteAt(position: Int){

    }

    fun onClickShareAt(position: Int){

    }
}
