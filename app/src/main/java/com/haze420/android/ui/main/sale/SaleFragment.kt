package com.haze420.android.ui.main.sale

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.haze420.android.R
import com.haze420.android.adapter.ProductsAdapter
import com.haze420.android.adapter.SaleAdapter
import com.haze420.android.model.enums.FilterType
import com.haze420.android.model.enums.SlideMenuType
import com.haze420.android.ui.MainActivity
import com.haze420.android.ui.main.BaseMenuLevelFragment
import com.haze420.android.ui.main.products.ProductsFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sale.*

class SaleFragment : BaseMenuLevelFragment(){

    companion object {
        fun newInstance() = SaleFragment()
    }

    private lateinit var viewModel: SaleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuItemTypeFor = SlideMenuType.SALE
        return inflater.inflate(R.layout.fragment_sale, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).actionBarView.config_SALEFragment()
        viewModel = ViewModelProviders.of(this).get(SaleViewModel::class.java)
        val adapter = SaleAdapter(viewModel)
        recyclerView.adapter = adapter
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: SaleAdapter) {
        txtFilter.setOnClickListener {
           onClickFilter()
        }
        imgFilter.setOnClickListener {
            onClickFilter()
        }
        imgArrow.setOnClickListener {
            onClickFilter()
        }
        filtersLayout.selectedFilter.observe(this, Observer {
            Log.d("TAG", "================= Filter selected. " + it.toString())
            viewModel.selectedFilter.set(it.toString())
            txtFilter.setText(it.dispName)
            val anim1 = AnimationUtils.loadAnimation(context, R.anim.rotate_up)
            imgArrow.startAnimation(anim1)
        })
        viewModel.getProductsList().observe(viewLifecycleOwner, Observer { p ->
            if (p.size == 0) {
                // Show  empty warning!
            } else {
//                sharedViewModel.showEmpty.set(View.GONE)
//                sharedViewModel.setDogBreedsInAdapter(dogBreeds)
//                adapter.submitList(p)
//                adapter.notifyItemChanged(0)
//                recyclerView.smoothScrollToPosition(0)
            }
        })
        viewModel.getSelected().observe(this, Observer {
            if (it != null){
                viewModel.clearSelected()
                val direction = SaleFragmentDirections.actionSaleToProductDetail(it)
                findNavController().navigate(direction)
            }

        })
    }

    private fun onClickFilter(){
        if (filtersLayout.isOpenedFilter.value!!){
            val anim1 = AnimationUtils.loadAnimation(context, R.anim.rotate_up)
            imgArrow.startAnimation(anim1)
            filtersLayout.closeFilter()
        }else{
            val anim1 = AnimationUtils.loadAnimation(context, R.anim.rotate_down)
            imgArrow.startAnimation(anim1)
            filtersLayout.openFilter()
        }
    }

    override fun handleTransaction(from: SlideMenuType, goto: SlideMenuType){
        Log.d("Test", "handleTransaction(goto: SlideMenuType) ------------------")
        if (goto == menuItemTypeFor) return // Filter actions for me.
        if (from != menuItemTypeFor) return // Filter actions for me.
        view?.let {
            if (goto == SlideMenuType.Products){
                if (!Navigation.findNavController(it).popBackStack(R.id.productsFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_saleFragment_to_productsFragment)
                }
            }else if (goto == SlideMenuType.Basket){
                if (!Navigation.findNavController(it).popBackStack(R.id.basketFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_saleFragment_to_basketFragment)
                }
            }else if (goto == SlideMenuType.Orders){
                if (!Navigation.findNavController(it).popBackStack(R.id.ordersFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_saleFragment_to_ordersFragment)
                }
            }else if (goto == SlideMenuType.Account){
                if (!Navigation.findNavController(it).popBackStack(R.id.accountFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_saleFragment_to_accountFragment)
                }
            }else if (goto == SlideMenuType.Info){
                if (!Navigation.findNavController(it).popBackStack(R.id.infoFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_saleFragment_to_infoFragment)
                }
            }else if (goto == SlideMenuType.Followus){
                if (!Navigation.findNavController(it).popBackStack(R.id.followusFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_saleFragment_to_followusFragment)
                }
            }
        }
    }

}
