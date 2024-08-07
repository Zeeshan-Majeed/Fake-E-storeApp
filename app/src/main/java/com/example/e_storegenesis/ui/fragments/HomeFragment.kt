package com.example.e_storegenesis.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.e_storegenesis.R
import com.example.e_storegenesis.databinding.FragmentHomeBinding
import com.example.e_storegenesis.db.models.Products
import com.example.e_storegenesis.ui.adapters.ProductAdapter
import com.example.e_storegenesis.ui.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : BaseFragment() {

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ProductAdapter(object : ProductAdapter.OnItemClicked {
            override fun onClick(item: Products, position: Int) {
                navigateToNext(R.id.productFragment, bundle = bundleOf(Pair("product_id", item.id)))
            }

            override fun onAddToCart(item: Products, position: Int, added: Boolean) {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        database.getDao().addToCart(item.id, added)
                    }
                }
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("check_splash_data", "home On: ")
        with(binding) {
            recyclerView.adapter = adapter

            toolbar.btnCart.isVisible = true

            toolbar.btnCart.setOnClickListener {
                navigateToNext(R.id.cartFragment)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isSplashPassed = false

        database.getDao().getAllProducts().observe(this) {
            adapter.addData(it)
        }

        database.getDao().getProductsInCart().observe(this) {
            with(binding.toolbar) {
                if (it.isNotEmpty()) {
                    tvCartCount.isVisible = true
                    if (it.size > 9)
                        tvCartCount.text = "9+"
                    else
                        tvCartCount.text = it.size.toString()
                } else {
                    tvCartCount.isVisible = false
                }
            }
        }

    }

    override fun goBack() {
        activity?.finish()
    }
}