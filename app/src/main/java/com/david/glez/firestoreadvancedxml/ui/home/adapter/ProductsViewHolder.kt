package com.david.glez.firestoreadvancedxml.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.david.glez.firestoreadvancedxml.databinding.ItemProductBinding
import com.david.glez.firestoreadvancedxml.domain.model.Product

class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemProductBinding.bind(view)

    fun render(product: Product) {
        binding.apply {
            Glide.with(ivProduct.context).load(product.imageURL).into(ivProduct)
            tvTitle.text = product.title
            tvDescription.text = product.description
            tvPrice.text = "$${product.price}"

        }
    }
}