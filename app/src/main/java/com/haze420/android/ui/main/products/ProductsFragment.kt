package com.haze420.android.ui.main.products

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.haze420.android.R
import com.haze420.android.adapter.ProductsAdapter
import com.haze420.android.model.enums.FilterType
import com.haze420.android.model.enums.SlideMenuType
import com.haze420.android.ui.MainActivity
import com.haze420.android.ui.main.BaseMenuLevelFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_products.*

import kotlinx.android.synthetic.main.form_filter.*


class ProductsFragment : BaseMenuLevelFragment(){

    companion object {
        fun newInstance() = ProductsFragment()
    }

    private lateinit var viewModel: ProductsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuItemTypeFor = SlideMenuType.Products
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainActivity = activity as MainActivity
        mainActivity.showActionBarView()
        mainActivity.actionBarView.config_ProductsFragment()

        viewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)

        val adapter = ProductsAdapter(viewModel)
        recyclerView.adapter = adapter
        subscribeUi(adapter)
        setUpFilterLayout()
        viewModel.getActiveCategory().observe(this, Observer {
            txtStrainType.setText(it.toString())
        })

    }

    private fun subscribeUi(adapter: ProductsAdapter) {
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
    }

    // Filter management ---------------------------------------------
    // TODO: Need to create a separate widget class for filter layout
    var isOpenedFilter = false
    var activeFilterType = FilterType.MostPopular
    private fun setUpFilterLayout(){
        imgFilter.setOnClickListener {
            if (isOpenedFilter){
                closeFilter()
            }else{
                openFilter()
            }
        }
        txtMostPopular.setOnClickListener {
            activeFilterType = FilterType.MostPopular
            txtMostPopular.setTextColor(ContextCompat.getColor(this.context!!, R.color.red))
            txtLowToHigh.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            txtHightToLow.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            txtTopRated.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            closeFilter()
        }
        txtLowToHigh.setOnClickListener {
            activeFilterType = FilterType.LowToHigh
            txtMostPopular.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            txtLowToHigh.setTextColor(ContextCompat.getColor(this.context!!, R.color.red))
            txtHightToLow.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            txtTopRated.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            closeFilter()
        }
        txtHightToLow.setOnClickListener {
            activeFilterType = FilterType.HighToLow
            txtMostPopular.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            txtLowToHigh.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            txtHightToLow.setTextColor(ContextCompat.getColor(this.context!!, R.color.red))
            txtTopRated.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            closeFilter()
        }
        txtTopRated.setOnClickListener {
            activeFilterType = FilterType.TopRated
            txtMostPopular.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            txtLowToHigh.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            txtHightToLow.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
            txtTopRated.setTextColor(ContextCompat.getColor(this.context!!, R.color.red))
            closeFilter()
        }
    }

    private fun openFilter(){
        layoutFilter.alpha = 0.0f
        layoutFilter.visibility = View.VISIBLE
        val anim1 = AnimationUtils.loadAnimation(context, R.anim.opening_filter)
        layoutFilter.startAnimation(anim1)
        layoutFilter.alpha = 1.0f
        isOpenedFilter = true
    }

    private fun closeFilter(){

        val anim1 = AnimationUtils.loadAnimation(context, R.anim.closing_filter)
        layoutFilter.startAnimation(anim1)
        Handler().postDelayed({layoutFilter.visibility = View.GONE}, 150)
        isOpenedFilter = false
    }

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
