package com.example.androidlesson23.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlesson23.models.responses.get.basket.Basket
import com.example.androidlesson23.models.responses.get.product.Product
import com.example.androidlesson23.source.api.Resource
import com.example.androidlesson23.source.api.repositories.ProductRepository
import com.example.androidlesson23.source.local.mapping.toBasketEntity
import com.example.androidlesson23.source.local.mapping.toProductEntity
import com.example.androidlesson23.source.local.repositories.EntityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repo: ProductRepository,     private val repo2: EntityRepository) : ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun getProductById(id: Int) {
        isLoading.value = true

        viewModelScope.launch(Dispatchers.Main) {
            val response = repo.getProductById(id.toInt())

            when (response) {
                is Resource.Success -> {
                    isLoading.value = false
                    val itemResponse = response.data

                    if (itemResponse != null) {
                        _product.value = itemResponse!!

                        withContext(Dispatchers.IO) {
                            val localBaskets =
                                repo2.getBasketEntity().filter { it.id == itemResponse.id }
                            if (localBaskets.isEmpty()) {
                                repo2.addbasketEntity(
                                    Basket(
                                        id = itemResponse.id,
                                        category = itemResponse.category,
                                        price = itemResponse.price,
                                        image = itemResponse.image,
                                        title = itemResponse.title
                                    ).toBasketEntity()
                                )
                            }
                        }

                    } else {
                        error.value = "No product found"
                    }
                }

                is Resource.Error -> {
                    isLoading.value = false
                    error.value = "Failed to fetch product: ${response.message}"
                }

                else -> {
                    isLoading.value = false
                }
            }
        }
    }
}


