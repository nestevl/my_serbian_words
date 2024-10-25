package com.example.myserbianwords

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myserbianwords.data.Lesson
import com.example.myserbianwords.data.Word
import com.example.myserbianwords.databinding.LessonsListBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun LessonsListItem(lesson:Lesson,navigateToLesson:(Lesson)->Unit) {
    val fs = Firebase.firestore
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp,)
            .fillMaxWidth(),


        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {


        Row(
            Modifier.clickable { navigateToLesson(lesson)

            }
        ) {

            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),

                )
            {
                Text(text = lesson.title,
                    Modifier.fillMaxWidth().wrapContentWidth().padding(15.dp)


                )

            }
        }

    }
}

@Composable
fun OnclickItem(lesson: String):String{
return lesson
}
