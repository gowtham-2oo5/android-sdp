package com.gows.sdp.client.data.source


import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class FirebaseRemoteAuthDataSource {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    private var verificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    suspend fun signInWithEmail(email: String, pass: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, pass).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun createAccountWithEmail(email: String, pass: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, pass).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity,
        callback: (Boolean, String?) -> Unit
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    callback(true, null) // Auto-verification success
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    callback(false, e.message)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    this@FirebaseRemoteAuthDataSource.verificationId = verificationId
                    this@FirebaseRemoteAuthDataSource.resendToken = token
                    callback(true, "OTP Sent Successfully")
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    fun verifyCode(code: String, callback: (Boolean, String?) -> Unit) {
        val credential = verificationId?.let { PhoneAuthProvider.getCredential(it, code) }
        if (credential != null) {
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "Phone Auth Success")
                } else {
                    callback(false, task.exception?.message)
                }
            }
        } else {
            callback(false, "Invalid Verification ID")
        }
    }

    fun saveUserToFirestore(uid: String, name: String, email: String, phone: String, gender: String) {
        val user = hashMapOf(
            "uid" to uid,
            "name" to name,
            "email" to email,
            "phone" to phone,
            "gender" to gender
        )

        FirebaseFirestore.getInstance().collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener { Log.d("Firestore", "User saved successfully") }
            .addOnFailureListener { Log.e("Firestore", "Error saving user: ${it.message}") }
    }

    fun signInWithGoogle(idToken: String, onResult: (Result<FirebaseUser>) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(Result.success(auth.currentUser!!))
                } else {
                    onResult(Result.failure(task.exception ?: Exception("Google Sign-In failed")))
                }
            }
    }


}