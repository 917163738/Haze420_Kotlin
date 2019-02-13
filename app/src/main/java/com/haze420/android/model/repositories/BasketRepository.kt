package com.haze420.android.model.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haze420.android.model.BasketItem
import com.haze420.android.model.enums.CATEGORY
import com.haze420.android.model.Product

class BasketRepository {

    private var _basketItemList = ArrayList<BasketItem>()
    private val _basketItemListLiveData = MutableLiveData<List<BasketItem>>()

    init {
        generateDummyBasketItems()
    }

    fun trigerFetchList(){
        _basketItemListLiveData.value = _basketItemList
    }

    fun getBasketList(): LiveData<List<BasketItem>> {
        return _basketItemListLiveData
    }

    private fun generateDummyBasketItems(){
       _basketItemList.clear()

        // add fake product for category selection

        for (i in 0..60){
            if (i < 10){
                _basketItemList.add(BasketItem(i.toString(), false, "AK-27_" + i.toString(), CATEGORY.Sativa))
            }else if (i < 20){
                _basketItemList.add(BasketItem(i.toString(), false, "AK-27_" + i.toString(), CATEGORY.Hybrid))
            }else if (i < 30){
                _basketItemList.add(BasketItem(i.toString(), false, "AK-27_" + i.toString(), CATEGORY.Indica))
            }else if (i < 40){
                _basketItemList.add(BasketItem(i.toString(), true, "AK-27_" + i.toString(), CATEGORY.Sativa))
            }else if (i < 50){
                _basketItemList.add(BasketItem(i.toString(), true, "AK-27_" + i.toString(), CATEGORY.Hybrid))
            }else{
                _basketItemList.add(BasketItem(i.toString(), true, "AK-27_" + i.toString(), CATEGORY.Indica))
            }

        }
    }
}