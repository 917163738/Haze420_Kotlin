package com.haze420.android.model.repositories

import androidx.lifecycle.MutableLiveData
import com.haze420.android.model.enums.CATEGORY
import com.haze420.android.model.Product

class ProductsRepository {

    private var _productsList = ArrayList<Product>()
    private val _productsLiveData = MutableLiveData<List<Product>>()

    init {
        generateDummyProducts()
    }

    fun trigerFetchList(isSale: Boolean = true, category: CATEGORY = CATEGORY.ALL){
        _productsLiveData.value = filterCountryList(isSale, category)
    }

    fun getProductList(): MutableLiveData<List<Product>>{
        return _productsLiveData
    }

    private fun generateDummyProducts(){
       _productsList.clear()

        // add fake product for category selection

        for (i in 0..60){
            if (i < 10){
                _productsList.add(Product(i.toString(), false, "AK-27_" + i.toString(), CATEGORY.Sativa))
            }else if (i < 20){
                _productsList.add(Product(i.toString(), false, "AK-27_" + i.toString(), CATEGORY.Hybrid))
            }else if (i < 30){
                _productsList.add(Product(i.toString(), false, "AK-27_" + i.toString(), CATEGORY.Indica))
            }else if (i < 40){
                _productsList.add(Product(i.toString(), true, "AK-27_" + i.toString(), CATEGORY.Sativa))
            }else if (i < 50){
                _productsList.add(Product(i.toString(), true, "AK-27_" + i.toString(), CATEGORY.Hybrid))
            }else{
                _productsList.add(Product(i.toString(), true, "AK-27_" + i.toString(), CATEGORY.Indica))
            }

        }

    }

    private fun filterCountryList(isSale: Boolean, category: CATEGORY): List<Product>{
        val rnds = (-200000..-100000).random()
        val fakeForCategorySelection = Product("-1", false, rnds.toString())

        val filtered = _productsList.filterIndexed { index, product ->
            if (isSale){
                   product.isSale
            }else{
                if (category == CATEGORY.ALL){
                    !product.isSale
                }else{
                    !product.isSale && product.category == category
                }
            }
        }
        var resultArray = ArrayList<Product>()
        if (!isSale){
            resultArray.add(fakeForCategorySelection)
        }
        resultArray.addAll(filtered)
        return resultArray
    }
}