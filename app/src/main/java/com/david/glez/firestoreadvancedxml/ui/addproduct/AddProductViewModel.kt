package com.david.glez.firestoreadvancedxml.ui.addproduct

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(AddProductsUIState())
    val uiState: StateFlow<AddProductsUIState> = _uiState
}

data class AddProductsUIState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val imageURL: String = "",
    val isLoading: Boolean = false
)