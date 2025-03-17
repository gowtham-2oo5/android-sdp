package com.gows.sdp.client.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gows.sdp.client.auth.RegisterUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val createAccountUseCase: com.gows.sdp.client.auth.RegisterUseCase) : ViewModel() {
    private val _createAccountResult = MutableSharedFlow<Boolean>()
    val createAccountResult = _createAccountResult.asSharedFlow()
    fun createAccountWithEmail(email: String, pass: String) {
        viewModelScope.launch {
            val result = createAccountUseCase(email, pass)
            _createAccountResult.emit(result)
        }
    }
}