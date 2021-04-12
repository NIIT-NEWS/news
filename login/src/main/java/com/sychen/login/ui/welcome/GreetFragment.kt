package com.sychen.login.ui.welcome

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.sychen.basic.DataStoreUtils
import com.sychen.login.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class GreetFragment : Fragment() {

    companion object {
        fun newInstance() = GreetFragment()
    }

    private lateinit var viewModel: GreetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.greet_fragment, container, false)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                Navigation.findNavController(view)
                    .navigate(R.id.action_greetFragment_to_loginFragment)
            }
        }, 2_000)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GreetViewModel::class.java)
        val readDataToDataStore: Flow<String> = DataStoreUtils.readDataToDataStore(requireContext())
        lifecycleScope.launch {
            readDataToDataStore.collect{
                viewModel.verifyToken(it)
            }
        }
    }

}