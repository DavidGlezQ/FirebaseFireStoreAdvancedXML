package com.david.glez.firestoreadvancedxml.data.response

import com.david.glez.firestoreadvancedxml.domain.model.Product

data class ProductResponse(
    val id: String = "",
    val imageURL: String = "",
    val title: String = "",
    val description: String = "",
    val price: String = ""
) {
    fun toDomain(): Product {
        return Product(
            id = id,
            imageURL = imageURL,
            title = title,
            description = description,
            price = price
        )
    }
}
