package com.example.myserbianwords

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

import com.example.myserbianwords.data.Lesson
import com.example.myserbianwords.data.Word
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import org.w3c.dom.Text


class RepeatCompose : ComponentActivity() {
    private val lesson: Lesson by lazy {
        intent?.getSerializableExtra(lesson_id) as Lesson
    }
    private val value1: String by lazy { intent.getStringExtra(action_type) as String }

    //    val list =
//        mutableStateOf(emptyList<Word>())
    val index = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var k = " ${lesson.title}"
//        readData(object : MyCallback {
//            override fun onCallback(value: List<Word>) {
//                Log.d("CallBack", list.value.toString())
        setContent {

            ProfileScreen1()
        }

    }


    @Composable
    @Preview
    fun ProfileScreen1() {
        val fs = Firebase.firestore
        val list = remember {
            mutableStateOf(emptyList<Word>())
        }
        fs.collection(lesson.title).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    list.value = task.result.toObjects(Word::class.java)
                }
            }
        val listState = rememberLazyListState()
        Column(
            modifier = Modifier.fillMaxSize(),
            //verticalArrangement = Arrangement.SpaceBetween
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .horizontalScroll(rememberScrollState()),
                // state=listState

            ) {

                items(list.value) { word ->
                    //  if (word.index_word == 1) {
                    ProfileScreenLayout(word)
                }
//                    Spacer(modifier = Modifier.height(20.dp))
//                    Row(Modifier.height(IntrinsicSize.Min)) {
//                        Button(
//                            onClick = {},
//                            modifier = Modifier
//                                .fillMaxHeight()
//                                .padding(10.dp)
//                        ) {
//                            Text("Предыдущее \n слово")
//                        }
//
//                        Button(
//                            onClick = {},
//                            modifier = Modifier
//                                .fillMaxHeight()
//                                .padding(10.dp),
//                        ) {
//                            Text("Следующее \n слово")
//                        }
//                    }
//                }
//            }
            }
        }
    }

    @Composable
    fun ChooseWord(
        index: Int, list:
        MutableState<List<Word>>
    ) {

    }


    @Composable
    fun ProfileScreenLayout(
        word: Word, modifier: Modifier = Modifier
    ) {
        var showDetails by rememberSaveable { mutableStateOf(false) }
        var textOnButton: String by rememberSaveable {
            mutableStateOf("Проверить")
        }
        val buttonColor = remember { mutableStateOf(Color.Blue) }

//       val word = list.value.get(index)
//        if (word.index_word == index) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .border(width = 2.dp, color = Color.Black)
                .shadow(3.dp)


        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center


            )
            {
                AsyncImage(
                    model = word.imageURl,
                    contentDescription = null,
                    modifier = Modifier
                        .height(300.dp)
                        .width(300.dp)
                        .fillMaxWidth()
                        .padding(10.dp)

//                        contentScale = ContentScale.Crop,
                    // alignment = Alignment.CenterVertically,

                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = word.serb_Word,
                style = LocalTextStyle.current.copy(fontSize = 30.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(30.dp))
            if (value1.equals("repeat")) {
                Button(
                    onClick = {
                        textOnButton = word.rus_Translate
                        showDetails = true
                        buttonColor.value = Color.White

                    },
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth()
                        .align(Alignment.CenterHorizontally)


                ) {
                    Text(
                        text = textOnButton,
                        style = LocalTextStyle.current.copy(fontSize = 20.sp)
                    )

                }

            } else {
                Text(
                    text = word.rus_Translate,
                    style = LocalTextStyle.current.copy(fontSize = 30.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth()
                        .align(Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
//                Row(Modifier.height(IntrinsicSize.Min)) {
//                    Button(
//                        onClick = { },
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .padding(10.dp)
//                    ) {
//                        Text("Предыдущее \n слово")
//                    }
//
//                    Button(
//                        onClick = {
//                          ///  ProfileScreenLayout(word = word)
//                        },
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .padding(10.dp),
//                    ) {
//                        Text("Следующее \n слово")
//                    }
//                }
        }
    }


    companion object {
        private const val lesson_id = "lesson_id"
        private const val action_type = "type_of_action"
        fun newIntant(context: Context, lesson: Lesson, action: String) =
            Intent(context, RepeatCompose::class.java).apply {
                putExtra(lesson_id, lesson)
                putExtra(action_type, action)
            }

    }
}


//    fun readData(myCallback: MyCallback) {
//      val list = ArrayList<Word>()
//        val i=1;
//        Firebase.firestore.collection("Lekcija 4 Moja porodica").get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    for (document in task.result) {
//                        val word = document.toObject(Word::class.java)
//                        list.add(word)
//                        if(i==1){
//
//                        }
//                    }
//                    myCallback.onCallback(list)
//
//                }
//
//
//
//            }
//    }
//}


//    modifier : Modifier = Modifier ) ​​{
//    // Этот подход будет работать var count by запомнить { mutableStateOf( 0 ) }
//    // Column(        verticalArrangement = Arrangement.Center,
//    // horizontalAlignment = Alignment.CenterHorizontally,
//    // modifier = modifier.fillMaxSize()    )
//    // {        Text(text = " $count " , fontSize = 32.sp )
//    // Button(onClick = { count++ }) {
//    // Text(text = "Count Up" , fontSize = 32.sp )
//    // }    } } @Preview(showBackground = true)
//    // @Composable fun UiStateSamplePreview ()
//    // {    UiStateSample() }
//    }
