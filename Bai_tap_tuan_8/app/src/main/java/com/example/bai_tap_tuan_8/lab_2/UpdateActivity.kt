package com.example.bai_tap_tuan_8.lab_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bai_tap_tuan_8.databinding.ActivityUpdateBinding

private lateinit var binding: ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var databaseProductHelper: DatabaseProductHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Khởi tạo viewbinding
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id_product", 0) //0 là giá trị mặc định nếu không tìm thấy.
        val name = intent.getStringExtra("name_product") ?: ""
        val desc = intent.getStringExtra("desc_product") ?: ""
        val price = intent.getDoubleExtra("price_product", 0.0)

        val product = Product(id, name, desc, price)

        putData(product)

        //DatabaseHelper
        databaseProductHelper = DatabaseProductHelper(this@UpdateActivity)

        binding.buttonEditProduct.setOnClickListener {

            updateProduct(id)
        }

        binding.buttonDeleteProduct.setOnClickListener {

            deleteProduct(id)
        }
    }

    private fun putData(product: Product){

        binding.editTextUpdateName.setText(product.name_product)
        binding.editTextUpdateDescription.setText(product.desc_product)
        binding.editTextUpdatePrice.setText(product.price_product.toString())
    }

    private fun updateProduct(id: Int){

        val name = binding.editTextUpdateName.text.toString()
        val desc = binding.editTextUpdateDescription.text.toString()
        val price = binding.editTextUpdatePrice.text.toString().toDouble()

        val product = Product(id, name, desc, price)

        if(databaseProductHelper.updateCourse(product)){

            teleport()
        }
    }

    private fun deleteProduct(id: Int){

        if (databaseProductHelper.deleteCourse(id)){

            teleport()
        }
    }

    private fun teleport(){

        //Chuyển qua 1 activity main2
        val intent  = Intent(this, MainActivity_lab_2::class.java)

        startActivity(intent)
    }
}