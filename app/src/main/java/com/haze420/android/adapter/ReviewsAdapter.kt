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
import com.haze420.android.R
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.Target
import com.haze420.android.databinding.ItemReviewBinding
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
        lateinit var avatarImageView : ImageView
        fun bind(viewModel: ReviewsViewModel, position: Int) {
            binding.apply {
                binding.viewModel = viewModel
                binding.position = position
                _position = position
                executePendingBindings()
            }
            avatarImageView = binding.root.findViewById(R.id.imgAvatar)
            val requestOptions = RequestOptions.circleCropTransform()
                .placeholder(R.drawable.avatar)

            Glide.with(avatarImageView.context)
                .load(binding.viewModel!!.getAvatarURLAt(_position))
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(avatarImageView)
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
        return  oldItem.id == newItem.id && oldItem.review == newItem.review
    }
}