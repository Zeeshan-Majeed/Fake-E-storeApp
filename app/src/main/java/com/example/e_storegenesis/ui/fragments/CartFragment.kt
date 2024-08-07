package com.example.e_storegenesis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.e_storegenesis.R
import com.example.e_storegenesis.databinding.FragmentCartBinding
import com.example.e_storegenesis.ui.adapters.CartAdapter
import com.example.e_storegenesis.ui.base.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CartFragment : BaseFragment() {
    private val binding by lazy {
        FragmentCartBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        CartAdapter(object : CartAdapter.OnRemoveClicked {
            override fun onRemoved(id: Int) {
                CoroutineScope(Dispatchers.IO).launch {
                    database.getDao().addToCart(id, false)
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            toolbar.tvTitle.text = getString(R.string.cart)
            toolbar.btnBack.isVisible = true
            toolbar.btnBack.setOnClickListener {
                goBack()
            }


            btnBrowse.setOnClickListener {
                goBack()
            }

            recyclerview.adapter = adapter
            database.getDao().getProductsInCart().observe(viewLifecycleOwner) {
                noItemView.isVisible = it.isEmpty()
                adapter.setData(it)
            }
            database.getDao().getSumOfPricesInCart().observe(viewLifecycleOwner) {
                if (it != null)
                    adapter.updateSubTotal(it)
            }
        }
    }

    override fun onDestroyView() {
        database.getDao().getProductsInCart().removeObservers(viewLifecycleOwner)
        database.getDao().getSumOfPricesInCart().removeObservers(viewLifecycleOwner)
        super.onDestroyView()
    }
}