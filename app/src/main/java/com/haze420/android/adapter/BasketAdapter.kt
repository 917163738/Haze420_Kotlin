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
import com.haze420.android.model.BasketItem

import com.haze420.android.ui.main.basket.BasketViewModel


class BasketAdapter(viewModel_: BasketViewModel) : ListAdapter<BasketItem, BasketAdapter.ViewHolder>(BasketItemDiffCallback()){

    private var viewModel: BasketViewModel? = viewModel_

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBasketBinding.inflate(
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
        private val binding: ItemBasketBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        var _position = 0
        var txtView : TextView? = null
        fun bind(viewModel: BasketViewModel, position: Int) {
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

private class BasketItemDiffCallback : DiffUtil.ItemCallback<BasketItem>() {
    override fun areItemsTheSame(oldItem: BasketItem, newItem: BasketItem): Boolean {
        return oldItem.prductId == newItem.prductId
    }

    override fun areContentsTheSame(oldItem: BasketItem, newItem: BasketItem): Boolean {
        return oldItem.prductId == newItem.prductId && oldItem.name == newItem.name
    }
}