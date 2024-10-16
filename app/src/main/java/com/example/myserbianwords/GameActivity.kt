package com.example.myserbianwords

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myserbianwords.RepeatCompose.Companion
import com.example.myserbianwords.data.Lesson
import com.example.myserbianwords.data.Word
import com.example.myserbianwords.ui.theme.MySerbianWordsTheme
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.random.Random


class GameActivity : ComponentActivity() {
    private val lesson: Lesson by lazy {
        intent?.getSerializableExtra(lesson_id) as Lesson
    }
    var ready=false;
    var listOfWord = ArrayList<Word>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FourProfiles()
        }
    }

    companion object {
        private const val lesson_id = "lesson_id"
        fun newIntant(context: Context, lesson: Lesson) =
            Intent(context, GameActivity::class.java).apply {
                putExtra(lesson_id, lesson)
            }

    }

    @Composable
    @Preview
    fun FourProfiles() {
        val fs = Firebase.firestore
        val list = remember {
            mutableStateOf(emptyList<Word>())
        }
        val coroutineScope = rememberCoroutineScope()

        val ready = remember { mutableStateOf(false) }
        fs.collection(lesson.title).get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    list.value = task.result.toObjects(Word::class.java)
                    ready.value = true


                }
            }

//
//        if (ready.value) {

            Column(
                modifier = Modifier.fillMaxSize(),
                //verticalArrangement = Arrangement.SpaceBetween
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
               // RandomPicTure(list2 = list.value)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {

                    var p = 0
                    items(list.value) { word ->
                        listOfWord.add(word);
                        val k = word.serb_Word
                        p++
                        if (p % 4 == 0) {
                            RandomPicTure(listOfWord)
                            listOfWord.clear()
                        }
                    }
                }

            }
        }

   // }

    @Composable
    fun RandomPicTure(list2: List<Word>): Unit {
        val randomWord = list2.get(Random.nextInt(0, 3)).serb_Word
        val firs_rs = list2.get(0).serb_Word
        var p = 0
        while (p <= list2.size) {
            if (p % 4 == 0) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                        .border(width = 2.dp, color = Color.Black)
                        .shadow(3.dp),

                    )
                {
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Выбери правильную картинку: ",
                        style = LocalTextStyle.current.copy(fontSize = 25.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth()


                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = randomWord,
                        style = LocalTextStyle.current.copy(fontSize = 30.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth()

                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    )
                    {

                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier
                                .height(150.dp)
                                .width(100.dp)
                                .clickable {
                                    if (list2
                                            .get(p - 2)
                                            .equals(randomWord)
                                    ) {
                                        Toast.makeText(
                                            this@GameActivity,
                                            "Верно",
                                            Toast.LENGTH_SHORT
                                        )

                                    } else {
                                        Toast.makeText(
                                            this@GameActivity,
                                            "Не верно",
                                            Toast.LENGTH_SHORT
                                        )
                                    }

                                }

                        )
                        {
                            AsyncImage(
                                model = list2.get(p - 2).imageURl,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier
                                .height(250.dp)
                                .width(250.dp)
                                .clickable {
                                    if (list2.get(p - 1).serb_Word.equals(randomWord)) {
                                        Toast.makeText(
                                            this@GameActivity,
                                            "Верно",
                                            Toast.LENGTH_SHORT
                                        )

                                    } else {
                                        Toast.makeText(
                                            this@GameActivity,
                                            "Не верно",
                                            Toast.LENGTH_SHORT
                                        )
                                    }

                                }
                        ) {
                            AsyncImage(
                                model = list2.get(p - 1).imageURl,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)

                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    )
                    {
                        Box(
                            modifier = Modifier
                                .height(250.dp)
                                .width(250.dp)
                                .clickable {
                                    if (list2.get(p).serb_Word.equals(randomWord)) {
                                        Toast.makeText(
                                            this@GameActivity,
                                            "Верно",
                                            Toast.LENGTH_SHORT
                                        )

                                    } else {
                                        Toast.makeText(
                                            this@GameActivity,
                                            "Не верно",
                                            Toast.LENGTH_SHORT
                                        )
                                    }

                                }
                        ) {
                            AsyncImage(
                                model = list2.get(p).imageURl,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(200.dp)
                                    .fillMaxWidth()
                                    .padding(10.dp)

                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier
                                .height(250.dp)
                                .width(250.dp)
                                .clickable {
                                    if (list2.get(p - 3).serb_Word.equals(randomWord)) {
                                        Toast.makeText(
                                            this@GameActivity,
                                            "Верно",
                                            Toast.LENGTH_SHORT
                                        )

                                    } else {
                                        Toast.makeText(
                                            this@GameActivity,
                                            "Не верно",
                                            Toast.LENGTH_SHORT
                                        )
                                    }

                                }
                        ) {
                            AsyncImage(
                                model = list2.get(p - 3).imageURl,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(200.dp)
                                    .fillMaxWidth()
                                    .padding(10.dp)

                            )
                        }


                    }


                }
                p++;


            }



        }

        suspend fun doWork() {
            println("doWork starts")
            delay(3000) // симулируем долгую работу с помощью задержки в 5 секунд
            println("doWork ends")
        }

        @Composable
        fun GridItem(title: String, modifier: Modifier = Modifier) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .wrapContentWidth(),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}





