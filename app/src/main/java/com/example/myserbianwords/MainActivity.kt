package com.example.myserbianwords

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.myserbianwords.data.Word
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    var n = 105
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  Thread.sleep(1000)
        installSplashScreen()



        setContent {
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia()
            ) { uri ->
                if (uri == null) return@rememberLauncherForActivityResult

            }
            MainScreen {
                launcher.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }

        }
    }


    @Composable
    fun MainScreen(onClick: () -> Unit) {
        val fs = Firebase.firestore

        val list = remember {
            mutableStateOf(emptyList<Word>())
        }
          //var openDialog = remember { mutableStateOf(false) }
        val storage = Firebase.storage.reference.child("8_lesson")
        val context = LocalContext.current

        val task = storage.child("pic "+n+".jpg").putBytes(
            bitmapToByteArray(context)
        )
        val showDialog = remember { mutableStateOf(false) }
        val openDialog = remember { mutableStateOf(false) }
        if (showDialog.value)
            addWord("", setShowDialog = {
                showDialog.value = it
            }) {
                Log.i("HomePage", "HomePage : $it")
            }
        task.addOnSuccessListener { uploadTask ->
            uploadTask.metadata?.reference?.downloadUrl?.addOnCompleteListener { uriTask ->
                saveWord(fs, uriTask.result.toString())
            }

        }
        val showAlertDialog = remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(if (showDialog.value == true) Modifier.blur(30.dp) else Modifier),
            verticalArrangement = Arrangement.Center


        ) {


            Spacer(modifier = Modifier.height(1.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                onClick = {
                    openDialog.value = true

//                    val value: String = "repeat"
//                    val explicitIntent = Intent(this@MainActivity, ChooseLesson::class.java)
//                    explicitIntent.putExtra("action", value)
//                    startActivity(explicitIntent)


                }) {
                Text(
                    text = "Повторить слова",

                    )
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = { openDialog.value = false },
                    title = { Text(text = "Повторить слова") },
                    text = { Text("Ты хочешь повторить в виде игры на угадывание или увидеть список всез слов?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                onclickRepeat("Game")
                                openDialog.value = false
                            },
                            border = BorderStroke(
                                1.dp,
                                androidx.compose.ui.graphics.Color.LightGray
                            )
                        ) {
                            Text("Игра", fontSize = 22.sp)

                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                onclickRepeat("All")
                                openDialog.value = false
                            },
                            border = BorderStroke(
                                1.dp,
                                androidx.compose.ui.graphics.Color.LightGray
                            )
                        ) {
                            Text("Все слова", fontSize = 22.sp)


                        }
                    },
                    containerColor = androidx.compose.ui.graphics.Color.DarkGray,
                    titleContentColor = LightGray,
                    textContentColor = LightGray,
                    iconContentColor = LightGray
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                onClick = {
                    val value: String = "learn"
                    val explicitIntent2 = Intent(this@MainActivity, ChooseLesson::class.java)
                    explicitIntent2.putExtra("action", value)
                    startActivity(explicitIntent2)
                }) {
                Text(
                    text = "Учить новые слова",

                    )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Button(
                { showDialog.value = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),

                ) {
                Text(
                    text = "Добавить слово",

                    )
            }
            if (showDialog.value) {
                showDialog.value = true
//

            }
        }
    }

    private fun bitmapToByteArray(context: Context): ByteArray {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.im96)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        return baos.toByteArray()
    }

    private fun saveWord(fs: FirebaseFirestore, url: String) {

    fs.collection("Lekcija 8 Stil života").document().set(
        Word(
            "8",
            "želeti(želim, i/e)",
                    "желать",

            url, n
        )
    )

    }

    private fun onclickRepeat(actionToDo: String) {
        val value: String = "repeat"
        val explicitIntent = Intent(this@MainActivity, ChooseLesson::class.java)
        explicitIntent.putExtra("action", value)
        explicitIntent.putExtra("actionTODO", actionToDo)
        startActivity(explicitIntent)
    }

}


//}
