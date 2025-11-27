package com.example.oop.data.api

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

object FirebaseDataSource {
    val db: FirebaseFirestore by lazy { Firebase.firestore }
}