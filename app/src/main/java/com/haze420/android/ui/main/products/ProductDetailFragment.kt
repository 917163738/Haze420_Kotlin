package com.haze420.android.ui.main.products

import android.graphics.Typeface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.haze420.android.R
import com.haze420.android.adapter.ImageViewPagerAdapter

import com.haze420.android.databinding.FragmentProductDetailBinding
import com.haze420.android.model.ImageModel
import com.haze420.android.model.Product
import com.haze420.android.model.ProductModel
import com.haze420.android.ui.MainActivity
import com.haze420.android.util.InjectorUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product_detail.*
import java.util.*
import kotlin.collections.ArrayList

class ProductDetailFragment : Fragment(), ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        currentPage = position
    }

    companion object {
        fun newInstance() = ProductDetailFragment()
    }


    private lateinit var product: ProductModel
    private lateinit var viewModel: ProductDetailViewModel

    var NUM_PAGES = 0
    var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        product = ProductDetailFragmentArgs.fromBundle(arguments!!).productData
        val binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root
        val factory = InjectorUtils.provideProductDetailViewModelFactory(product)
        viewModel = ViewModelProviders.of(this, factory).get(ProductDetailViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mainActivity = activity as MainActivity
        mainActivity.actionBarView.config_ProductDeatilFragment()

        // --------------- Rating bar menu setup
        ratingLayout.setOnClickListener {
            val direction = ProductDetailFragmentDirections.actionProductDetailToReviews(product.name, product.id.toString(), false)
            findNavController().navigate(direction)
        }
        // View Pager Set up
        configImageSlider()

        // Weights view setup
        configWeightsView()

    }

    private fun configWeightsView(){
        val sizeByDimen = context!!.resources.getDimension(R.dimen._15ssp)
        val bookFont  = ResourcesCompat.getFont(context!!, R.font.avenir_book)
        for (index in 0 .. product.attributes!!.weight.size - 1){
            val txtView = TextView(context)
            val layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.weight = 1.0f
            txtView.layoutParams = layoutParams
            txtView.gravity = Gravity.CENTER

            txtView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeByDimen)
            txtView.text = product.attributes!!.weight.get(index)
            txtView.typeface = bookFont
            if (index == 0){
                txtView.setTextColor(ContextCompat.getColor(context!!, R.color.red))
            }else{
                txtView.setTextColor(ContextCompat.getColor(context!!, R.color.weight_color))
            }
            llWeight.addView(txtView)
            txtView.setOnClickListener {
                onClickWeigt(index)
            }
        }
    }

    private fun onClickWeigt(index: Int){
        for (i in 0 .. llWeight.childCount - 1){
            val txtView = llWeight.getChildAt(i) as TextView
            if (i == index){
                txtView.setTextColor(ContextCompat.getColor(context!!, R.color.red))
            }else{
                txtView.setTextColor(ContextCompat.getColor(context!!, R.color.weight_color))
            }
        }
    }

    private fun configImageSlider(){
        NUM_PAGES = product.images.size
        viewPager.adapter = ImageViewPagerAdapter(context!!, product.images)
        indicator.setViewPager(viewPager)
        // Auto sliding
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            if (viewPager != null){
                viewPager.setCurrentItem(currentPage++, true)
            }
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 2000, 2000)
        viewPager.addOnPageChangeListener(this)
    }

}
