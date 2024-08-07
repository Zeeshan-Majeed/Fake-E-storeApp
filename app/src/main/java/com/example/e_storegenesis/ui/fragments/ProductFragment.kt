package com.example.e_storegenesis.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.e_storegenesis.R
import com.example.e_storegenesis.databinding.FragmentProductBinding
import com.example.e_storegenesis.ui.base.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProductFragment : BaseFragment() {

    private val binding by lazy {
        FragmentProductBinding.inflate(layoutInflater)
    }
    private var isInCart = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val productId = arguments?.getInt("product_id")
            if (productId != null) {

                CoroutineScope(Dispatchers.IO).launch {
                    val product = database.getDao().getProductById(productId)
                    withContext(Dispatchers.Main) {
                        tvName.text = product.title
                        tvPrice.text = "${product.price}$"
                        tvDescription.text = product.description
                        ratingBar.rating = (product.rating).toFloat()
                        tvSold.text="Total sold: ${product.sold}"
                        context?.let {
                            Glide.with(it).load(product.image).into(ivProduct)
                        }
                        if (product.addedToCart) {
                            isInCart = true
                            btnAddToCart.text = getString(R.string.remove_from_cart)
                        } else {
                            isInCart = false
                            btnAddToCart.text = getString(R.string.add_to_cart)
                        }
                    }
                }

                btnAddToCart.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        database.getDao().addToCart(productId, !isInCart)
                    }
                    if (isInCart)
                        btnAddToCart.text = getString(R.string.add_to_cart)
                    else
                        btnAddToCart.text = getString(R.string.remove_from_cart)

                    isInCart = !isInCart
                }
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()
                goBack()
            }

            btnViewCart.setOnClickListener {
                navigateToNext(R.id.cartFragment)
            }
            btnBack.setOnClickListener {
                goBack()
            }
        }
    }

}