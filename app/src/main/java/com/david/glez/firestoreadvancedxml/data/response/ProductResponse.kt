package com.david.glez.firestoreadvancedxml.data.response

import com.david.glez.firestoreadvancedxml.domain.model.Product

data class ProductResponse(
    val id: String = "",
    val image: String = "",
    val name: String = "",
    val description: String = "",
    val price: String = ""
) {
    fun toDomain(): Product {
        return Product(
            id = id,
            imageURL = image,
            title = name,
            description = description,
            price = price
        )
    }
}
