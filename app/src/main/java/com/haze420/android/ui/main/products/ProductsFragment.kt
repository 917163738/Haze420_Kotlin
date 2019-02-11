package com.haze420.android.ui.main.products

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation

import com.haze420.android.R
import com.haze420.android.model.SlideMenuType
import com.haze420.android.ui.MainActivity
import com.haze420.android.ui.main.BaseMenuLevelFragment
import kotlinx.android.synthetic.main.activity_main.*

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
        viewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)
        mainActivity.actionBarView.config_ProductsFragment()

        // TODO: Use the ViewModel

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
