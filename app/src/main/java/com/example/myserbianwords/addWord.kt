package com.example.myserbianwords

import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myserbianwords.data.Word
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addWord(value: String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {
    val txtFieldError = remember { mutableStateOf("") }
    val txtField_serb = remember { mutableStateOf("") }
    val txtField_ru = remember { mutableStateOf("") }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Добавить слово",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "",
                            tint = colorResource(android.R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) }
                        )
                    }

//


                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = if (txtField_serb.value.isEmpty()) R.color.holo_green_light else R.color.holo_red_dark)
                                ),
                                shape = RoundedCornerShape(50),
                            ),
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.Black,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,

                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent

                        ),
                        placeholder = { Text(text = "Введи слово на сербском") },
                        value = txtField_serb.value,
                        onValueChange = {
                            txtField_serb.value = it.take(10)
                        })

                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = if (txtField_ru.value.isEmpty()) R.color.holo_green_light else R.color.holo_red_dark)
                                ),
                                shape = RoundedCornerShape(50)
                            ),
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.Black,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,

                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent

                        ),

                        placeholder = { Text(text = "напиши на русском") },
                        value = txtField_ru.value,
                        onValueChange = {
                            txtField_ru.value = it.take(10)
                        })

                    Spacer(modifier = Modifier.height(20.dp))
                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                if (txtField_serb.value.isEmpty()) {
                                    txtFieldError.value = "Field can not be empty"
                                    return@Button
                                }
                                saveWord(txtField_serb.value, txtField_ru.value)
                                setShowDialog(false)

                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Добавить слово")
                        }
                    }
                }
            }
        }
    }
}

private fun saveWord(serb_word: String, rus_word: String) {
    Firebase.firestore.collection("My Word").document().set(
        Word(
            "0",
            serb_word,
            rus_word,

            "",
            1
        )
    )

}

//    Column(modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center) {
//        TextField(value = serb_State.value, onValueChange ={
//            serb_State.value=it
//        } )
//        Spacer(modifier = Modifier.height(20.dp))
//        TextField(value = rus_State.value, onValueChange ={
//            rus_State.value=it
//        } )
//        Spacer(modifier = Modifier.height(20.dp))
//        Button(onClick = { /*TODO*/ }) {
//            Text(text = "Добавить слово")
//
//        }
//    }
//
//    }
//
//fun DialogWithImage(
//    onDismissRequest: () -> Unit,
//    onConfirmation: () -> Unit,
//   // painter: Painter,
//    imageDescription: String,
//) {
//    val serb_State = remember {
//        mutableStateOf("")
//    }
//    val rus_State = remember {
//        mutableStateOf("")
//    }
//    Dialog(onDismissRequest = { onDismissRequest() }) {
//        // Draw a rectangle shape with rounded corners inside the dialog
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(375.dp)
//                .padding(16.dp),
//            shape = RoundedCornerShape(16.dp),
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally,
//            ) {
////                Image(
////                    //painter = painter,
////                    contentDescription = imageDescription,
////                    contentScale = ContentScale.Fit,
////                    modifier = Modifier
////                        .height(160.dp)
////                )
//                Spacer(modifier = Modifier.height(20.dp))
//                TextField(value = rus_State.value, onValueChange = {
//                    rus_State.value = it
//                })
//                Spacer(modifier = Modifier.height(20.dp))
//                Button(onClick = { /*TODO*/ }) {
//                    Text(text = "Добавить слово")
//
//                }
//            }
//        }
//    }
//}
