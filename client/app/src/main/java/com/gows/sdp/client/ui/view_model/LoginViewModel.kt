package com.gows.sdp.client.ui.view_model

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.gows.sdp.client.auth.GoogleLoginUseCase
import com.gows.sdp.client.auth.LoginWithEmailUseCase
import com.gows.sdp.client.auth.PhoneLoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginWithEmailUseCase: LoginWithEmailUseCase,
    private val phoneLoginUseCase: PhoneLoginUseCase,
    private val googleLoginUseCase: GoogleLoginUseCase
) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<FirebaseUser>>()
    val loginResult: LiveData<Result<FirebaseUser>> get() = _loginResult

    private val _authState = MutableLiveData<String>()
    val authState: LiveData<String> get() = _authState

    /** Email/Password Authentication */
    fun loginWithEmail(email: String, pass: String) {
        viewModelScope.launch {
            val result = loginWithEmailUseCase(email, pass)
            if (result) {
                _authState.postValue("Login Successful")
            } else {
                _authState.postValue("Login Failed")
            }
        }
    }

    /** Phone Authentication */
    fun sendCode(phone: String, activity: Activity) {
        phoneLoginUseCase.sendVerificationCode(phone, activity) { success, message ->
            _authState.postValue(if (success) "OTP Sent" else "Error: $message")
        }
    }

    fun verifyCode(code: String) {
        phoneLoginUseCase.verifyCode(code) { success, message ->
            _authState.postValue(if (success) "Login Successful" else "Error: $message")
        }
    }

    /** Google Authentication */
    fun signInWithGoogle(idToken: String) {
        googleLoginUseCase.executeGoogleLogin(idToken) { result ->
            _loginResult.postValue(result)
        }
    }
}
