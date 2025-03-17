package com.gows.sdp.client.auth

import com.google.firebase.auth.FirebaseUser
import com.gows.sdp.client.data.repo.AuthRepo

class GoogleLoginUseCase(private val authRepo: AuthRepo) {
    suspend fun executeGoogleLogin(idToken: String): Result<FirebaseUser> {
        return authRepo.loginWithGoogle(idToken)
    }
}
