package com.ksas.maintac.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ksas.maintac.repository.AuthRepository
import com.ksas.maintac.repository.AuthRepositoryImpl
import com.ksas.maintac.repository.SignInResponse
import com.ksas.maintac.repository.SignUpResponse
import com.ksas.maintac.response.Response
import kotlinx.coroutines.tasks.await
import com.ksas.maintac.response.Response.Failure
import com.ksas.maintac.response.Response.Success
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.Flow

class FirebaseAuthenticationViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val currentUser = mutableStateOf(auth.currentUser)
    private val authRepository = AuthRepositoryImpl()

    suspend fun signUp(email: String, password: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun signInUser(
        email: String,
        password: String
    ): kotlinx.coroutines.flow.Flow<Response<String>> {
        return authRepository.firebaseSignInWithEmailAndPassword(email, password)
    }

    suspend fun signUpUser(
        email: String,
        password: String
    ): kotlinx.coroutines.flow.Flow<Response<String>> {
        return authRepository.firebaseSignupWithEmailAndPassword(email, password)
    }

    fun signOut() {
        authRepository.signOut()
    }

}