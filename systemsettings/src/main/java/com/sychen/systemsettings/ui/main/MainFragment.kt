package com.sychen.systemsettings.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alibaba.android.arouter.launcher.ARouter
import com.sychen.basic.ARouterUtil
import com.sychen.systemsettings.R
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sitCardLayout.setOnClickListener { v->
            Navigation.findNavController(v)
                .navigate(R.id.action_mainFragment_to_cardSetFragment)
        }
        toolbar.setNavigationOnClickListener { v->
            val makeScaleUpAnimation =
                ActivityOptionsCompat.makeScaleUpAnimation(v, v.width, v.height, 0, 0)
            ARouter.getInstance()
                .build(ARouterUtil.START_MAIN_ACTIVITY)
                .withOptionsCompat(makeScaleUpAnimation)
                .navigation()
        }
    }
}