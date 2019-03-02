package com.haze420.android.ui.main.products

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.haze420.android.R
import com.haze420.android.adapter.ProductsAdapter
import com.haze420.android.model.ProductModel
import com.haze420.android.model.enums.FilterType
import com.haze420.android.model.enums.SlideMenuType
import com.haze420.android.ui.main.BaseMenuLevelFragment
import com.haze420.android.webservice.core.RetrofitFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_products.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException


class ProductsFragment : BaseMenuLevelFragment(){

    companion object {
        fun newInstance() = ProductsFragment()
    }

    private lateinit var viewModel: ProductsViewModel
    var activeFilterType = FilterType.MostPopular

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuItemTypeFor = SlideMenuType.Products
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mMainActivity.showActionBarView()
        mMainActivity.actionBarView.config_ProductsFragment()

        viewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)

        val adapter = ProductsAdapter(viewModel)
        recyclerView.adapter = adapter
        subscribeUi(adapter)
        viewModel.getActiveCategory().observe(this, Observer {
            txtStrainType.setText(it.toString())
        })

    }

    private fun subscribeUi(adapter: ProductsAdapter) {
        imgFilter.setOnClickListener {
            if (filtersLayout.isOpenedFilter.value!!){
                filtersLayout.closeFilter()
            }else{
                filtersLayout.openFilter()
            }
        }
        filtersLayout.selectedFilter.observe(this, Observer {
            Log.d("TAG", "================= Filter selected. " + it.toString())
            viewModel.selectedFilter = it
        })
        viewModel.getProductsList().observe(viewLifecycleOwner, Observer { p ->
            if (p.size == 0) {
                // Show  empty warning!
            } else {
//                sharedViewModel.showEmpty.set(View.GONE)
//                sharedViewModel.setDogBreedsInAdapter(dogBreeds)
                adapter.submitList(p)
//                adapter.notifyItemChanged(0)
//                recyclerView.smoothScrollToPosition(0)
            }
        })
        viewModel.getSelected().observe(this, Observer {
            if (it != null){
                viewModel.clearSelected()
                val direction = ProductsFragmentDirections.actionProductsFragmentToProductDetail(it)
                findNavController().navigate(direction)
            }

        })
        if (viewModel.productListAll.size == 0){
            loadProducts()
        }else{
            loadProducts(true)
        }
    }

    private fun loadProducts(silently: Boolean = false){
        if (!mMainActivity.checkConnection()!!){
            return
        }
        if (!silently){
            mMainActivity.showLoading()
        }
        val token = mMainActivity.prefs.token
        if (token == ""){
            mMainActivity.showUnauthError()
            return
        }
        val service = RetrofitFactory.makeHomeServiceService(token)
        GlobalScope.launch(Dispatchers.Main){
            val params: HashMap<String, String> = HashMap()
            params.set("page", "1")
            params.set("per_page", "100")
            params.set("offset", "0")
            params.set("order", "desc")
            params.set("orderby", "popularity")

            val request = service.getProductList(params)
            try {
                // Wait for response
                val response = request.await()

                //Hide loading
                mMainActivity.hideLoading()
                // Handle response
                if (response.success){
                    val productList = ArrayList<ProductModel>(response.data)
                    viewModel.productListAll = productList
                }else{
                    response.error?.let { it.message?.let { it1 -> mMainActivity.showError(it1) } }
                }

            }catch (e: HttpException){
                handleAPIError(e)

            }catch (e: Throwable){
                handleAPIError(e)
            }
        }
    }

    // Filter management ---------------------------------------------
    // TODO: Need to create a separate widget class for filter layout

    override fun handleTransaction(from: SlideMenuType, goto: SlideMenuType){
        Log.d("Test", "handleTransaction(goto: SlideMenuType) ------------------")
        if (goto == menuItemTypeFor) return // Filter actions for me.
        if (from != menuItemTypeFor) return // Filter actions for me.
        view?.let {
            if (goto == SlideMenuType.Basket){
                if (!Navigation.findNavController(it).popBackStack(R.id.basketFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_productsFragment_to_basketFragment)
                }
            }else if (goto == SlideMenuType.SALE){
                if (!Navigation.findNavController(it).popBackStack(R.id.saleFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_productsFragment_to_saleFragment)
                }
            }else if (goto == SlideMenuType.Orders){
                if (!Navigation.findNavController(it).popBackStack(R.id.ordersFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_productsFragment_to_ordersFragment)
                }
            }else if (goto == SlideMenuType.Account){
//                findMainNavController().navigate(R.id.action_productsFragment_to_accountFragment)
                if (!Navigation.findNavController(it).popBackStack(R.id.accountFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_productsFragment_to_accountFragment)
                }
            }else if (goto == SlideMenuType.Info){
                if (!Navigation.findNavController(it).popBackStack(R.id.infoFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_productsFragment_to_infoFragment)
                }
            }else if (goto == SlideMenuType.Followus){
                if (!Navigation.findNavController(it).popBackStack(R.id.followusFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_productsFragment_to_followusFragment)
                }
            }
        }
    }

}
