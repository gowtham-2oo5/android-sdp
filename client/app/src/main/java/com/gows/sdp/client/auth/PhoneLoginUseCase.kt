package com.gows.sdp.client.auth

import android.app.Activity
import com.gows.sdp.client.data.repo.AuthRepo

class PhoneLoginUseCase(private val authRepo: com.gows.sdp.client.data.repo.AuthRepo) {

    fun sendVerificationCode(
        phone: String,
        activity: Activity,
        callback: (Boolean, String?) -> Unit
    ) {
        authRepo.sendPhoneVerification(phone, activity, callback)
    }

    fun verifyCode(code: String, callback: (Boolean, String?) -> Unit) {
        authRepo.verifyPhoneCode(code, callback)
    }
}
