package com.example.kotlin_test

import java.io.File


fun main() {

    val file =
        File("/Users/nhn/workspace_android/AndroidTestProject/kotlin-test/src/main/java/com/example/kotlin_test/quiz.csv")
    println(file.exists())

    if (file.exists()) {
        var text = file.readText()
        text = text.replace("\\n", "\n")

        val modifiedString = text.replace("\"", "\\\"").lines()
        modifiedString.forEach {

            val parts = it.split(",\\s*(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())
            println(parts)
        }


    } else {
        println("파일을 찾을 수 없습니다.")
    }



}