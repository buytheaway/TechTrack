package com.example.techtrack.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.techtrack.models.Request
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RequestDetailScreen(navController: NavController, id: String) {
    var requests by remember { mutableStateOf(listOf<Request>()) }

    LaunchedEffect(true) {
        val db = FirebaseFirestore.getInstance()
        val query = if (id == "all") db.collection("requests")
        else db.collection("requests").whereEqualTo("userId", id)

        query.get().addOnSuccessListener { result ->
            val list = result.documents.mapNotNull { it.toObject(Request::class.java) }
            requests = list
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Список заявок", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(12.dp))

        requests.forEach { request ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { /* future details */ }
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(request.title, style = MaterialTheme.typography.titleLarge)
                    Text("Категория: ${request.category}")
                    Text("Срочность: ${request.urgency}")
                    Text("Статус: ${request.status}")
                    request.commentFromAsu?.let {
                        Text("Комментарий: $it", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
