package com.gows.sdp.client.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gows.sdp.client.auth.RegisterUseCase

class RegisterViewModelFactory(private val createAccountUseCase: com.gows.sdp.client.auth.RegisterUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.gows.sdp.client.ui.view_model.RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return com.gows.sdp.client.ui.view_model.RegisterViewModel(createAccountUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}