package com.example.myserbianwords

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.myserbianwords.data.Word
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
@Composable
fun dataDb(list: MutableState<List<Word>>, string: String) {
   val fs = Firebase.firestore

    val list = remember {
        mutableStateOf(emptyList<Word>())
    }
        fs.collection("Lekcija 4 Moja porodica").addSnapshotListener { snapShot, exception ->

            list.value = snapShot?.toObjects(Word::class.java) ?: emptyList()
        }

}