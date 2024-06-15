package com.example.androidlesson23.source.api.repositories

import android.util.Log
import com.example.androidlesson23.source.api.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) {

    suspend fun loginUser(email: String, password: String): Resource<AuthResult> {
        return safeFirebaseAuthRequest {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
        }
    }

    suspend fun registerUser(email: String, password: String): Resource<AuthResult> {
        return safeFirebaseAuthRequest {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        }
    }

    suspend fun logoutUser(): Resource<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                firebaseAuth.signOut()
                Resource.Success(Unit)
            } catch (e: Exception) {
                Log.e("FirebaseAuthRepo", e.localizedMessage ?: "Unknown error")
                Resource.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    private suspend fun <T> safeFirebaseAuthRequest(request: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val result = request.invoke()
                Resource.Success(result)
            } catch (e: Exception) {
                Log.e("FirebaseAuthRepo", e.localizedMessage ?: "Unknown error")
                Resource.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}