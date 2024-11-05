package com.david.glez.firestoreadvancedxml.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.david.glez.firestoreadvancedxml.R
import com.david.glez.firestoreadvancedxml.databinding.ActivityHomeBinding
import com.david.glez.firestoreadvancedxml.domain.model.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initListeners()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.uiState.collect { state ->
                    renderLastProduct(state.lastProduct)
                    renderTopProducts(state.topProducts)
                    renderProducts(state.products)
                }
            }
        }
    }

    private fun initListeners() {
        binding.toolbar.tvAddProduct.setOnClickListener {

        }
    }

    private fun renderProducts(products: List<Product>) {

    }

    private fun renderTopProducts(topProducts: List<Product>) {

    }

    private fun renderLastProduct(lastProduct: Product?) {
        if (lastProduct == null) return
        binding.lastProduct.tvTitle.text = lastProduct.title
        binding.lastProduct.tvDesc.text = lastProduct.description
        Glide.with(this).load(lastProduct.imageURL).placeholder(R.drawable.ic_placeholder)
            .into(binding.lastProduct.ivLastProduct)
    }
}