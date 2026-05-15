package com.yielders.jatrenammapride

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.yielders.jatrenammapride.ui.JatreApp
import com.yielders.jatrenammapride.ui.JatreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            JatreTheme {
                JatreApp()
            }
        }
    }
}
