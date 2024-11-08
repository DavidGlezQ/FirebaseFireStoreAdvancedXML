package com.david.glez.firestoreadvancedxml.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.david.glez.firestoreadvancedxml.databinding.ItemProductBinding
import com.david.glez.firestoreadvancedxml.domain.model.Product

class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemProductBinding.bind(view)

    fun render(product: Product) {
        binding.apply {
            Glide.with(ivProduct.context).load(product.imageURL)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(ivProduct)
            tvTitle.text = product.title
            tvDescription.text = product.description
            tvPrice.text = "$${product.price}"

        }
    }
}