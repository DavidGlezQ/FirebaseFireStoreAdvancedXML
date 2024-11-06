package com.david.glez.firestoreadvancedxml.ui.addproduct

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.david.glez.firestoreadvancedxml.databinding.ActivityAddProductBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@AndroidEntryPoint
class AddProductActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context) = Intent(context, AddProductActivity::class.java)
    }

    private lateinit var binding: ActivityAddProductBinding
    private val addProductViewModel: AddProductViewModel by viewModels()

    private lateinit var uri: Uri

    private val intentCameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if(it && uri.path?.isNotEmpty() == true) {
            addProductViewModel.onImageSelected(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initUIState()
    }

    private fun initListeners() {
        binding.etName.doOnTextChanged { text, _, _, _ ->
            addProductViewModel.onNameChanged(text)
        }

        binding.etDescription.doOnTextChanged { text, _, _, _ ->
            addProductViewModel.onDescriptionChanged(text)
        }

        binding.etPrice.doOnTextChanged { text, _, _, _ ->
            addProductViewModel.onPriceChanged(text)
        }

        binding.etImage.setOnClickListener {
            takePicture()
        }
    }

    private fun takePicture() {
        generateUri()
        intentCameraLauncher.launch(uri)
    }

    private fun generateUri() {
        uri = FileProvider.getUriForFile(
            Objects.requireNonNull(this),
            "com.david.glez.firestoreadvancedxml",
            createFile()
        )
    }

    private fun createFile(): File {
        val name: String = SimpleDateFormat("yyyyMMdd_hhmmss").format(Date()) + "image"
        return File.createTempFile(name, ".jpg", externalCacheDir)
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addProductViewModel.uiState.collect { state ->
                    binding.pbLoading.isVisible = state.isLoading
                    binding.btnAddProduct.isEnabled = state.isValidProduct()
                }
            }
        }
    }
}