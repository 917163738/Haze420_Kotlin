package com.haze420.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.haze420.android.R
import com.haze420.android.databinding.ItemBasketBinding
import com.haze420.android.databinding.ItemOrderBinding
import com.haze420.android.model.BasketItem
import com.haze420.android.model.OrderItem

import com.haze420.android.ui.main.basket.BasketViewModel
import com.haze420.android.ui.main.orders.OrdersViewModel


class OrdersAdapter(viewModel_: OrdersViewModel) : ListAdapter<OrderItem, OrdersAdapter.ViewHolder>(OrderItemDiffCallback()){

    private var viewModel: OrdersViewModel? = viewModel_

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOrderBinding.inflate(
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
        private val binding: ItemOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        var _position = 0
        var txtView : TextView? = null
        fun bind(viewModel: OrdersViewModel, position: Int) {
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

private class OrderItemDiffCallback : DiffUtil.ItemCallback<OrderItem>() {
    override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem.prductId == newItem.prductId
    }

    override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem.prductId == newItem.prductId && oldItem.name == newItem.name
    }
}