package com.sonukg.addproductapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sonukg.addproductapp.data.dao.ProductDao
import com.sonukg.addproductapp.data.entity.Product

@Database(entities = [Product::class],version = 1,exportSchema = false)
abstract class ProductDB : RoomDatabase() {
    abstract fun productDao():ProductDao

    companion object{
        private var productDBInstance:ProductDB?=null

        fun getproductDBInstance(context: Context):ProductDB?{
            if (productDBInstance==null){
                synchronized(ProductDB::class.java){
                    productDBInstance = Room.databaseBuilder(context,
                        ProductDB::class.java,"product_database")
                        .build()
                }
            }
            return productDBInstance
        }
    }
}