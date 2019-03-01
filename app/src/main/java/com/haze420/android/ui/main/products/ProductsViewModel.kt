package com.haze420.android.ui.main.products

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.haze420.android.model.enums.CATEGORY
import com.haze420.android.model.ProductModel
import com.haze420.android.model.enums.FilterType
import com.haze420.android.model.enums.THC
import com.haze420.android.model.repositories.ProductsRepository

class ProductsViewModel : ViewModel() {
    private val _selected = MutableLiveData<ProductModel?>()
    private  val _activeCategory = MutableLiveData<CATEGORY>()
    private  val _productList = MutableLiveData<List<ProductModel>>()

    private  val _repository = ProductsRepository()
    var productListAll = ArrayList<ProductModel>()
    set(value) {
        field = value
        filterCategoryAndSort()
    }

    var selectedFilter = FilterType.MostPopular
        set(value) {
            field = value
            filterCategoryAndSort()
        }

    init {
        _activeCategory.value = CATEGORY.ALL
    }

    fun getProductsList() : MutableLiveData<List<ProductModel>>{
        return _productList
    }

    fun getActiveCategory() : MutableLiveData<CATEGORY>{
        return _activeCategory
    }
    fun getSelected(): MutableLiveData<ProductModel?>{
        return _selected
    }

    fun clearSelected(){
        _selected.value = null
    }

    // Commands from View
    fun filterCategoryAndSort(){
        val categoryModel = ProductModel(-1,_activeCategory.value!!.dispName)
        val filtered = productListAll.filter { productModel ->
            when (_activeCategory.value!!){
                CATEGORY.Sativa -> productModel.category == CATEGORY.Sativa
                CATEGORY.Hybrid -> productModel.category == CATEGORY.Hybrid
                CATEGORY.Indica -> productModel.category == CATEGORY.Indica
                else -> true
            }
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

        val manipulatedList = ArrayList<ProductModel>()
        manipulatedList.add(categoryModel)
        manipulatedList.addAll(sortedList)

        _productList.value = manipulatedList
    }

    // Data binding
    fun isCategoryActive(index: Int): Boolean{
        when (index){
            0 -> return _activeCategory.value!! == CATEGORY.Sativa || _activeCategory.value!! == CATEGORY.ALL
            1 -> return _activeCategory.value!! == CATEGORY.Hybrid || _activeCategory.value!! == CATEGORY.ALL
            2 -> return _activeCategory.value!! == CATEGORY.Indica || _activeCategory.value!! == CATEGORY.ALL
            else -> {
                return false
            }
        }
    }
    fun onCategoryClicked(index: Int){
        when (index){
            0 -> {
                _activeCategory.value = CATEGORY.Sativa
                filterCategoryAndSort()
            }
            1 -> {
                _activeCategory.value = CATEGORY.Hybrid
                filterCategoryAndSort()
            }
            2 -> {
                _activeCategory.value = CATEGORY.Indica
                filterCategoryAndSort()
            }
            else ->{
                _activeCategory.value = CATEGORY.ALL
                filterCategoryAndSort()
            }

        }

    }

    fun onProductClick(position: Int){
        _selected.value = getProductsList().value?.get(position)
    }

    fun getProductNameAt(position: Int): String{
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
        val price = getProductsList().value?.get(position)?.price
        return "£" + price
    }

    fun getImageURLAt(position: Int): String{
        val url = getProductsList().value?.get(position)?.thumbnail_image
        return url + ""
    }

    //
}
