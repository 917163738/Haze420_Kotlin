/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haze420.android.binding

import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.haze420.android.R
import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder





@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) { /// Try thumnail link to load list pages. And then try fullImage link in detail page
    if (!imageUrl.isNullOrEmpty()) {
        // Show placeholder as a static image
//        val requestOption = RequestOptions()
//            .placeholder(R.drawable.loadinggif).fitCenter()
//        Glide.with(view.context)
//                .load(imageUrl)
//                .apply(requestOption)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(view)

        //load gif as a thumbnail like animating placeholder
        val thumbnailRequest = Glide
            .with(view.context)
            .load(R.drawable.loadinggif)
        Glide.with(view.context)
            .load(imageUrl)
            .thumbnail(thumbnailRequest)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)

    }
}
@BindingAdapter("fullImageFromUrl") // unused
fun bindFullImageFromUrl(view: ImageView, fullImageUrl: String?) {
    fullImageUrl ?: return
    val thumbnailUrl = fullImageUrl.replace("500x500", "100x100")
    if (!fullImageUrl.isNullOrEmpty()) {
        // Show placeholder as a static image
        val requestOption = RequestOptions()
            .placeholder(R.drawable.ico_placeholder).centerCrop()
        if (!thumbnailUrl.isNullOrEmpty()){
            Glide.with(view.context)
                .load(fullImageUrl)
                .apply(requestOption)
                .thumbnail(Glide.with(view.context).load(thumbnailUrl))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }else{
            Glide.with(view.context)
                .load(fullImageUrl)
                .apply(requestOption)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    }
}

//@BindingAdapter("setAdapter")
//fun bindRecyclerViewAdapter(recyclerView: RecyclerView, adapter: ListAdapter<T, T>) {
//    recyclerView.setHasFixedSize(true)
////    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
//    recyclerView.adapter = adapter
//}



@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
        view.text = HtmlCompat.fromHtml(description, FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}
