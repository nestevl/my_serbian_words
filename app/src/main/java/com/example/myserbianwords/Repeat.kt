package com.example.myserbianwords

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.compose.AsyncImage
import com.example.myserbianwords.data.Lesson
import com.example.myserbianwords.data.Word
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream

class Repeat : AppCompatActivity() {
    var i = 1;
    private val lesson: Lesson by lazy {
        intent?.getSerializableExtra(lesson_id) as Lesson
    }
    private val value: String by lazy { intent.getStringExtra(action_type) as String }

    val list =
        mutableStateOf(emptyList<Word>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var k = " ${lesson.title}"
        readData(object : MyCallback {
            override fun onCallback(value: List<Word>) {
                Log.d("TAG", list.value.toString())

            }
        })

    }


    companion object {
        private const val lesson_id = "lesson_id"
        private const val action_type = "type_of_action"
        fun newIntant(context: Context, lesson: Lesson, action: String) =
            Intent(context, Repeat::class.java).apply {
                putExtra(lesson_id, lesson)
                putExtra(action_type, action)
            }

    }

    fun ProfileScr(word: Word) {
        setContentView(R.layout.activity_repeat)
        val textView = findViewById<TextView>(R.id.rusWord)
        val textView2 = findViewById<TextView>(R.id.serb_slovo)
        val buttonCheak = findViewById<Button>(R.id.b_cheak).isInvisible
        val image =findViewById<ImageView>(R.id.imageView)
        val buttonn_next=findViewById<Button>(R.id.next)
        val button_pr =findViewById<Button>(R.id.previos)
        if (value.equals("learn")) {
          //  buttonCheak.isGone
            textView.isVisible
            textView.text = word.rus_Translate
            val l=word.rus_Translate
        }
        textView2.text = word.serb_Word


    }
    fun set_next(){

    }
//    private fun bitmapToByteArray(context: Context): ByteArray {
//        val bitmap = BitmapFactory.decodeResource(context.resources,)
//        val baos = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
//        return baos.toByteArray()
//    }

    fun readData(myCallback: MyCallback) {
        val list = ArrayList<Word>()
        Firebase.firestore.collection("Lekcija 4 Moja porodica").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val word = document.toObject(Word::class.java)
                        list.add(word)
                        if (i == 1) {
                            val l=word.rus_Translate
                            ProfileScr(word = word)
                            i++;
                        }
                    }
                    myCallback.onCallback(list)

                }


            }
    }
}




