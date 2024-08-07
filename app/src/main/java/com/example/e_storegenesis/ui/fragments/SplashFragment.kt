package com.example.e_storegenesis.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.e_storegenesis.R
import com.example.e_storegenesis.databinding.FragmentSplashBinding
import com.example.e_storegenesis.ui.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment() {

    private val binding by lazy {
        FragmentSplashBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            delay(2000)
            isSplashPassed = true
            navigateToNext(R.id.homeFragment, true)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isSplashPassed)
            navigateToNext(R.id.homeFragment, true)
    }

}