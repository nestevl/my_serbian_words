package com.example.myserbianwords

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.inputmethodservice.Keyboard
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import coil.compose.AsyncImage
import com.example.myserbianwords.data.Word
import com.example.myserbianwords.ui.theme.MySerbianWordsTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(1000)
        installSplashScreen()



        setContent {
            val fs = Firebase.firestore
            val storage = Firebase.storage.reference.child("first_lesson")
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia()
            ) { uri ->
                if (uri == null) return@rememberLauncherForActivityResult

//                val task = storage.child("4_lesson").putBytes(
//                    bitmapToByteArray(this)
//                )
//                task.addOnSuccessListener { uploadTask ->
//                    uploadTask.metadata?.reference?.downloadUrl?.addOnCompleteListener { uriTask ->
//
//                        saveWord(fs, uriTask.result.toString())
                //  }
                // }
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
//        fs.collection("Lekcija 4 Moja porodica").addSnapshotListener { snapShot, exception ->
//
//            list.value = snapShot?.toObjects(Word::class.java) ?: emptyList()
//        }
//        fs.collection("Lekcija 4 Moja porodica")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "Error getting documents: ", exception)
//            }
//        fs.collection("Lekcija 4 Moja porodica").document("i9s9NfwDlMmGvX9MWSRx").get().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
////                list.value = task.result.toObjects(Word::class.java)
//                val k=task.result.toObject(Word::class.java)
//                val p= k?.serb_Word.toString()
//            }
//            else {
//                // Result is guaranteed to be non-null
//                task.getResult();
//            }


        val storage = Firebase.storage.reference.child("4_lesson")
        val context = LocalContext.current
        val task = storage.child("family.jpg").putBytes(
            bitmapToByteArray(context)
        )
        task.addOnSuccessListener { uploadTask ->
            uploadTask.metadata?.reference?.downloadUrl?.addOnCompleteListener { uriTask ->
                saveWord(fs, uriTask.result.toString())
            }

        }




        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center

        ) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(0.8f)
//            ) {
//                items(list.value) { word ->
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(10.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            AsyncImage(
//                                model = word.imageURl,
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .height(100.dp)
//                                    .width(100.dp)
//                            )
//                        }
//                        Text(
//                            text = word.serb_Word,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .wrapContentWidth()
//
//                        )
////                    Text(
////                        text = word.Rus_Translate,
////                        modifier = Modifier
////                            .fillMaxWidth()
////                            .wrapContentWidth()
////                    )


            Spacer(modifier = Modifier.height(1.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                onClick = {
                    val value: String = "repeat"
                    val explicitIntent = Intent(this@MainActivity, ChooseLesson::class.java)
                    explicitIntent.putExtra("action", value)
                    startActivity(explicitIntent)


                }) {
                Text(
                    text = "Повторить слова",

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                onClick = {
                    val value: String = "add"
                    val explicitIntent3 = Intent(this@MainActivity, ChooseLesson::class.java)
                    explicitIntent3.putExtra("action", value)
                    startActivity(explicitIntent3)


                }) {
                Text(
                    text = "Добавить слово",

                    )
            }
        }
    }


    private fun bitmapToByteArray(context: Context): ByteArray {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.babuishka)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        return baos.toByteArray()
    }

    private fun saveWord(fs: FirebaseFirestore, url: String) {
//    fs.collection("Lekcija 4 Moja porodica").document().set(
//        Word(
//            "4",
//            " baka",
//            "бабушка",
//
//            url,
//            1
//        )
//    )
//
//}
    }
}

//}
