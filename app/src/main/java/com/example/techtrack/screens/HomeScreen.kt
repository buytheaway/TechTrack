package com.example.techtrack.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.techtrack.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeScreen(navController: NavController) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(uid) {
        uid?.let {
            FirebaseFirestore.getInstance().collection("users")
                .document(it).get()
                .addOnSuccessListener { snapshot ->
                    user = snapshot.toObject(User::class.java)
                }
        }
    }

    if (user == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("Привет, ${user?.name}!", style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(24.dp))

            if (user?.role == "employee") {
                Button(onClick = { navController.navigate("create_request") }) {
                    Text("Создать заявку")
                }
                Spacer(Modifier.height(12.dp))
                Button(onClick = { navController.navigate("request_detail/all") }) {
                    Text("Мои заявки")
                }
            } else if (user?.role == "asu") {
                Button(onClick = { navController.navigate("request_detail/all") }) {
                    Text("Обработка заявок")
                }
            }

            Spacer(Modifier.height(24.dp))
            Button(onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            }) {
                Text("Выйти")
            }
        }
    }
}
