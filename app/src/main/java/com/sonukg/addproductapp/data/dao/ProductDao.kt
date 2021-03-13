package com.sonukg.addproductapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sonukg.addproductapp.data.entity.Product

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(product: Product)

    @Query("SELECT * FROM product ORDER BY id DESC")
    fun  getAllProduct(): LiveData<List<Product>>

}
