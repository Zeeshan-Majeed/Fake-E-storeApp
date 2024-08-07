package com.example.e_storegenesis.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_storegenesis.R
import com.example.e_storegenesis.databinding.ItemProductsBinding
import com.example.e_storegenesis.db.models.Products

class ProductAdapter(private val onItemClick: OnItemClicked) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private val dataList = mutableListOf<Products>()

    interface OnItemClicked {
        fun onClick(item: Products, position: Int)
        fun onAddToCart(item: Products, position: Int, added: Boolean)
    }

    class ViewHolder(itemView: ItemProductsBinding) : RecyclerView.ViewHolder(itemView.root) {
        val tvName = itemView.tvName
        val tvPrice = itemView.tvPrice
        val ivProduct = itemView.ivProduct
        val btnAdd = itemView.btnAdd
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        with(holder) {
            tvName.text = item.title
            tvPrice.text = "${item.price}$"
            Glide.with(itemView.context).load(item.image).into(ivProduct)

            if (item.addedToCart)
                btnAdd.setImageResource(R.drawable.delete_from_cart)
            else
                btnAdd.setImageResource(R.drawable.ic_add_to_cart)

            btnAdd.setOnClickListener {
                onItemClick.onAddToCart(item, position, !item.addedToCart)
            }
            itemView.setOnClickListener {
                onItemClick.onClick(item,position)
            }
        }
    }

    fun addData(mList: MutableList<Products>) {
        dataList.clear()
        dataList.addAll(mList)
        notifyDataSetChanged()
    }
}