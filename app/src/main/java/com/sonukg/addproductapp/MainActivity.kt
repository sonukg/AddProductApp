package com.sonukg.addproductapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.sonukg.addproductapp.data.entity.Product
import com.sonukg.addproductapp.databinding.ActivityMainBinding
import com.sonukg.addproductapp.viewModel.ProductViewModel
import com.sonukg.addproductapp.viewModel.ProductViewModelFactory

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    var product:Product?=null
    lateinit var viewModel: ProductViewModel
    private var text:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view:View=binding.root
        setContentView(view)
        val productViewFactory= ProductViewModelFactory(application)
        viewModel= ViewModelProvider(this,productViewFactory).get(ProductViewModel::class.java)
        getSpinnerSetup()
        binding.save.setOnClickListener {
            saveProduct()
        }
    }
    fun saveProduct(){
        if (validateInput()){
            val id = if (product !=null) product?.id else null
            val products = Product(
                id =id,
                name = binding.productName.text.toString(),
                price = binding.productPrice.text.toString(),
                category = text
            )
            val intent=Intent(this,DisplayProducts::class.java)
            viewModel.saveProduct(products)
            startActivity(intent)
            finish()
        }
    }

    fun validateInput():Boolean{
        if (binding.productName.text.isEmpty()){
            binding.productName.setError("Enter the name of product")
            binding.productName.requestFocus()
            return false
        }

        if (binding.productPrice.text.isEmpty()){
            binding.productPrice.setError("Enter the rate of product")
            binding.productPrice.requestFocus()
            return false
        }

        Toast.makeText(this, "Product is saved successfully.", Toast.LENGTH_SHORT).show()
        return true
    }

    private fun getSpinnerSetup() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.Category,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter
        binding.spinnerCategory.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        text= parent?.getItemAtPosition(position).toString()
        //Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.add -> {
               val intent=Intent(this,DisplayProducts::class.java)
                startActivity(intent)
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}