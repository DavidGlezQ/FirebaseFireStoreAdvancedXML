package com.david.glez.firestoreadvancedxml.data.network

import android.net.Uri
import com.david.glez.firestoreadvancedxml.data.response.ProductResponse
import com.david.glez.firestoreadvancedxml.data.response.TopProductsResponse
import com.david.glez.firestoreadvancedxml.domain.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.storageMetadata
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import kotlin.coroutines.resume

class FirebaseDataBaseService @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {

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

    suspend fun uploadAndDownloadImage(uri: Uri): String {
        return suspendCancellableCoroutine { suspendCancellableCoroutine ->
            val reference = storage.reference.child("downloads/${uri.lastPathSegment}")
            reference.putFile(uri, createMetaData())
                .addOnFailureListener {
                    suspendCancellableCoroutine.resume("")
                }
                .addOnSuccessListener {
                    downloadImage(it, suspendCancellableCoroutine)
                }
        }
    }

    private fun downloadImage(
        uploadTask: UploadTask.TaskSnapshot,
        suspendCancellableCoroutine: CancellableContinuation<String>
    ) {
        uploadTask.storage.downloadUrl.addOnSuccessListener {
            suspendCancellableCoroutine.resume(it.toString())
        }.addOnFailureListener {
            suspendCancellableCoroutine.resume("")
        }
    }

    private fun createMetaData(): StorageMetadata {
        return storageMetadata {
            contentType = "image/jpeg"
            setCustomMetadata("date", Date().time.toString())
        }
    }

    suspend fun uploadNewProduct(
        name: String,
        description: String,
        price: String,
        imageURL: String
    ): Boolean {
        val id = generateProductId()
        val product = hashMapOf(
            "id" to id,
            "name" to name,
            "description" to description,
            "price" to price,
            "image" to imageURL
        )
        return suspendCancellableCoroutine { suspendCancellableCoroutine ->
            firestore.collection(PRODUCTS_PATH).document(id).set(product).addOnSuccessListener {
                suspendCancellableCoroutine.resume(true)
            }.addOnFailureListener {
                suspendCancellableCoroutine.resume(false)
            }
        }
    }

    private fun generateProductId(): String {
        return Date().time.toString()
    }
}