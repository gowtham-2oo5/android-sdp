package com.gows.sdp.client.ui.view_model


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gows.sdp.client.auth.GoogleLoginUseCase
import com.gows.sdp.client.auth.LoginWithEmailUseCase
import com.gows.sdp.client.auth.PhoneLoginUseCase

class LoginViewModelFactory(
    private val loginWithEmailUseCase: LoginWithEmailUseCase? = null,
    private val phoneLoginUseCase: PhoneLoginUseCase? = null,
    private val googleLoginUseCase: GoogleLoginUseCase? = null
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginWithEmailUseCase ?: throw IllegalArgumentException("Email use case required"),
                phoneLoginUseCase ?: throw IllegalArgumentException("Phone use case required"),
                googleLoginUseCase ?: throw IllegalArgumentException("Google use case required")
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
