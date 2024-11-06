package com.david.glez.firestoreadvancedxml.ui.addproduct

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AddProductsUIState())
    val uiState: StateFlow<AddProductsUIState> = _uiState

    fun onNameChanged(name: CharSequence?) {
        _uiState.update { it.copy(name = name.toString()) }
    }

    fun onPriceChanged(price: CharSequence?) {
        _uiState.update { it.copy(price = price.toString()) }
    }

    fun onDescriptionChanged(description: CharSequence?) {
        _uiState.update { it.copy(description = description.toString()) }
    }

    fun onImageSelected(uri: Uri) {
        //val result =
    }
}

data class AddProductsUIState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val imageURL: String = "",
    val isLoading: Boolean = false
) {
    fun isValidProduct() = name.isNotBlank() && description.isNotBlank() && price.isNotBlank()
}