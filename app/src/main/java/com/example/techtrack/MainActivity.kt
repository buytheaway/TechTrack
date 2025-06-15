package com.example.techtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import com.example.techtrack.navigation.NavGraph
import com.example.techtrack.ui.theme.TechTrackTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            TechTrackTheme {
                Surface {
                    NavGraph()
                }
            }
        }
    }
}
