package com.david.glez.firestoreadvancedxml.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.glez.firestoreadvancedxml.data.network.FirebaseDataBaseService
import com.david.glez.firestoreadvancedxml.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: FirebaseDataBaseService) : ViewModel() {

    private var _uiState: MutableStateFlow<HomeUIState> = MutableStateFlow(HomeUIState())
    val uiState: MutableStateFlow<HomeUIState> = _uiState

    init {
        getData()
    }

    private fun getData() {
        getLastProduct()
    }

    private fun getLastProduct() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.getLastProduct()
            }
            //_uiState.value = _uiState.value.copy(lastProduct = response)
            _uiState.update { it.copy(lastProduct = response) }
        }
    }

}

data class HomeUIState(
    val lastProduct: Product? = null,
    val products: List<Product> = emptyList(),
    val topProducts: List<Product> = emptyList(),
)