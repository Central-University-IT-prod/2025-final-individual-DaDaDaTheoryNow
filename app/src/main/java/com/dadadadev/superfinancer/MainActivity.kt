package com.dadadadev.superfinancer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dadadadev.designsystem.theme.SuperFinancerTheme
import com.dadadadev.superfinancer.feature.app.AppScreen
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            KoinContext {
                SuperFinancerTheme {
                    AppScreen()
                }
            }
        }
    }
}