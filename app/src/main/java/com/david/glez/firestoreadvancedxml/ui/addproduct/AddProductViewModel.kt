package com.david.glez.firestoreadvancedxml.ui.addproduct

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.glez.firestoreadvancedxml.data.network.FirebaseDataBaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val firebaseRepository: FirebaseDataBaseService) :
    ViewModel() {

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
        viewModelScope.launch {
            showLoading(true)
            val result = withContext(Dispatchers.IO) {
                firebaseRepository.uploadAndDownloadImage(uri = uri)
            }
            _uiState.update { it.copy(imageURL = result) }
            showLoading(false)
        }
    }

    private fun showLoading(show: Boolean) {
        _uiState.update { it.copy(isLoading = show) }
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