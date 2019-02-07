package com.haze420.android.ui.main.orders

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.haze420.android.R
import com.haze420.android.model.MenuItemType
import com.haze420.android.ui.MainActivity
import com.haze420.android.ui.main.BaseMenuLevelFragment

class OrdersFragment : BaseMenuLevelFragment(){

    companion object {
        fun newInstance() = OrdersFragment()
    }

    private lateinit var viewModel: OrdersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuItemTypeFor = MenuItemType.Orders
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OrdersViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun handleTransaction(goto: MenuItemType){
        if (goto == menuItemTypeFor) return
        view?.let {
            if (goto == MenuItemType.Products){
                if (!Navigation.findNavController(it).popBackStack(R.id.productsFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_ordersFragment_to_productsFragment)
                }
            }else if (goto == MenuItemType.Basket){
                if (!Navigation.findNavController(it).popBackStack(R.id.basketFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_ordersFragment_to_basketFragment)
                }
            }else if (goto == MenuItemType.SALE){
                if (!Navigation.findNavController(it).popBackStack(R.id.saleFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_ordersFragment_to_saleFragment)
                }
            }else if (goto == MenuItemType.Account){
                if (!Navigation.findNavController(it).popBackStack(R.id.accountFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_ordersFragment_to_accountFragment)
                }
            }else if (goto == MenuItemType.Info){
                if (!Navigation.findNavController(it).popBackStack(R.id.infoFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_ordersFragment_to_infoFragment)
                }
            }else if (goto == MenuItemType.Followus){
                if (!Navigation.findNavController(it).popBackStack(R.id.followusFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_ordersFragment_to_followusFragment)
                }
            }
        }
    }

}
