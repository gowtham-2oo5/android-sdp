package com.gows.sdp.client.data.repo

import android.app.Activity
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.gows.sdp.client.data.source.FirebaseRemoteAuthDataSource

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

    fun loginWithGoogle(idToken: String, onResult: (Result<FirebaseUser>) -> Unit) {
        remoteAuthDataSource.signInWithGoogle(idToken, onResult)
    }

}