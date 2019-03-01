package com.haze420.android.util

import com.google.samples.apps.sunflower.viewmodels.PlantListViewModelFactory
import com.haze420.android.model.Product
import com.haze420.android.model.ProductModel
import com.haze420.android.ui.main.products.ProductDetailViewModel

object InjectorUtils {
    fun provideProductDetailViewModelFactory(product: ProductModel): PlantListViewModelFactory {
        return PlantListViewModelFactory(product)
    }
}