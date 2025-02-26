package com.example.shoppingapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val firebaseAuth: FirebaseAuth,
    ) : AuthRepository {
        override val currentUser: FirebaseUser?
            get() = firebaseAuth.currentUser

        override suspend fun signUp(
            email: String,
            password: String,
        ): Result<FirebaseUser> =
            try {
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                Result.success(result.user!!)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun signIn(
            email: String,
            password: String,
        ): Result<FirebaseUser> =
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                Result.success(result.user!!)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override fun signOut() {
            firebaseAuth.signOut()
        }
    }
