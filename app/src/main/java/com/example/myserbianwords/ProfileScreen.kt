package com.example.myserbianwords

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.example.myserbianwords.data.Word
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun  ProfileScreen(word: Word) {
val scrollState= rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    ProfileHeader(word = word, containerHeight = this@BoxWithConstraints.maxHeight)

                }
            }
        }

    }
}
@Composable
private fun ProfileHeader( word:Word,
                           containerHeight: Dp
){
    AsyncImage(model = word.imageURl,contentDescription = null
,            modifier = Modifier
            .heightIn(max = containerHeight / 2)
            .fillMaxWidth(),
        contentScale = ContentScale.Crop,
    )



}
