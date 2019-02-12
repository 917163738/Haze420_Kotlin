package com.haze420.android.ui.main.products

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.haze420.android.model.CATEGORY
import com.haze420.android.model.Product
import com.haze420.android.model.THC
import com.haze420.android.model.repositories.ProductsRepository
import java.util.*

class ProductsViewModel : ViewModel() {
    private val _selected = MutableLiveData<Int>()
    private  val _repository = ProductsRepository()
    private  val _activeCategory = MutableLiveData<CATEGORY>()
    init {
        _activeCategory.value = CATEGORY.Sativa
        refresh()
    }

    //Getter
    fun getProductsList() : MutableLiveData<List<Product>>{
        return _repository.getProductList()
    }
    fun getActiveCategory() : MutableLiveData<CATEGORY>{
        return _activeCategory
    }
    fun getSelected(): MutableLiveData<Int>{
        return _selected
    }

    // Commands from View
    fun refresh(){
        _repository.trigerFetchList(false, _activeCategory.value!!)
    }

    // Data binding
    fun isCategoryActive(index: Int): Boolean{
        when (index){
            0 -> return _activeCategory.value!! == CATEGORY.Sativa
            1 -> return _activeCategory.value!! == CATEGORY.Hybrid
            2 -> return _activeCategory.value!! == CATEGORY.Indica
            else -> {
                return false
            }
        }
    }
    fun onCategoryClicked(index: Int){
        when (index){
            0 -> {
                _activeCategory.value = CATEGORY.Sativa
                refresh()
            }
            1 -> {
                _activeCategory.value = CATEGORY.Hybrid
                refresh()
            }
            2 -> {
                _activeCategory.value = CATEGORY.Indica
                refresh()
            }
            else ->{
                _activeCategory.value = CATEGORY.ALL
                refresh()
            }

        }

    }

    fun onProductClick(position: Int){
        _selected.value = position
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

    fun getImageURLAt(position: Int): String{
        val url = getProductsList().value?.get(position)?.thumb_URL
        return url + ""
    }

    //
}
