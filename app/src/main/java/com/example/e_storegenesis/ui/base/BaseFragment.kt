package com.example.e_storegenesis.ui.base

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.e_storegenesis.db.DataBaseClass
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    @Inject
    lateinit var database: DataBaseClass
    var isSplashPassed = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureBackPress {
            goBack()
        }
    }

    private val navOptions = NavOptions.Builder().setEnterAnim(android.R.anim.fade_in)
        .setExitAnim(android.R.anim.fade_out).setPopEnterAnim(android.R.anim.fade_in)
        .setPopExitAnim(android.R.anim.fade_out).build()

    fun navigateToNext(
        fragmentId: Int, removeCurr: Boolean = false, bundle: Bundle = bundleOf()
    ) {
        if (view != null) {
            findNavController().apply {
                if (currentDestination?.id != fragmentId) {
                    if (removeCurr)
                        popBackStack()
                    navigate(fragmentId, bundle, navOptions)
                }
            }
        }
    }

    private fun configureBackPress(backPressed: () -> Unit) {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backPressed()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner, backPressedCallback
        )

    }

    open fun goBack() {
        view?.let {
            findNavController().popBackStack()
        }
    }
}