package com.example.techtrack.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.techtrack.models.User

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Регистрация", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Имя") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Пароль") })

        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    val uid = authResult.user?.uid ?: return@addOnSuccessListener
                    val user = User(id = uid, name = name, email = email)
                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(uid)
                        .set(user)
                        .addOnSuccessListener { navController.navigate("home") }
                        .addOnFailureListener { error = it.message }
                }
                .addOnFailureListener { error = it.message }
        }) {
            Text("Зарегистрироваться")
        }

        TextButton(onClick = { navController.navigate("login") }) {
            Text("Уже есть аккаунт? Войти")
        }

        error?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}
