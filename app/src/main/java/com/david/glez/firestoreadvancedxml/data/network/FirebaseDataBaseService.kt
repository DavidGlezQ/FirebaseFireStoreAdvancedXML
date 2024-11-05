package com.david.glez.firestoreadvancedxml.data.network

import com.david.glez.firestoreadvancedxml.data.response.ProductResponse
import com.david.glez.firestoreadvancedxml.data.response.TopProductsResponse
import com.david.glez.firestoreadvancedxml.domain.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDataBaseService @Inject constructor(private val firestore: FirebaseFirestore) {

    companion object {
        const val PRODUCTS_PATH = "products"
        const val MANAGEMENT_PATH = "management"
        const val TOP_PRODUCTS_PATH = "top_products"
    }

    suspend fun getAllProducts(): List<Product> {
        return firestore.collection(PRODUCTS_PATH).get().await().map { product ->
            product.toObject(ProductResponse::class.java).toDomain()
        }
    }

    suspend fun getLastProduct(): Product? {
        return firestore.collection(PRODUCTS_PATH).orderBy("id", Query.Direction.DESCENDING)
            .limit(1).get().await().firstOrNull()?.toObject(ProductResponse::class.java)?.toDomain()
    }

    suspend fun getTopProducts(): List<String> {
        return firestore.collection(MANAGEMENT_PATH).document(TOP_PRODUCTS_PATH).get().await()
            .toObject(TopProductsResponse::class.java)?.ids.orEmpty()
    }
}