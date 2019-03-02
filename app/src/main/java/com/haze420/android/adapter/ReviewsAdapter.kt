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
import com.haze420.android.ui.main.products.ProductsFragmentDirections
import com.haze420.android.ui.main.products.ProductsViewModel
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.haze420.android.databinding.ItemReviewBinding
import com.haze420.android.model.Review
import com.haze420.android.model.ReviewModel
import com.haze420.android.ui.main.orders.ReviewsViewModel

class ReviewsAdapter(viewModel_: ReviewsViewModel) : ListAdapter<ReviewModel, ReviewsAdapter.ViewHolder>(ReviewDiffCallback()){

    private var viewModel: ReviewsViewModel? = viewModel_

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReviewBinding.inflate(
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
        private val binding: ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        var _position = 0
        var txtView : TextView? = null
        fun bind(viewModel: ReviewsViewModel, position: Int) {
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

private class ReviewDiffCallback : DiffUtil.ItemCallback<ReviewModel>() {
    override fun areItemsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
        return oldItem.review == newItem.review
    }
}