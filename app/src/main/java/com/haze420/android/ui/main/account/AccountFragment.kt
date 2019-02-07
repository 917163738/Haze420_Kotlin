package com.haze420.android.ui.main.account

import android.app.Dialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.Navigation

import com.haze420.android.R
import com.haze420.android.model.MenuItemType
import com.haze420.android.ui.main.BaseMenuLevelFragment

class AccountFragment : BaseMenuLevelFragment(){
    companion object {
        fun newInstance() = AccountFragment()
    }

    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuItemTypeFor = MenuItemType.Account
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        view?.findViewById<View>(R.id.imgCopy)?.setOnClickListener {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_link_copied)
            dialog.show()
            Handler().postDelayed({dialog.dismiss()}, 1000)
        }
    }

    override fun handleTransaction(goto: MenuItemType){
        if (goto == menuItemTypeFor) return
        view?.let {
            if (goto == MenuItemType.Products){
                if (!Navigation.findNavController(it).popBackStack(R.id.productsFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_productsFragment)
                }
            }else if (goto == MenuItemType.Basket){
                if (!Navigation.findNavController(it).popBackStack(R.id.basketFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_basketFragment)
                }
            }else if (goto == MenuItemType.SALE){
                if (!Navigation.findNavController(it).popBackStack(R.id.saleFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_saleFragment)
                }
            }else if (goto == MenuItemType.Orders){
                if (!Navigation.findNavController(it).popBackStack(R.id.ordersFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_ordersFragment)
                }
            }else if (goto == MenuItemType.Info){
                if (!Navigation.findNavController(it).popBackStack(R.id.infoFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_infoFragment)
                }
            }else if (goto == MenuItemType.Followus){
                if (!Navigation.findNavController(it).popBackStack(R.id.followusFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_followusFragment)
                }
            }
        }
    }

}
