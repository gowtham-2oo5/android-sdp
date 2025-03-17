package com.gows.sdp.client.ui.view_model

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
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

    fun signInWithEmail(email: String, pass: String) {
        viewModelScope.launch {
            try {
                val authResult = loginWithEmailUseCase.invoke(email, pass)
                val user = authResult?.user

                Log.d("LoginViewModel", "User: ${user.toString()}")
                if (user != null) {
                    _loginResult.postValue(Result.success(user))
                } else {
                    _loginResult.postValue(Result.failure(Exception("User is null")))
                }
            } catch (e: Exception) {
                _loginResult.postValue(Result.failure(e))
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
