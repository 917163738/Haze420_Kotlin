package com.haze420.android.ui.main.orders

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.haze420.android.adapter.ReviewsAdapter
import com.haze420.android.databinding.FragmentReviewsBinding
import com.haze420.android.model.Review
import com.haze420.android.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_reviews.*

class ReviewsFragment : Fragment() {
    lateinit var productId: String
    var shouldLeaveReview = true
    companion object {
        fun newInstance() = ReviewsFragment()
    }

    private lateinit var viewModel: ReviewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productId = ReviewsFragmentArgs.fromBundle(arguments!!).productId
        val productName = ReviewsFragmentArgs.fromBundle(arguments!!).productName
        shouldLeaveReview = ReviewsFragmentArgs.fromBundle(arguments!!).shouldLeaveReview
        val binding = FragmentReviewsBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root
        viewModel = ViewModelProviders.of(this).get(ReviewsViewModel::class.java)
        viewModel.setProductName(productName)
        binding.viewModel = viewModel
        val adapter = ReviewsAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)
        return binding.root
    }

    private fun subscribeUi(adapter: ReviewsAdapter) {
        var list = ArrayList<Review>()
        for (i in 0..20){
            list.add(Review(i.toString()))
        }

        adapter.submitList(list)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // ---------- Config action bar
        val mainActivity = activity as MainActivity
        mainActivity.actionBarView.config_ReviewsFragment()

        // -----   Config leave review
        if (!shouldLeaveReview){
            leaveReviewLayout.visibility = View.GONE
        }
    }

}
