package com.example.androidlesson23.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlesson23.models.responses.get.product.Product
import com.example.androidlesson23.source.api.Resource
import com.example.androidlesson23.source.api.repositories.CategoryRepository
import com.example.androidlesson23.source.api.repositories.ProductRepository
import com.example.androidlesson23.source.local.mapping.toProduct
import com.example.androidlesson23.source.local.mapping.toProductEntity
import com.example.androidlesson23.source.local.repositories.EntityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: CategoryRepository,
    private val repo2: ProductRepository,
    private val repo3: EntityRepository
) : ViewModel() {

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()


    private var originalCategoryList = listOf<String>()
    private var originalProductList = listOf<Product>()

    init {
        getAllCategory()
        getAllProduct()
    }


    fun getAllCategory() {
        loading.value = true
        viewModelScope.launch(Dispatchers.Main) {

            val response = repo.getAllCategory()
            when (response) {
                is Resource.Success -> {
                    loading.value = false
                    val itemresponse = response.data
                    if (itemresponse != null && itemresponse.isNotEmpty()) {
                        _categories.value = itemresponse.orEmpty()
                        originalCategoryList=itemresponse.orEmpty()
                    } else {
                        error.value = "No categories found"
                        _categories.value = emptyList()
                    }
                }
                is Resource.Error -> {
                    error.value = "Failed to fetch categories: ${response.message}"
                }
                else -> {
                    loading.value = false
                }
            }
        }
    }

    fun getAllProduct() {
        loading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            delay(100)
            val localProducts = repo3.getProductEntity()
            if (localProducts.isNotEmpty()) {
                _products.value = localProducts.map { it.toProduct() }
                originalProductList=localProducts.map { it.toProduct() }
                loading.value = false
            } else {
                fetchRemoteProducts()
            }
        }
    }

    private fun fetchRemoteProducts() {
        viewModelScope.launch(Dispatchers.Main) {
            delay(100)
            val response = repo2.getAllProduct()
            when (response) {
                is Resource.Success -> {
                    loading.value = false
                    val itemResponse = response.data
                    if (itemResponse != null && itemResponse != null) {
                        _products.value = itemResponse.orEmpty()
                        originalProductList=itemResponse.orEmpty()
                        itemResponse.forEach { product ->
                            withContext(Dispatchers.IO) {
                                repo3.addProductEntity(product.toProductEntity())
                            }
                        }
                    } else {
                        error.value = "No products found"
                        _products.value = emptyList()
                    }
                }
                is Resource.Error -> {
                    error.value = "Failed to fetch products: ${response.message}"
                }
                else -> {
                    loading.value = false
                }
            }
        }
    }


    fun getProductsByCategory(categoryName: String) {
        loading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            delay(100)
            val response = if (categoryName.isNotEmpty()) {
                repo2.getProductsbyCategory(categoryName)
            } else {
                repo2.getAllProduct()
            }

            when (response) {
                is Resource.Success -> {
                    loading.value = false
                    val itemResponse = response.data
                    if (itemResponse != null && itemResponse != null) {
                        _products.value = itemResponse.orEmpty()
                        originalProductList = itemResponse.orEmpty()
                    } else {
                        error.value = "No products found for this category"
                        _products.value = emptyList()
                    }
                }
                is Resource.Error -> {
                    loading.value = false
                    error.value = "Failed to fetch products: ${response.message}"
                }
                else -> {
                    loading.value = false
                }
            }
        }
    }

    fun searchProducts(query: String) {

        if (query.isBlank()) {
            _products.value = originalProductList

        }

        val filtered = originalProductList.filter { item ->
            item.title?.contains(query, ignoreCase = true) ?: false
        }
        _products.value = filtered

    }


}