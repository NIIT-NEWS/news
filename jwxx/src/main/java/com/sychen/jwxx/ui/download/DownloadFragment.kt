package com.sychen.jwxx.ui.download

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sychen.jwxx.R
import com.sychen.jwxx.ui.publicnotice.NoticeAdapter
import kotlinx.android.synthetic.main.notice_fragment.*

class DownloadFragment : Fragment() {

    companion object {
        fun newInstance() = DownloadFragment()
    }

    private lateinit var viewModel: DownloadViewModel
    private lateinit var downloadAdapter: DownloadAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.download_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DownloadViewModel::class.java)
        downloadAdapter = DownloadAdapter()
        recyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = downloadAdapter
            downloadAdapter.notifyDataSetChanged()
        }
        viewModel.downloadList.observe(requireActivity(),{
            downloadAdapter.submitList(it)
        })
    }

}