package com.example.shoppingapp.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun signUp(
        email: String,
        password: String,
    ): Result<FirebaseUser>

    suspend fun signIn(
        email: String,
        password: String,
    ): Result<FirebaseUser>

    fun signOut()
}
