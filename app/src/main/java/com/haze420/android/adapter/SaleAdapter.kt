package com.haze420.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.haze420.android.model.Product
import com.haze420.android.databinding.ItemSaleListBinding
import com.haze420.android.model.ProductModel
import com.haze420.android.ui.main.sale.SaleViewModel

class SaleAdapter(viewModel_: SaleViewModel) : ListAdapter<ProductModel, SaleAdapter.ViewHolder>(ProductDiffCallback()){

    private var viewModel: SaleViewModel? = viewModel_

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSaleListBinding.inflate(
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
        private val binding: ItemSaleListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        var _position = 0
        var txtView : TextView? = null
        fun bind(viewModel: SaleViewModel, position: Int) {
            binding.apply {
                binding.viewModel = viewModel
                binding.position = position
                _position = position
                executePendingBindings()
            }
//            txtView = binding.root.findViewById(R.id.txtCountryName)
        }

        fun gotoDetail(){

//            val direction = ProductsFragmentDirections.actionProductsFragmentToAccountFragment()
//            findNavController(txtView!!).navigate(direction)
        }


    }
}

private class SaleDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name
    }
}