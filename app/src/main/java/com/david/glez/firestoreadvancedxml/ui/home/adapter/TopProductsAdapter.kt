package com.david.glez.firestoreadvancedxml.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.glez.firestoreadvancedxml.R
import com.david.glez.firestoreadvancedxml.domain.model.Product

class TopProductsAdapter(private var topProducts: List<Product> = emptyList()) :
    RecyclerView.Adapter<TopProductsViewHolder>() {

    fun updateTopProducts(topProducts: List<Product>) {
        this.topProducts = topProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_top_product, parent, false)
        return TopProductsViewHolder(view)
    }

    override fun getItemCount() = topProducts.size

    override fun onBindViewHolder(holder: TopProductsViewHolder, position: Int) {
        holder.render(topProducts[position])
    }
}