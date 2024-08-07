package com.example.e_storegenesis.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_storegenesis.databinding.ItemCartBinding
import com.example.e_storegenesis.databinding.ItemSubtotalPriceBinding
import com.example.e_storegenesis.db.models.Products
import com.example.e_storegenesis.utils.Extensions.roundValues
import com.example.e_storegenesis.utils.Extensions.setColoredText

class CartAdapter(private val onRemoveClicked: OnRemoveClicked) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList = mutableListOf<Products>()
    private var typeData = 0
    private var typePrices = 1

    private var subTotalPrice = 0.0

    interface OnRemoveClicked {
        fun onRemoved(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == typeData) {
            val view = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemViewHolder(view)
        }
        val view =
            ItemSubtotalPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubTotalViewHolder(view)


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == typeData) {
            val item = dataList[position]
            val holder = viewHolder as ItemViewHolder
            with(holder) {
                tvName.text = item.title
                tvPrice.setColoredText("Price: ${item.price}$")
                Glide.with(itemView.context).load(item.image).into(ivProducts)
                btnRemove.setOnClickListener {
                    onRemoveClicked.onRemoved(item.id)
                }
            }

        } else {
            val holder = viewHolder as SubTotalViewHolder
            with(holder) {

                // +10 are delivery charges
                tvSubTotal.text = "${(subTotalPrice + 10).roundValues()}$"
            }
        }
    }

    class ItemViewHolder(itemView: ItemCartBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val tvName = itemView.tvTitle
        val tvPrice = itemView.tvPrice
        val ivProducts = itemView.ivProduct
        val btnRemove = itemView.btnRemove

    }

    class SubTotalViewHolder(itemView: ItemSubtotalPriceBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val tvSubTotal = itemView.tvSubtotal

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == dataList.size - 1)
            typePrices
        else
            typeData
    }

    fun setData(mList: MutableList<Products>) {
        dataList.clear()
        dataList.addAll(mList)
        if (mList.isNotEmpty())
            dataList.add(Products(21, "", "", "", 0.0, 0.0, "", 0))

        notifyDataSetChanged()
    }

    fun updateSubTotal(price: Double) {
        subTotalPrice = price
        notifyItemChanged(dataList.size - 1)
    }

}