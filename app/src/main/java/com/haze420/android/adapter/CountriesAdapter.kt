package com.haze420.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.haze420.android.R
import com.haze420.android.databinding.ItemCountryBinding
import com.haze420.android.ui.main.account.CountriesViewModel

class CountriesAdapter(viewModel_: CountriesViewModel) : ListAdapter<String, CountriesAdapter.ViewHolder>(CountryDiffCallback()){

    private var viewModel: CountriesViewModel? = viewModel_

//    fun setViewModel(vm: CountriesViewModel){ // Don't miss it to call after created Adapter
//        viewModel = vm
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCountryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = getItem(position)
        holder.apply {
            bind(viewModel!!, position)
            itemView.tag = country
        }
    }
    private fun createOnClickListener(aa: String): View.OnClickListener {
        return View.OnClickListener {

        }
    }

    class ViewHolder(
        private val binding: ItemCountryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        var _position = 0
        var txtView : TextView? = null
        fun bind(viewModel: CountriesViewModel, position: Int) {
            binding.apply {
                binding.viewModel = viewModel
                binding.position = position
                _position = position
                executePendingBindings()
            }
            txtView = binding.root.findViewById(R.id.txtCountryName)
        }


    }
}

private class CountryDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}