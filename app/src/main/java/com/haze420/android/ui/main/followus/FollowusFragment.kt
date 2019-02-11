package com.haze420.android.ui.main.followus

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.haze420.android.R
import com.haze420.android.model.SlideMenuType
import com.haze420.android.ui.MainActivity
import com.haze420.android.ui.main.BaseMenuLevelFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_followus.*
import android.content.pm.PackageManager
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.Observer


class FollowusFragment : BaseMenuLevelFragment(){
    companion object {
        fun newInstance() = FollowusFragment()
    }

    private lateinit var viewModel: FollowusViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuItemTypeFor = SlideMenuType.Followus
        return inflater.inflate(com.haze420.android.R.layout.fragment_followus, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FollowusViewModel::class.java)
        (activity as MainActivity).actionBarView.config_FollowusFragment()
        imgFB.setOnClickListener{openFB()}
        imgTwitter.setOnClickListener { openYoutube() }
        imgInstagram.setOnClickListener { openInstagram() }

        imgSwitch.setOnClickListener {
            viewModel.isSubscribed.value = !viewModel.isSubscribed.value!!
        }
        viewModel.isSubscribed.observe(this, Observer {
            if (it){
                imgSwitch.setImageResource(R.drawable.switch_on)
            }else{
                imgSwitch.setImageResource(R.drawable.switch_off)
            }
        })
    }

    private fun openFB(){
        val facebookIntent = Intent(Intent.ACTION_VIEW)
        val facebookUrl = getFBPageURL()
        facebookIntent.data = Uri.parse(facebookUrl)
        startActivity(facebookIntent)
    }

    private fun getFBPageURL(): String{
        val fbURL = getString(R.string.fb_link)
        val fbPageId = getString(R.string.fb_pageid)
        val packageManager = context?.getPackageManager()
        try {
            val versionCode = packageManager?.getPackageInfo("com.facebook.katana", 0)?.versionCode
            return if (versionCode != null && versionCode >= 3002850) { //newer versions of fb app
                "fb://facewebmodal/f?href=$fbURL"
            } else { //older versions of fb app
                "fb://page/$fbPageId"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            return fbURL //normal web url
        }
    }

    private fun openYoutube(){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_link))))
    }

    private fun openInstagram(){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.instagra_link))))
    }

    override fun handleTransaction(from: SlideMenuType, goto: SlideMenuType){
        Log.d("Test", "handleTransaction(goto: SlideMenuType) ------------------")
        if (goto == menuItemTypeFor) return // Filter actions for me.
        if (from != menuItemTypeFor) return // Filter actions for me.
        view?.let {
            if (goto == SlideMenuType.Products){
                if (!Navigation.findNavController(it).popBackStack(R.id.productsFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_followusFragment_to_productsFragment)
                }
            }else if (goto == SlideMenuType.Basket){
                if (!Navigation.findNavController(it).popBackStack(R.id.basketFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_followusFragment_to_basketFragment)
                }
            }else if (goto == SlideMenuType.SALE){
                if (!Navigation.findNavController(it).popBackStack(R.id.saleFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_followusFragment_to_saleFragment)
                }
            }else if (goto == SlideMenuType.Orders){
                if (!Navigation.findNavController(it).popBackStack(R.id.ordersFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_followusFragment_to_ordersFragment)
                }
            }else if (goto == SlideMenuType.Account){
                if (!Navigation.findNavController(it).popBackStack(R.id.accountFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_followusFragment_to_accountFragment)
                }
            }else if (goto == SlideMenuType.Info){
                if (!Navigation.findNavController(it).popBackStack(R.id.infoFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_followusFragment_to_infoFragment)
                }
            }
        }
    }

}
