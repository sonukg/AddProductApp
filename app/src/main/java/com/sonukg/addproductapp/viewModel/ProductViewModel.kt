package com.sonukg.addproductapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sonukg.addproductapp.data.entity.Product
import com.sonukg.addproductapp.data.repository.ProductRepository

class ProductViewModel(application: Application):AndroidViewModel(application) {
    private val productsRepository:ProductRepository=ProductRepository(application)
    private val productsAllList:LiveData<List<Product>> = productsRepository.getAllProductsList()

    fun saveProduct(product: Product){
        productsRepository.save(product)
    }

    fun getProductList():LiveData<List<Product>>{
        return productsAllList
    }
}