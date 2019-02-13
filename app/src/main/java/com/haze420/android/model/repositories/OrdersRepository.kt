package com.haze420.android.model.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.haze420.android.model.OrderItem
import com.haze420.android.model.enums.CATEGORY

class OrdersRepository {

    private var _ordersList = ArrayList<OrderItem>()
    private val _orderListLiveData = MutableLiveData<List<OrderItem>>()

    init {
        generateDummyOrders()
    }

    fun trigerFetchList(){
        _orderListLiveData.value = _ordersList
    }

    fun getOrderList(): LiveData<List<OrderItem>> {
        return _orderListLiveData
    }

    private fun generateDummyOrders(){
       _ordersList.clear()

        // add fake product for category selection

        for (i in 0..60){
            if (i < 10){
                _ordersList.add(OrderItem(i.toString(), false, "AK-27_" + i.toString(), CATEGORY.Sativa))
            }else if (i < 20){
                _ordersList.add(OrderItem(i.toString(), false, "AK-27_" + i.toString(), CATEGORY.Hybrid))
            }else if (i < 30){
                _ordersList.add(OrderItem(i.toString(), false, "AK-27_" + i.toString(), CATEGORY.Indica))
            }else if (i < 40){
                _ordersList.add(OrderItem(i.toString(), true, "AK-27_" + i.toString(), CATEGORY.Sativa))
            }else if (i < 50){
                _ordersList.add(OrderItem(i.toString(), true, "AK-27_" + i.toString(), CATEGORY.Hybrid))
            }else{
                _ordersList.add(OrderItem(i.toString(), true, "AK-27_" + i.toString(), CATEGORY.Indica))
            }

        }
    }
}