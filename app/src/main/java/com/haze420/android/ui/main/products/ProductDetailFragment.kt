package com.haze420.android.ui.main.products

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.haze420.android.adapter.ImageViewPagerAdapter

import com.haze420.android.databinding.FragmentProductDetailBinding
import com.haze420.android.model.ImageModel
import com.haze420.android.model.Product
import com.haze420.android.ui.MainActivity
import com.haze420.android.util.InjectorUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product_detail.*
import java.util.*

class ProductDetailFragment : Fragment(), ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        currentPage = position
    }

    companion object {
        fun newInstance() = ProductDetailFragment()
    }


    private lateinit var product: Product
    private lateinit var viewModel: ProductDetailViewModel



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
            val direction = ProductDetailFragmentDirections.actionProductDetailToReviews(product.name, product.id, false)
            findNavController().navigate(direction)
        }
        // -------------- View Pager Set up
        populateList()
        viewPager.adapter = ImageViewPagerAdapter(context!!, imageModelArrayList)
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

        // ----------------------------------
    }

    private var imageModelArrayList: ArrayList<ImageModel> = ArrayList()
    var NUM_PAGES = 0
    var currentPage = 0
    private fun populateList() {

        val list = ArrayList<ImageModel>()
        val imageModel = ImageModel()
        imageModel.fullImageURL = "https://www.haze420.co.uk/wp-content/uploads/2018/10/GorillaGlue4-500x500.jpg"
        imageModel.thumbImageURL = "https://www.haze420.co.uk/wp-content/uploads/2018/10/GorillaGlue4-100x100.jpg"
        list.add(imageModel)
        for (i in 0..2) {
            val imageModel = ImageModel()
            imageModel.fullImageURL = "https://www.haze420.co.uk/wp-content/uploads/2018/10/GorillaGlue4_1-500x500.jpg"
            imageModel.thumbImageURL = "https://www.haze420.co.uk/wp-content/uploads/2018/10/GorillaGlue4_1-100x100.jpg"
            list.add(imageModel)
        }

        imageModelArrayList = list
        NUM_PAGES = imageModelArrayList.size
    }

}
