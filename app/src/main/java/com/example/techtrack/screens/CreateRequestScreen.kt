package com.example.techtrack.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.techtrack.models.Request
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

@Composable
fun CreateRequestScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Интернет") }
    var urgency by remember { mutableStateOf("Обычная") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var uploading by remember { mutableStateOf(false) }

    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) photoUri = it
    }

    val categories = listOf("Интернет", "ПК", "Принтер", "Протяжка кабеля", "Софт", "Другое")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Новая заявка", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Заголовок") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Описание") })

        Spacer(Modifier.height(8.dp))
        DropdownMenuBox(items = categories, selectedItem = category, onItemSelected = { category = it }, label = "Категория")

        Spacer(Modifier.height(8.dp))
        DropdownMenuBox(items = listOf("Обычная", "Срочная", "Критичная"), selectedItem = urgency, onItemSelected = { urgency = it }, label = "Срочность")

        Spacer(Modifier.height(8.dp))
        Button(onClick = { imagePicker.launch("image/*") }) {
            Text("Прикрепить фото")
        }

        Spacer(Modifier.height(16.dp))
        Button(enabled = !uploading, onClick = {
            uploading = true
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@Button
            val request = Request(
                id = UUID.randomUUID().toString(),
                userId = uid,
                title = title,
                description = description,
                category = category,
                urgency = urgency
            )

            fun saveToFirestore(photoUrl: String? = null) {
                val finalRequest = request.copy(photoUrl = photoUrl)
                FirebaseFirestore.getInstance().collection("requests")
                    .document(finalRequest.id)
                    .set(finalRequest)
                    .addOnSuccessListener {
                        navController.popBackStack()
                    }
            }

            if (photoUri != null) {
                val ref = FirebaseStorage.getInstance().getReference("photos/${request.id}.jpg")
                ref.putFile(photoUri!!)
                    .continueWithTask { ref.downloadUrl }
                    .addOnSuccessListener { saveToFirestore(it.toString()) }
            } else {
                saveToFirestore()
            }
        }) {
            Text("Отправить заявку")
        }
    }
}
