package com.example.bai_tap_tuan_8.lab_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bai_tap_tuan_8.R

class ProductAdapter(private var list: List<Product>, private var listener: OnItemClickListener): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface OnItemClickListener {

        fun onItemClick(product: Product)
    }

    inner class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        //bind được gọi -> mỗi item sẽ được set xử lý event.
        //object lắng nghe event gọi onItemClick (override bên MainActivity).
        fun bind(product: Product){

            itemView.setOnClickListener{

                listener.onItemClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.itemView.apply {

            val name_product = findViewById<TextView>(R.id.textViewProductName)
            name_product.text = list[position].name_product

            val name_desc = findViewById<TextView>(R.id.textViewProductDescription)
            name_desc.text = list[position].desc_product

            val name_price = findViewById<TextView>(R.id.textViewProductPrice)
            name_price.text = list[position].price_product.toString()
        }

        //Lấy list thứ i truyền vào bind.
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {

        return list.size
    }

    //Nếu danh sách sản phẩm có thể thay đổi (thêm, xóa, cập nhật), thì ta nên thêm một hàm cập nhật danh sách mà không cần tạo adapter mới.
    //Dễ dàng cập nhật RecyclerView khi dữ liệu thay đổi.
    //Tránh tạo lại adapter không cần thiết.
    fun updateList(newList: List<Product>) {

        list = newList

        notifyDataSetChanged()
    }

}