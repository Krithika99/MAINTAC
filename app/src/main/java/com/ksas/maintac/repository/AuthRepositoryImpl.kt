package com.ksas.maintac.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.ksas.maintac.response.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl : AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override val currentUser: FirebaseUser? = auth.currentUser

    override suspend fun firebaseSignupWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Response<String>> = callbackFlow {
        trySend(Response.Loading)
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Response.Success("User signed up successfully!!!"))
                    Log.d("repo", "current user id: ${auth.currentUser?.uid}")
                }
            }.addOnFailureListener {
                trySend(Response.Failure(it))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }

        awaitClose {
            close()
        }
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Response<String>> = callbackFlow {
        trySend(Response.Loading)
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Response.Success("User Signed in successfully!!"))
                    Log.d("repo", "current user id: ${auth.currentUser?.uid}")
                }
            }.addOnFailureListener {
                Log.e("Repo", it.message!!)
                trySend(Response.Failure(it))
            }
        } catch (e: java.lang.Exception) {
            Response.Failure(e)
        }

        awaitClose {
            close()
        }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = AuthStateListener {
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)
}