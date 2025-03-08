package com.example.bai_tap_tuan_8.lab_2

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseProductHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

    companion object {

        private const val DATABASE_NAME = "goods.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_PRODUCTS = "product"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_PRICE = "price"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        //Câu lệnh tạo table.
        val createTableStatement = ("CREATE TABLE $TABLE_PRODUCTS("
                + "${COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "${COLUMN_NAME} TEXT, "
                + "${COLUMN_DESCRIPTION} TEXT, "
                + "${COLUMN_PRICE} REAL)")

        //Thực thi câu lệnh.
        db?.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        //Xóa table nếu nó đã tồn tại.
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")

        //Rồi tạo table mới.
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getProducts(): List<Product> {

        val productList = mutableListOf<Product>()

        //db để read.
        val db = this.readableDatabase

        //Read products, select data.
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_PRODUCTS", null)

        //Add to List (Đổ data (từ cursor) vào list(productList)).
        if (cursor.moveToFirst()) {

            do {

                val id_product = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))

                val name_product = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))

                val desc_product = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))

                val price_product = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))

                productList.add(Product(id_product, name_product, desc_product, price_product))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return productList
    }

    //Insert data.
    fun addProduct(product: Product): Boolean {

        //db để write.
        val db = this.writableDatabase

        //Get values from course.
        val contentValues = ContentValues().apply {

            put(COLUMN_NAME, product.name_product)

            put(COLUMN_DESCRIPTION, product.desc_product)

            put(COLUMN_PRICE, product.price_product)
        }

        //Insert data (contentValues) to table.
        val result = db.insert(TABLE_PRODUCTS, null, contentValues)

        db.close()

        return result != -1L //Returns true if insert was successful.
    }

    fun updateCourse(product: Product): Boolean {

        val db = this.writableDatabase

        //Get values from course.
        val contentValues = ContentValues().apply {

            put(COLUMN_NAME, product.name_product)

            put(COLUMN_DESCRIPTION, product.desc_product)

            put(COLUMN_PRICE, product.price_product)
        }

        //Update course to database.
        val result = db.update(TABLE_PRODUCTS, contentValues,"${COLUMN_ID} = ?", arrayOf(product.id_product.toString()))

        db.close()

        return result > 0 //Returns true if result > 0.
    }

    fun deleteCourse(id_product: Int): Boolean {

        val db = this.writableDatabase

        //Delete course.
        val result = db.delete(TABLE_PRODUCTS, "${COLUMN_ID} = ?", arrayOf(id_product.toString()))

        db.close()

        return result > 0 //Returns true if result > 0
    }
}