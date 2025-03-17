package com.gows.sdp.client.auth

import com.google.firebase.auth.FirebaseUser
import com.gows.sdp.client.data.repo.AuthRepo

class GoogleLoginUseCase(private val authRepo: AuthRepo) {
    fun executeGoogleLogin(idToken: String, onResult: (Result<FirebaseUser>) -> Unit) {
        authRepo.loginWithGoogle(idToken, onResult)
    }
}