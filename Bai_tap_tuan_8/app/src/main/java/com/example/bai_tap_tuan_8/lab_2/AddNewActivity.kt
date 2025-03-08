package com.example.bai_tap_tuan_8.lab_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bai_tap_tuan_8.databinding.ActivityAddNewBinding

private lateinit var binding: ActivityAddNewBinding

class AddNewActivity : AppCompatActivity() {

    private lateinit var databaseProductHelper: DatabaseProductHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Khởi tạo viewbinding
        binding = ActivityAddNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //DatabaseHelper
        databaseProductHelper = DatabaseProductHelper(this@AddNewActivity)

        binding.buttonInsertProduct.setOnClickListener {

            insertProduct()
        }

        binding.buttonExitProduct.setOnClickListener {

            //Chuyển qua 1 activity main2
            val intent  = Intent(this, MainActivity_lab_2::class.java)

            startActivity(intent)
        }
    }

    private fun insertProduct(){

        val name = binding.editTextInsertName.text.toString()
        val desc = binding.editTextInsertDescription.text.toString()
        val price = binding.editTextInsertPrice.text.toString().toDouble()

        val product = Product(0, name, desc, price)

        if(databaseProductHelper.addProduct(product)){

            clearInputFields()
        }
    }

    //Clear views.
    private fun clearInputFields() {

        binding.editTextInsertName.text.clear()
        binding.editTextInsertDescription.text.clear()
        binding.editTextInsertPrice.text.clear()

        // Đưa con trỏ về editTextInsertName.
        binding.editTextInsertName.requestFocus()
    }
}