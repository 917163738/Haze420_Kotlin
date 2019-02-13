package com.haze420.android.ui.main.basket

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation

import com.haze420.android.R
import com.haze420.android.adapter.BasketAdapter
import com.haze420.android.adapter.ProductsAdapter
import com.haze420.android.model.enums.SlideMenuType
import com.haze420.android.ui.MainActivity
import com.haze420.android.ui.main.BaseMenuLevelFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_products.*

class BasketFragment : BaseMenuLevelFragment(){

    companion object {
        fun newInstance() = BasketFragment()
    }

    private lateinit var viewModel: BasketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuItemTypeFor = SlideMenuType.Basket
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BasketViewModel::class.java)
        val mainActivity = activity as MainActivity
        mainActivity.actionBarView.config_BasketFragment()
        val adapter = BasketAdapter(viewModel)
        recyclerView.adapter = adapter
        subscribeUi(adapter)

    }

    private fun subscribeUi(adapter: BasketAdapter) {
        viewModel.getBasketItemList().observe(viewLifecycleOwner, Observer { p ->
            if (p.size == 0) {
                // Show  empty warning!
            } else {
//                sharedViewModel.showEmpty.set(View.GONE)
                adapter.submitList(p)
            }
        })
    }

    override fun handleTransaction(from: SlideMenuType, goto: SlideMenuType){
        Log.d("Test", "handleTransaction(goto: SlideMenuType) ------------------")
        if (goto == menuItemTypeFor) return // Filter actions for me.
        if (from != menuItemTypeFor) return // Filter actions for me.
        view?.let {
            if (goto == SlideMenuType.Products){
                if (!Navigation.findNavController(it).popBackStack(R.id.productsFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_basketFragment_to_productsFragment)
                }
            }else if (goto == SlideMenuType.SALE){
                if (!Navigation.findNavController(it).popBackStack(R.id.saleFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_basketFragment_to_saleFragment)
                }
            }else if (goto == SlideMenuType.Orders){
                if (!Navigation.findNavController(it).popBackStack(R.id.ordersFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_basketFragment_to_ordersFragment)
                }
            }else if (goto == SlideMenuType.Account){
                if (!Navigation.findNavController(it).popBackStack(R.id.accountFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_basketFragment_to_accountFragment)
                }
            }else if (goto == SlideMenuType.Info){
                if (!Navigation.findNavController(it).popBackStack(R.id.infoFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_basketFragment_to_infoFragment)
                }
            }else if (goto == SlideMenuType.Followus){
                if (!Navigation.findNavController(it).popBackStack(R.id.followusFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_basketFragment_to_followusFragment)
                }
            }
        }
    }

}
