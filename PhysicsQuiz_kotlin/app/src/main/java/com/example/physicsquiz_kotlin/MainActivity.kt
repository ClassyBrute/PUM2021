package com.example.physicsquiz_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    private var id_: Int = 0

    private val questions = listOf(
        Question(R.string.question1, true),
        Question(R.string.question2, false),
        Question(R.string.question3, true),
        Question(R.string.question4, false),
        Question(R.string.question5, true),
        Question(R.string.question6, true),
        Question(R.string.question7, false),
        Question(R.string.question8, false))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.question_text)

        println(resources.getString(R.string.question1))

        textView.text = questions[id_].textResId.toString()
    }

    fun answerTrue(view: View) {

    }

    fun answerFalse(view: View) {

    }

    fun prevQuestion(view: View) {
        id_--

        if (id_ < 0){
            id_ = questions.size-1
        }

        textView.text = questions[id_].textResId.toString()
    }

    fun nextQuestion(view: View) {
        id_++

        if (id_ > questions.size-1){
            id_ = 0
        }

        textView.text = questions[id_].textResId.toString()
    }

    fun restart(view: View) {

    }

    fun cheat(view: View) {

    }

    fun search(view: View) {

    }


}