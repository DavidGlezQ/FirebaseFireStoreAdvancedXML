package com.david.glez.firestoreadvancedxml.ui.home

import androidx.lifecycle.ViewModel
import com.david.glez.firestoreadvancedxml.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private var _uiState: MutableStateFlow<HomeUIState> = MutableStateFlow(HomeUIState())
    val uiState: MutableStateFlow<HomeUIState> = _uiState


}

data class HomeUIState(
    val lastProduct: Product? = null,
    val products: List<Product> = emptyList(),
val topProducts: List<Product> = emptyList(),
)