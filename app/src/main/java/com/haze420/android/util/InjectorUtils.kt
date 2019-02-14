package com.haze420.android.util

import com.google.samples.apps.sunflower.viewmodels.PlantListViewModelFactory
import com.haze420.android.model.Product
import com.haze420.android.ui.main.products.ProductDetailViewModel

object InjectorUtils {
    fun provideProductDetailViewModelFactory(product: Product): PlantListViewModelFactory {
        return PlantListViewModelFactory(product)
    }
}