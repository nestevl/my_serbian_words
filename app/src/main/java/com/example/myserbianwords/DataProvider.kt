package com.example.myserbianwords

import com.example.myserbianwords.data.Lesson


object DataProvider {
    val lesson=
        Lesson(
            id=1,
            title = "Lekcija 1 Dobar dan!")
    val lessonList= listOf(lesson,
        Lesson(
            id = 2,
            title = "Lekcija 2 Moja zemlja",
        ),
        Lesson(
            id = 3,
            title =  " Lekcija 3 Razumeš li?",
        ),
        Lesson(
            id = 4,
            title =  "Lekcija 4 Moja porodica",
        ),
        Lesson(
            id = 5,
            title = "Lekcija 5 Moj svet",
        ),
        Lesson(
            id = 6,
            title =  "Lekcija 6 Šta radiš?"
        ),
        Lesson(
            id = 7,
            title =  "Lekcija 7 Srećan put"
        ),
        Lesson(
            id = 8,
            title =  "Lekcija 8 Stil života"
        ),
        Lesson(
            id = 0,
            title =  "Moje reči"
        ),





    )
}