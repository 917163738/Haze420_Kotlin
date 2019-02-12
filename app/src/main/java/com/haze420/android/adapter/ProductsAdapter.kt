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
import com.haze420.android.databinding.ItemProductListBinding
import com.haze420.android.model.Product
import com.haze420.android.ui.main.account.CountriesViewModel
import com.haze420.android.ui.main.products.ProductsViewModel

class ProductsAdapter(viewModel_: ProductsViewModel) : ListAdapter<Product, ProductsAdapter.ViewHolder>(ProductDiffCallback()){

    private var viewModel: ProductsViewModel? = viewModel_

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = getItem(position)
        holder.apply {
            bind(viewModel!!, position)
            itemView.tag = country
        }
    }

    class ViewHolder(
        private val binding: ItemProductListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        var _position = 0
        var txtView : TextView? = null
        fun bind(viewModel: ProductsViewModel, position: Int) {
            binding.apply {
                binding.viewModel = viewModel
                binding.position = position
                _position = position
                executePendingBindings()
            }
//            txtView = binding.root.findViewById(R.id.txtCountryName)
        }


    }
}

private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.prductId == newItem.prductId
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.prductId == newItem.prductId
    }
}