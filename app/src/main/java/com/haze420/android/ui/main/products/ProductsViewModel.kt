package com.haze420.android.ui.main.products

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
    private  var _activeCategory : CATEGORY
    init {
        _activeCategory = CATEGORY.Sativa
        _repository.trigerFetchList(false, CATEGORY.Sativa)
    }

    //Getter
    fun getProductsList() : MutableLiveData<List<Product>>{
        return _repository.getProductList()
    }
    fun getSelected(): MutableLiveData<Int>{
        return _selected
    }

    // Commands from View
    fun refresh(category: CATEGORY){
        _repository.trigerFetchList(false, category)
    }

    // Data binding
    fun isCategoryActive(index: Int): Boolean{
        when (index){
            0 -> return _activeCategory == CATEGORY.Sativa
            1 -> return _activeCategory == CATEGORY.Hybrid
            2 -> return _activeCategory == CATEGORY.Indica
            else -> {
                return _activeCategory == CATEGORY.ALL
            }
        }

    }
    fun onCategoryClicked(index: Int){
        when (index){
            0 -> {
                _activeCategory = CATEGORY.Sativa
                refresh(CATEGORY.Sativa)
            }
            1 -> {
                _activeCategory = CATEGORY.Hybrid
                refresh(CATEGORY.Hybrid)
            }
            2 -> {
                _activeCategory = CATEGORY.Indica
                refresh(CATEGORY.Indica)
            }
            else ->{
                _activeCategory = CATEGORY.ALL
                refresh(CATEGORY.ALL)
            }

        }

    }

    fun onProductClick(position: Int){
        _selected.value = position
    }

    fun getProductNameAt(position: Int): String{
        return getProductsList().value?.get(position)?.name!!
    }
    fun getAvgRateAt(position: Int): String{
        val avg_rate = getProductsList().value?.get(position)?.avg_rating
        if (avg_rate != null)
            return avg_rate
        return "0"
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
