package com.haze420.android.ui.onboarding.splash

import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.haze420.android.R
import com.haze420.android.ui.MainActivity
import com.haze420.android.util.findMainNavController

class LaunchFragment : Fragment() {

    companion object {
        fun newInstance() = LaunchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_launch, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainAct = activity as MainActivity
        mainAct.hideActionBarView()
        Handler().postDelayed({
            view?.let {
                findNavController(this).navigate(R.id.action_launchFragment_to_loginFragment)
            }
//            findMainNavController().navigate(R.id.action_launchFragment_to_loginFragment)
        }, 1500)
    }
}