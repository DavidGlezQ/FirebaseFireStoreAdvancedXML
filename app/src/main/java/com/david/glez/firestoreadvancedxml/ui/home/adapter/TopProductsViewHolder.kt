package com.david.glez.firestoreadvancedxml.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.david.glez.firestoreadvancedxml.databinding.ItemTopProductBinding
import com.david.glez.firestoreadvancedxml.domain.model.Product

class TopProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemTopProductBinding.bind(view)

    fun render(product: Product) {
        binding.apply {
            tvTopProductTitle.text = product.title
            Glide.with(ivTopProduct.context).load(product.imageURL)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(ivTopProduct)
        }
    }
}