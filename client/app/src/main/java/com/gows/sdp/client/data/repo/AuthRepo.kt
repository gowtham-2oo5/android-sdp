package com.gows.sdp.client.data.repo

import android.app.Activity
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.gows.sdp.client.data.source.FirebaseRemoteAuthDataSource
import kotlinx.coroutines.tasks.await

class AuthRepo(private val remoteAuthDataSource: FirebaseRemoteAuthDataSource) {

    suspend fun signInWithEmail(email: String, pass: String): AuthResult? {
        return remoteAuthDataSource.signInWithEmail(email, pass)
    }

    suspend fun createAccountWithEmail(email: String, pass: String): Boolean {
        return remoteAuthDataSource.createAccountWithEmail(email, pass)
    }

    fun sendPhoneVerification(
        phone: String,
        activity: Activity,
        callback: (Boolean, String?) -> Unit
    ) {
        remoteAuthDataSource.sendVerificationCode(phone, activity, callback)
    }

    fun verifyPhoneCode(code: String, callback: (Boolean, String?) -> Unit) {
        remoteAuthDataSource.verifyCode(code, callback)
    }

    suspend fun loginWithGoogle(idToken: String): Result<FirebaseUser> {
        return try {
            val authResult = remoteAuthDataSource.signInWithGoogle(idToken).await() // Ensure this returns a Task<AuthResult>
            authResult.user?.let {
                Result.success(it)
            } ?: Result.failure(Exception("User is null"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
