package com.example.myserbianwords

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.myserbianwords.data.Lesson

class ChooseLesson : ComponentActivity() {
    var lesson = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var bundle: Bundle? = intent.extras
        var message = bundle!!.getString("action") // 1
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        setContent {
            setContent {
                MyApp() {
                 val value: String = message.toString()
//                    val explicitIntent = Intent(this, RepeatWords::class.java)
//                    explicitIntent.putExtra("action", value)
//                    explicitIntent.putExtra("lesson", it)
//
//                    /// explicitIntent.putExtra("action", value)
//                    // explicitIntent.putExtras()
//                    startActivity(explicitIntent)
                    startActivity(RepeatCompose.newIntant(this, it,value))
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

















