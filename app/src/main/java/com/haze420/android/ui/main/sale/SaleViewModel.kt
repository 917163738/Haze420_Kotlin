package com.haze420.android.ui.main.sale

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.haze420.android.model.Product
import com.haze420.android.model.enums.CATEGORY
import com.haze420.android.model.enums.FilterType
import com.haze420.android.model.enums.THC
import com.haze420.android.model.repositories.ProductsRepository

class SaleViewModel : ViewModel() {

    private val _selected = MutableLiveData<Product?>()
    private  val _repository = ProductsRepository()
    var selectedFilter = ObservableField<String>("")

    init {
        selectedFilter.set(FilterType.MostPopular.toString())
        refresh()
    }

    //Getter
    fun getProductsList() : MutableLiveData<List<Product>>{
        return _repository.getProductList()
    }

    fun getSelected(): MutableLiveData<Product?>{
        return _selected
    }

    fun clearSelected(){
        _selected.value = null
    }

    // Commands from View
    fun refresh(){
        _repository.trigerFetchList(true)
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
//        val avg_rate = getProductsList().value?.get(position)?.avg_rating
//        if (avg_rate != null)
//            return avg_rate
//        return "0"
        return 4.0f
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
        val price = getProductsList().value?.get(position)?.price
        return "£" + price
    }

    fun getOldPriceAt(position: Int): String{
        val price = getProductsList().value?.get(position)?.price
        return "£" + "10.0"
    }

    fun getImageURLAt(position: Int): String{
        val url = getProductsList().value?.get(position)?.thumb_URL
        return url + ""
    }
}
