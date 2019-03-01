package com.haze420.android.ui.main.account

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.haze420.android.R

import com.haze420.android.adapter.CountriesAdapter
import com.haze420.android.databinding.FragmentCountriesBinding
import com.haze420.android.model.enums.ActionBarItemType
import com.haze420.android.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*


class CountriesFragment : Fragment() {

    companion object {
        fun newInstance() = CountriesFragment()
    }

    private lateinit var viewModel: CountriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCountriesBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root
        viewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)
        val adapter = CountriesAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)
        setupListClick()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mainActivity = activity as MainActivity
        //Config action bar
        mainActivity.actionBarView.config_CountriesFragment()

        // observe log out action from actionbar
        mainActivity.sharedViewModel.getSelectedActionbarItem().observe(this, Observer { clickedItem ->
            if (clickedItem == ActionBarItemType.LOGOUT){
                mainActivity.sharedViewModel.setSelectedActionbarItem(null)
                view?.let{Navigation.findNavController(it).navigate(R.id.action_countriesFragment_to_loginFragment)}
            }
        })
    }

    private fun subscribeUi(adapter: CountriesAdapter) {
        viewModel.getCountryList().observe(viewLifecycleOwner, Observer { countries ->
            if (countries.size == 0) {
                // Show  empty warning!
            } else {
//                sharedViewModel.showEmpty.set(View.GONE)
//                sharedViewModel.setDogBreedsInAdapter(dogBreeds)
                adapter.submitList(countries)
            }

        })
    }

//    private fun subscribeUi() {
////        sharedViewModel.loading.set(View.VISIBLE)
////        sharedViewModel.fetchList()
//        sharedViewModel.getCountryList().observe(this, Observer{ countries ->
////            sharedViewModel.loading.set(View.GONE)
//            if (countries.size == 0) {
////                sharedViewModel.showEmpty.set(View.VISIBLE)
//            } else {
//                sharedViewModel.getAdapter().submitList(countries)
////                sharedViewModel.showEmpty.set(View.GONE)
////                sharedViewModel.setDogBreedsInAdapter(dogBreeds)
//            }
//        })
//        setupListClick()
//    }

    private fun setupListClick() {
        viewModel.getSelected().observe(this, Observer{position  ->
            Toast.makeText(context, "Selected"+ position.toString(), Toast.LENGTH_SHORT).show()
//            sharedViewModel.filterCategoryAndSort(position)
            view?.let {

                if (!Navigation.findNavController(it).popBackStack(R.id.deliveryAddressFragment, false)){
                    Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_forgotFragment)
                }
            }
        })
    }


}
