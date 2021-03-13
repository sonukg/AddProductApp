package com.sonukg.addproductapp
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sonukg.addproductapp.adapter.ProductListAdapter
import com.sonukg.addproductapp.data.entity.Product
import com.sonukg.addproductapp.databinding.ActivityDisplayProductsBinding
import com.sonukg.addproductapp.viewModel.ProductViewModel
import com.sonukg.addproductapp.viewModel.ProductViewModelFactory
import java.util.*

class DisplayProducts : AppCompatActivity() {
    lateinit var binding: ActivityDisplayProductsBinding
    lateinit var viewModel: ProductViewModel
    lateinit var productListAdapter: ProductListAdapter
    private var productList:MutableList<Product> = mutableListOf()
    private var productList1:MutableList<Product> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDisplayProductsBinding.inflate(layoutInflater)
        val view: View =binding.root
        setContentView(view)
        setUpRecycleView()
        val productViewFactory=ProductViewModelFactory(application)
        viewModel=ViewModelProvider(this, productViewFactory).get(ProductViewModel::class.java)
        viewModel.getProductList().observe(this, Observer { products ->
            productList.clear()
            productList1.clear()
            productList.addAll(products)
            productList1.addAll(productList)
            productListAdapter.setProducts(productList1)
//            productListAdapter.notifyDataSetChanged()
            //Log.d("main","${productList1}")
        })

    }
    private fun setUpRecycleView() {
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        productListAdapter = ProductListAdapter()
        binding.recycleView.adapter = productListAdapter
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.all_product -> {
                productList1.clear()
                productList1.addAll(productList.sortedBy { it.name })
                productListAdapter.setProducts(productList1)
                Log.d("DisplayProducts", "Sort by Name: ${productList.sortedBy { it.name }}")
                return true
            }
            R.id.by_price -> {
                productList1.clear()
                productList1.addAll(productList.sortedBy { it.price?.toInt() })
                productListAdapter.setProducts(productList1)
                Log.d("DisplayProducts","Sort by price: ${productList.sortedBy { it.price?.toInt() }}")
                return true
            }
            R.id.by_category -> {
                productList1.clear()
                productList1.addAll(productList.sortedBy { it.category })
                productListAdapter.setProducts(productList1, true)
                Log.d("DisplayProducts", "Sort by Category: ${productList.sortedBy { it.category }}")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}