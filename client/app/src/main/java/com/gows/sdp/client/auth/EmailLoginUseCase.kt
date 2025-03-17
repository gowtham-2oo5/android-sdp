package com.gows.sdp.client.auth

import com.gows.sdp.client.data.repo.AuthRepo

class LoginWithEmailUseCase(private val authRepository: com.gows.sdp.client.data.repo.AuthRepo) {
    suspend operator fun invoke(email: String, pass: String): Boolean {
        return authRepository.signInWithEmail(email, pass)
    }
}