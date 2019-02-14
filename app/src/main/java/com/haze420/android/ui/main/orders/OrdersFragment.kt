package com.haze420.android.ui.main.orders

import android.app.Dialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation

import com.haze420.android.R
import com.haze420.android.adapter.CountriesAdapter
import com.haze420.android.adapter.OrdersAdapter
import com.haze420.android.adapter.ProductsAdapter
import com.haze420.android.databinding.FragmentCountriesBinding
import com.haze420.android.databinding.FragmentOrdersBinding
import com.haze420.android.model.enums.SlideMenuType
import com.haze420.android.ui.MainActivity
import com.haze420.android.ui.main.BaseMenuLevelFragment
import com.haze420.android.ui.main.account.CountriesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class OrdersFragment : BaseMenuLevelFragment(){

    companion object {
        fun newInstance() = OrdersFragment()
    }

    private lateinit var viewModel: OrdersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuItemTypeFor = SlideMenuType.Orders
        val binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root
        viewModel = ViewModelProviders.of(this).get(OrdersViewModel::class.java)
        val adapter = OrdersAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).actionBarView.config_OrdersFragment()

        viewModel.getSelectedTrackNum().observe(this, Observer {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_track_number)
            dialog.findViewById<TextView>(R.id.txtTrackNum)!!.setText(it)
            dialog.show()
            Handler().postDelayed({dialog.dismiss()}, 3000)
        })
    }
    private fun subscribeUi(adapter: OrdersAdapter) {
        viewModel.getOrderList().observe(viewLifecycleOwner, Observer { p ->
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
    }


    override fun handleTransaction(from: SlideMenuType, goto: SlideMenuType){
        Log.d("Test", "handleTransaction(goto: SlideMenuType) ------------------")
        if (goto == menuItemTypeFor) return // Filter actions for me.
        if (from != menuItemTypeFor) return // Filter actions for me.
        view?.let {
            if (goto == SlideMenuType.Products){
                if (!Navigation.findNavController(it).popBackStack(R.id.productsFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_ordersFragment_to_productsFragment)
                }
            }else if (goto == SlideMenuType.Basket){
                if (!Navigation.findNavController(it).popBackStack(R.id.basketFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_ordersFragment_to_basketFragment)
                }
            }else if (goto == SlideMenuType.SALE){
                if (!Navigation.findNavController(it).popBackStack(R.id.saleFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_ordersFragment_to_saleFragment)
                }
            }else if (goto == SlideMenuType.Account){
                if (!Navigation.findNavController(it).popBackStack(R.id.accountFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_ordersFragment_to_accountFragment)
                }
            }else if (goto == SlideMenuType.Info){
                if (!Navigation.findNavController(it).popBackStack(R.id.infoFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_ordersFragment_to_infoFragment)
                }
            }else if (goto == SlideMenuType.Followus){
                if (!Navigation.findNavController(it).popBackStack(R.id.followusFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_ordersFragment_to_followusFragment)
                }
            }
        }
    }

}
