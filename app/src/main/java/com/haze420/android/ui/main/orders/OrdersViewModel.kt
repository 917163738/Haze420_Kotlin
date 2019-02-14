package com.haze420.android.ui.main.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.haze420.android.model.BasketItem
import com.haze420.android.model.OrderItem
import com.haze420.android.model.enums.CATEGORY
import com.haze420.android.model.repositories.BasketRepository
import com.haze420.android.model.repositories.OrdersRepository

class OrdersViewModel : ViewModel() {
    private  val _repository = OrdersRepository()
    val imageLink = "https://www.haze420.co.uk/wp-content/uploads/2018/12/OG-Kush-500x500.png"

    private  val _selectedTrack = MutableLiveData<String>()

    init {
        refresh()
    }

    fun getOrderList(): LiveData<List<OrderItem>> {
        return _repository.getOrderList()
    }

    fun getSelectedTrackNum(): LiveData<String>{
        return _selectedTrack
    }

    private fun refresh(){
        // TODO: make it public when it's needed from fragment
        _repository.trigerFetchList()
    }

    // data binding fragment

    // for data binding for cell
    fun getImageURLAt(position: Int): String{
        val url = imageLink
        return url + ""
    }

    fun onClickPlusAt(position: Int){

    }

    fun onClickMinusAt(position: Int){

    }

    fun onClickTrackOrderAt(position: Int){
        _selectedTrack.value = "#" + "378-6432-225" + position.toString()
    }

    fun onClickWriteReviewAt(position: Int){

    }

    fun onClickBuyAgainAt(position: Int){

    }
}
