package com.ksas.maintac.repository

import com.google.firebase.auth.FirebaseUser
import com.ksas.maintac.response.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

typealias SignUpResponse = Response<Boolean>
typealias SignInResponse = Response<Boolean>
typealias AuthStateResponse = StateFlow<Boolean>

interface AuthRepository {

    val currentUser: FirebaseUser?

    suspend fun firebaseSignupWithEmailAndPassword(email: String, password: String): Flow<Response<String>>

    suspend fun firebaseSignInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Response<String>>

    fun signOut()

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
}