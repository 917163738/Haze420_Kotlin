package com.haze420.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.haze420.android.R
import com.haze420.android.model.ImageModel
import java.util.ArrayList

class ImageViewPagerAdapter(private val context: Context, private val imageModelArrayList: ArrayList<ImageModel>) : PagerAdapter() {
    private val inflater: LayoutInflater


    init {
        inflater = LayoutInflater.from(context)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return imageModelArrayList.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false)!!

        val imageView = imageLayout
            .findViewById(R.id.image) as ImageView


//        imageView.setImageResource(imageModelArrayList[position].getImage_drawables())
        setImageFromUrl(imageView, imageModelArrayList.get(position).fullImageURL, imageModelArrayList.get(position).thumbImageURL)
        view.addView(imageLayout, 0)

        return imageLayout
    }

    fun setImageFromUrl(view: ImageView, fullImageUrl: String?, thumbnailUrl: String?) {
        if (!fullImageUrl.isNullOrEmpty()) {
            // Show placeholder as a static image
//            val requestOption = RequestOptions()
//                .placeholder(R.drawable.ico_placeholder).centerCrop()
            if (!thumbnailUrl.isNullOrEmpty()){
                Glide.with(view.context)
                    .load(fullImageUrl)
                    .thumbnail(Glide.with(view.context).load(thumbnailUrl))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(view)
            }else{
                Glide.with(view.context)
                    .load(fullImageUrl)
                    .thumbnail(Glide.with(view.context).load(R.drawable.loadinggif))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(view)
            }
        }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }
}