package com.david.glez.firestoreadvancedxml.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.glez.firestoreadvancedxml.R
import com.david.glez.firestoreadvancedxml.domain.model.Product

class ProductsAdapter(private var products: List<Product> = emptyList()): RecyclerView.Adapter<ProductsViewHolder>() {

    fun updateProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductsViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.render(products[position])
    }
}