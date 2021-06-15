package com.sychen.jwxx.ui.publicnotice

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sychen.jwxx.R
import kotlinx.android.synthetic.main.notice_fragment.*

class NoticeFragment : Fragment() {

    companion object {
        fun newInstance() = NoticeFragment()
    }

    private lateinit var viewModel: NoticeViewModel
    private lateinit var noticeAdapter: NoticeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notice_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoticeViewModel::class.java)
        noticeAdapter = NoticeAdapter()
        recyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = noticeAdapter
        }
        viewModel.getAllNotice().observe(requireActivity(),{
            noticeAdapter.submitList(it)
        })
    }

}