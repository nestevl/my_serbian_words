package com.example.myserbianwords

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.myserbianwords.data.Lesson
import com.example.myserbianwords.data.Word
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChooseLesson : ComponentActivity() {
    var lesson = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var bundle: Bundle? = intent.extras
        var message = bundle!!.getString("action") // 1
        var message_repeat = bundle!!.getString("actionTODO")
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        setContent {
            setContent {
                MyApp() {
                    val value_todo = message_repeat.toString();
                    if (value_todo != null && value_todo.equals("Game")) {

                        val list = ArrayList<Word>()

                        Firebase.firestore.collection(it.title).get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    list.addAll( task.result.toObjects(Word::class.java))
                                }}
                                startActivity(GameActivity.newIntant(this, it))

                    } else {
                        val value: String = message.toString()
                        startActivity(RepeatCompose.newIntant(this, it, value))
                    }
                }
            }
//            val fs = Firebase.firestore
//            val storage = Firebase.storage.reference.child("images")
//            val launcher = rememberLauncherForActivityResult(
//                contract = ActivityResultContracts.PickVisualMedia()
//            ) { uri ->
//                if (uri == null) return@rememberLauncherForActivityResult
//
//
//            }


        }
    }
}


@Composable
fun MyApp(navigateToLesson: (Lesson) -> Unit) {
    var text2 = ""
    Scaffold(

        content = {
            LessonContent(navigateToLesson = navigateToLesson)
        }

    )
}

















