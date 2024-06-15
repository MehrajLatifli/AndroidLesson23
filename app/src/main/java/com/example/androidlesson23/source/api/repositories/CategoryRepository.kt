package com.example.androidlesson23.source.api.repositories

import com.example.androidlesson23.models.responses.get.category.CategoryResponse
import com.example.androidlesson23.source.api.IApiManager
import com.example.androidlesson23.source.api.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class CategoryRepository  @Inject constructor(private val api: IApiManager) {


    suspend fun getAllCategory(): Resource<CategoryResponse> {
        return safeApiRequest {
            api.getAllCategory()
        }
    }





    suspend fun <T> safeApiRequest(request: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {

                val response = request.invoke()

                if (response.isSuccessful) {
                    Resource.Success(response.body())
                } else {
                    Resource.Error(response.message())
                }
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }
        }
    }
}