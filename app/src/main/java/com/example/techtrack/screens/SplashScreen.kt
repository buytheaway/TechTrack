package com.example.techtrack.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(true) {
        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }
            } else {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }, 1500)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("TechTrack", style = MaterialTheme.typography.headlineLarge)
    }
}
