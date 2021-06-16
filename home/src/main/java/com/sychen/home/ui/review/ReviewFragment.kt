package com.sychen.home.ui.review

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.home.R
import com.sychen.home.network.model.NiitNews
import kotlinx.android.synthetic.main.review_fragment.*

class ReviewFragment : Fragment() {

    companion object fun getInstance(new: NiitNews.News): Fragment {
        val reviewFragment = ReviewFragment()
        val bundle = Bundle()
        bundle.putString("NEW_INFO", Gson().toJson(new, NiitNews.News::class.java))
        reviewFragment.arguments = bundle
        return reviewFragment
    }

    private lateinit var viewModel: ReviewViewModel
    private lateinit var news: NiitNews.News
    private lateinit var reviewListAdapter: ReviewListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.review_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)
        try {
            news = Gson().fromJson(arguments?.getString("NEW_INFO"), NiitNews.News::class.java)
        } catch (e: NullPointerException) {
            Log.e(TAG, "ReviewFragment-onActivityCreated: ${e.message}")
        }
        initView()
    }

    private fun initView() {
        viewModel.getReviewByNid(news.id.toString())
        reviewListAdapter = ReviewListAdapter()
        review_recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = reviewListAdapter
        }
        viewModel.reviewInfo.observe(requireActivity(),{
            reviewListAdapter.submitList(it)
        })
    }

}