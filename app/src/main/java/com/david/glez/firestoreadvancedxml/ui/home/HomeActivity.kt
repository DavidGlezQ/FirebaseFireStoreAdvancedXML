package com.david.glez.firestoreadvancedxml.ui.home

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.david.glez.firestoreadvancedxml.R
import com.david.glez.firestoreadvancedxml.databinding.ActivityHomeBinding
import com.david.glez.firestoreadvancedxml.domain.model.Product
import com.david.glez.firestoreadvancedxml.ui.addproduct.AddProductActivity
import com.david.glez.firestoreadvancedxml.ui.home.adapter.ProductsAdapter
import com.david.glez.firestoreadvancedxml.ui.home.adapter.SpacingDecorator
import com.david.glez.firestoreadvancedxml.ui.home.adapter.TopProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var topProductsAdapter: TopProductsAdapter

    private val addProductLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                homeViewModel.getData()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initShimmer()
        initListeners()
        initList()
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

    private fun initShimmer() {
        binding.lastProductShimmer.rootCardShimmer.startShimmer()
        binding.topProductShimmer.startShimmer()
    }

    private fun initList() {
        productsAdapter = ProductsAdapter()
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(SpacingDecorator(16))
            adapter = productsAdapter
        }

        topProductsAdapter = TopProductsAdapter()
        binding.rvTopProduct.apply {
            layoutManager = LinearLayoutManager(context, GridLayoutManager.HORIZONTAL, false)
            addItemDecoration(SpacingDecorator(16))
            adapter = topProductsAdapter
        }
    }

    private fun initListeners() {
        binding.toolbar.tvAddProduct.setOnClickListener {
            addProductLauncher.launch(AddProductActivity.create(this))
        }
    }

    private fun renderProducts(products: List<Product>) {
        if (products.isEmpty()) return
        productsAdapter.updateProducts(products = products)
        binding.topProductShimmer.isVisible = false
        binding.topProductShimmer.stopShimmer()

    }

    private fun renderTopProducts(topProducts: List<Product>) {
        topProductsAdapter.updateTopProducts(topProducts = topProducts)
    }

    private fun renderLastProduct(lastProduct: Product?) {
        if (lastProduct == null) return
        binding.lastProduct.tvTitle.text = lastProduct.title
        binding.lastProduct.tvDesc.text = lastProduct.description
        Glide.with(this).load(lastProduct.imageURL)
            .transform(CenterCrop(), RoundedCorners(16))
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.lastProduct.ivLastProduct)
        binding.lastProduct.rootCard.isVisible = true
        binding.lastProductShimmer.rootCardShimmer.stopShimmer()
    }
}