package com.haze420.android.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.haze420.android.R
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
        lateinit var thumbImageView : ImageView
        lateinit var placeholderImageView : ImageView
        fun bind(viewModel: SaleViewModel, position: Int) {
            binding.apply {
                binding.viewModel = viewModel
                binding.position = position
                _position = position
                executePendingBindings()
            }
            thumbImageView = binding.root.findViewById(R.id.imgProductImage)
            placeholderImageView = binding.root.findViewById(R.id.imgPlaceholder)
            loadImages()
        }

        fun loadImages(){
            Glide.with(placeholderImageView.context)
                .load(R.drawable.loadinggif)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(placeholderImageView)
            Glide.with(thumbImageView.context)
                .load(binding.viewModel!!.getImageURLAt(_position))
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        placeholderImageView.visibility = View.GONE
                        return false
                    }
                })
                .into(thumbImageView)
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