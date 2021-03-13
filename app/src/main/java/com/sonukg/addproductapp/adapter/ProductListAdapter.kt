package com.sonukg.addproductapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonukg.addproductapp.data.entity.Product
import com.sonukg.addproductapp.databinding.ProductListItemBinding

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    private var productList: MutableList<Product> = mutableListOf()
    private var isCategorySelected: Boolean = false
    private var categoryName = ""


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position], isCategorySelected)
    }

    override fun getItemCount(): Int = productList.size

    fun setProducts(products: MutableList<Product>, isCategorySelected: Boolean = false) {
        productList = products
        this.isCategorySelected = isCategorySelected
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(binding: ProductListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var bind = binding
        fun bind(product: Product, isCategorySelected: Boolean) {
            if (isCategorySelected && categoryName.toUpperCase().equals(product.category, true)) {
                bind.nameTitle.text = product.name
                bind.priceTitle.text = product.price
                bind.relCategory.visibility=View.GONE
                bind.categoryName.visibility = View.GONE
            } else if (isCategorySelected && !categoryName.toUpperCase().equals(product.category, true)) {
                product.category?.let { categoryName = it }
                bind.nameTitle.text = product.name
                bind.priceTitle.text = product.price
                bind.relCategory.visibility=View.GONE
                bind.categoryName.visibility = View.VISIBLE
                bind.categoryName.text = categoryName
            } else {
                bind.nameTitle.text = product.name
                bind.priceTitle.text = product.price
                bind.categoryTitle.text = product.category
                bind.categoryName.visibility = View.GONE
            }
        }
    }
}