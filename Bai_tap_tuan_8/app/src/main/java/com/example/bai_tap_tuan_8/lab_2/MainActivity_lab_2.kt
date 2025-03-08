package com.example.bai_tap_tuan_8.lab_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bai_tap_tuan_8.databinding.ActivityMainLab2Binding

private lateinit var binding: ActivityMainLab2Binding

class MainActivity_lab_2 : AppCompatActivity() {

    private lateinit var databaseProductHelper: DatabaseProductHelper
    private var list_products: MutableList<Product> = mutableListOf()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Khởi tạo viewbinding
        binding = ActivityMainLab2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //DatabaseHelper
        databaseProductHelper = DatabaseProductHelper(this@MainActivity_lab_2)

        productAdapter = ProductAdapter(list_products, object: ProductAdapter.OnItemClickListener{

            override fun onItemClick(product: Product) {

                Toast.makeText(this@MainActivity_lab_2, "Bạn đã click vào ${product.name_product}", Toast.LENGTH_SHORT).show()

                updateProduct(product)
            }
        })

        binding.recyclerViewProducts.adapter = productAdapter

        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(

            this@MainActivity_lab_2,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.recyclerViewProducts.visibility = View.VISIBLE

        //Event InsertButton
        binding.buttonAddProduct.setOnClickListener {

            addProduct()
        }

        loadProduct()
    }

    private fun loadProduct(){

        list_products.clear()

        list_products.addAll(databaseProductHelper.getProducts())

        productAdapter.updateList(list_products)
    }

    private fun addProduct(){

        //Chuyển qua 1 activity insert
        val intent  = Intent(this, AddNewActivity::class.java)

        startActivity(intent)
    }

    private fun updateProduct(product: Product){

        //Chuyển qua 1 activity update
        val intent  = Intent(this, UpdateActivity::class.java)

        intent.putExtra("id_product", product.id_product)
        intent.putExtra("name_product", product.name_product)
        intent.putExtra("desc_product", product.desc_product)
        intent.putExtra("price_product", product.price_product)

        startActivity(intent)
    }
}