package com.gows.sdp.client.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.gows.sdp.client.data.repo.AuthRepo

class LoginWithEmailUseCase(private val authRepository: com.gows.sdp.client.data.repo.AuthRepo) {
    suspend operator fun invoke(email: String, pass: String): AuthResult? {
        return authRepository.signInWithEmail(email, pass)
    }
}