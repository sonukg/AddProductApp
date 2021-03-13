package com.sonukg.addproductapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sonukg.addproductapp.data.dao.ProductDao
import com.sonukg.addproductapp.data.database.ProductDB
import com.sonukg.addproductapp.data.entity.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProductRepository(application: Application) {
    private val productDao:ProductDao
    private val allProducts:LiveData<List<Product>>

    init {
        val database=ProductDB.getproductDBInstance(application.applicationContext)
        productDao=database!!.productDao()
        allProducts=productDao.getAllProduct()
    }

    fun save(product: Product)= runBlocking {
        this.launch (Dispatchers.IO){
            productDao.insert(product)
        }
    }
    fun getAllProductsList():LiveData<List<Product>>{
        return allProducts
    }
}