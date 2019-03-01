package com.haze420.android.ui.main.sale

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.haze420.android.model.Product
import com.haze420.android.model.ProductModel
import com.haze420.android.model.enums.CATEGORY
import com.haze420.android.model.enums.FilterType
import com.haze420.android.model.enums.THC
import com.haze420.android.model.repositories.ProductsRepository

class SaleViewModel : ViewModel() {

    private val _selected = MutableLiveData<ProductModel?>()
    private  val _repository = ProductsRepository()

    var selectedFilter = FilterType.MostPopular
        set(value) {
            field = value
            filterAndSort()
        }

    private val _productList = MutableLiveData<List<ProductModel>>()

    var productListAll = ArrayList<ProductModel>()
        set(value) {
            field = value
            filterAndSort()
        }

    init {

    }

    //Getter
    fun getProductsList() : MutableLiveData<List<ProductModel>>{
        return _productList
    }

    fun getSelectedProduct(): MutableLiveData<ProductModel?>{
        return _selected
    }

    fun clearSelected(){
        _selected.value = null
    }

    // Commands from View
    fun filterAndSort(){
        val filtered = productListAll.filter { productModel ->
           true
        }
        var sortedList = filtered
        if (selectedFilter != FilterType.MostPopular){
            sortedList = filtered.sortedWith(object: Comparator<ProductModel>{
                override fun compare(p1: ProductModel, p2: ProductModel): Int {
                    if (selectedFilter == FilterType.LowToHigh){
                        when {
                            p1.price.toDouble() > p2.price.toDouble() -> return 1
                            p1.price.toDouble() == p2.price.toDouble() -> return 0
                            else -> return -1
                        }
                    }else if (selectedFilter == FilterType.HighToLow){
                        when {
                            p1.price.toDouble() < p2.price.toDouble() -> return 1
                            p1.price.toDouble() == p2.price.toDouble() -> return 0
                            else -> return -1
                        }
                    }else{
                        when {
                            p1.average_rating.toDouble() < p2.average_rating.toDouble() -> return 1
                            p1.average_rating.toDouble() ==  p2.average_rating.toDouble() -> return 0
                            else -> return -1
                        }
                    }
                }
            })
        }
        _productList.value = sortedList
    }


    fun onProductClick(position: Int){
        _selected.value = getProductsList().value?.get(position)
    }

    fun getProductNameAt(position: Int): String{
        if (position >= getProductsList().value?.size!!){
            Log.e("Error", "============ " + position.toString())
            return "========================"
        }
        return getProductsList().value?.get(position)?.name!!
    }
    fun getAvgRateAt(position: Int): Float{
        val avg_rate = getProductsList().value?.get(position)?.average_rating
        if (avg_rate == "")
            return 0f
        return avg_rate!!.toFloat()
    }

    fun checkLevelImage1(position: Int): Boolean{
        val thcLevel = getProductsList().value?.get(position)?.THClevel
        return true
    }

    fun checkLevelImage2(position: Int): Boolean{
        val thcLevel = getProductsList().value?.get(position)?.THClevel
        return thcLevel != THC.LV1
    }

    fun checkLevelImage3(position: Int): Boolean{
        val thcLevel = getProductsList().value?.get(position)?.THClevel
        return thcLevel == THC.LV3
    }

    fun getPriceAt(position: Int): String{
        val price = getProductsList().value?.get(position)?.sale_price
        return "£" + price
    }

    fun getOldPriceAt(position: Int): String{
        val price = getProductsList().value?.get(position)?.price
        return "£" + price
    }

    fun getImageURLAt(position: Int): String{
        val url = getProductsList().value?.get(position)?.thumbnail_image
        return url + ""
    }
}
