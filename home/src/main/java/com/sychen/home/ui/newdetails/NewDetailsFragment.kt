package com.sychen.home.ui.newdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import coil.load
import com.google.gson.Gson
import com.sychen.home.R
import com.sychen.home.network.model.NewsC
import kotlinx.android.synthetic.main.new_details_fragment.*

class NewDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = NewDetailsFragment()
    }

    private lateinit var viewModel: NewDetailsViewModel
    private lateinit var news: NewsC.Data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewDetailsViewModel::class.java)
        news=Gson().fromJson(arguments?.getString("NEWS"), NewsC.Data::class.java)
        initView()
    }

    private fun initView(){
        new_create.text = news.creator
        new_detail_img.load(news.newTitleImgUrl)
        new_title.text = news.newTitle
        new_info.text = news.newInfo
        back.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_newDetailsFragment_to_homeFragment)
        }
    }

}