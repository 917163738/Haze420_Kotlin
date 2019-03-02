package com.haze420.android.ui.main.orders

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.haze420.android.adapter.ReviewsAdapter
import com.haze420.android.databinding.FragmentReviewsBinding
import com.haze420.android.model.ProductModel
import com.haze420.android.model.Review
import com.haze420.android.model.ReviewModel
import com.haze420.android.ui.BaseFragment
import com.haze420.android.ui.MainActivity
import com.haze420.android.webservice.core.RetrofitFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_reviews.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ReviewsFragment : BaseFragment() {
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
        viewModel.reviewList.observe(viewLifecycleOwner, Observer{ reviewList ->
            if (reviewList.size == 0) {
                // Show  empty warning!
            } else {
                adapter.submitList(reviewList)
            }
        } )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // ---------- Config action bar
        mMainActivity.actionBarView.config_ReviewsFragment()

        // -----   Config leave review
        if (!shouldLeaveReview){
            leaveReviewLayout.visibility = View.GONE
        }

        loadReviews()
    }


    private fun loadReviews(){
        if (!mMainActivity.checkConnection()!!){
            return
        }

        mMainActivity.showLoading()

        val token = mMainActivity.prefs.token
        if (token == ""){
            mMainActivity.showUnauthError()
            return
        }
        val service = RetrofitFactory.makeHomeServiceService(token)
        GlobalScope.launch(Dispatchers.Main){
            val params: HashMap<String, String> = HashMap()
            params.set("product", productId)
            params.set("page", "1")
            params.set("per_page", "100")
            params.set("offset", "0")
            params.set("order", "desc")
            params.set("orderby", "date_gmt")
            params.set("status", "approved")

            val request = service.getReviewList(params)
            try {
                // Wait for response
                val response = request.await()

                //Hide loading
                mMainActivity.hideLoading()
                // Handle response
                if (response.success){
                    viewModel.reviewList.value = response.data
                }else{
                    response.error?.let { it.message?.let { it1 -> mMainActivity.showError(it1) } }
                }

            }catch (e: HttpException){
                handleAPIError(e)

            }catch (e: Throwable){
                handleAPIError(e)
            }
        }
    }

}
