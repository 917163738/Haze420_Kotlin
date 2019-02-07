package com.haze420.android.ui.main.products

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.haze420.android.R
import com.haze420.android.model.MenuItemType
import com.haze420.android.ui.MainActivity

class ProductsFragment : Fragment() {

    companion object {
        fun newInstance() = ProductsFragment()
    }

    private lateinit var viewModel: ProductsViewModel
    private val menuItemTypeFor = MenuItemType.Products

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainActivity = activity as MainActivity
        mainActivity.showActionBarView()
        viewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun handleTransaction(goto: MenuItemType){
        if (goto == menuItemTypeFor) return
        view?.let {
            if (goto == MenuItemType.Products){
                if (!Navigation.findNavController(it).popBackStack(R.id.productsFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_basketFragment_to_productsFragment)
                }
            }else if (goto == MenuItemType.SALE){
                if (!Navigation.findNavController(it).popBackStack(R.id.saleFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_basketFragment_to_saleFragment)
                }
            }else if (goto == MenuItemType.Orders){
                if (!Navigation.findNavController(it).popBackStack(R.id.ordersFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_basketFragment_to_ordersFragment)
                }
            }else if (goto == MenuItemType.Account){
                if (!Navigation.findNavController(it).popBackStack(R.id.accountFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_basketFragment_to_accountFragment)
                }
            }else if (goto == MenuItemType.Info){
                if (!Navigation.findNavController(it).popBackStack(R.id.infoFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_basketFragment_to_infoFragment)
                }
            }else if (goto == MenuItemType.Followus){
                if (!Navigation.findNavController(it).popBackStack(R.id.followusFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_basketFragment_to_followusFragment)
                }
            }
        }
    }

}
