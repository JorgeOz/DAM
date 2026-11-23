package com.example.ud5_ejemplo3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ud5_ejemplo3.ui.Ud5Ejemplo3App
import com.example.ud5_ejemplo3.ui.theme.Ud5_Ejemplo3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ud5_Ejemplo3Theme {
                Ud5Ejemplo3App()
            }
        }
    }
}