package com.example.androidlesson23.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlesson23.models.responses.get.product.Product
import com.example.androidlesson23.source.api.Resource
import com.example.androidlesson23.source.api.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repo: ProductRepository) : ViewModel() {

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
                    loading.value = false
                    val recipeResponse = response.data
                    if (recipeResponse != null) {
                        _product.value = recipeResponse!!

                    } else {
                        error.value = "No recipes found"
                    }
                }
                is Resource.Error -> {
                    error.value = "Failed to fetch recipes: ${response.message}"
                }
                else -> {
                    loading.value = false
                }
            }




        }
    }


}