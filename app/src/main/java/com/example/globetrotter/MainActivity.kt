package com.example.globetrotter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.globetrotter.ui.GlobeTrotterScreen
import com.example.globetrotter.ui.theme.GlobeTrotterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GlobeTrotterTheme {
                GlobeTrotterScreen()
            }
        }
    }
}
