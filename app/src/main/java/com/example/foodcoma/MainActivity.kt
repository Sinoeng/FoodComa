package com.example.foodcoma

import android.app.KeyguardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.foodcoma.ui.theme.FoodComaTheme
import kotlinx.coroutines.flow.MutableStateFlow

private const val TAG = "MainActivity"
private var access = MutableStateFlow(false)

class MainActivity : FragmentActivity() {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!(keyguardManager.isDeviceSecure)) {
            access.value = true
        }
        setupAuthenticationPrompt()

        setContent {
            FoodComaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    if (access.collectAsState().value) {
                        FoodComaApp(calculateWindowSizeClass(this).widthSizeClass)
                    } else {
                        VerificationScreen(
                            verify = { biometricPrompt.authenticate(promptInfo) }
                        )
                    }
                }
            }
        }
    }

    private fun setupAuthenticationPrompt() {
        biometricPrompt = BiometricPrompt(
            this,
            ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    Log.d(TAG, "Authentication error: $errString")
                    access.value = false
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    Log.d(TAG, "Authentication succeeded")
                    access.value = true
                }

                override fun onAuthenticationFailed() {
                    Log.d(TAG, "Authentication failed")
                    access.value = false
                }
            }
        )

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authenticate using your PIN or fingerprint")
            .setSubtitle("This operation requires authentication")
            .setNegativeButtonText("Cancel")
            .setConfirmationRequired(false)
            .build()
    }
}
