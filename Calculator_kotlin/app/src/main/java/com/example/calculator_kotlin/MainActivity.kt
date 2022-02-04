package com.example.calculator_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var showResult : TextView
    private lateinit var operator : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showResult = findViewById(R.id.show_result)
        operator = ""
    }

    fun enterDigit(view: View) {
        showResult.append((view as Button).text)
    }

    fun enterOperator(view: View) {
        if (showResult.text.isEmpty()) {
            Toast.makeText(this, "enter some numbers", Toast.LENGTH_SHORT).show()
        } else if (operator.isNotEmpty()){
            Toast.makeText(this, "enter second number", Toast.LENGTH_SHORT).show()
        } else {
            val op = (view as Button).text
            showResult.append(op)
            operator = op.toString()
        }
    }

    fun equal(view: View) {
        if (showResult.text.isEmpty()) {
            Toast.makeText(this, "enter some numbers", Toast.LENGTH_SHORT).show()
        } else {
            val result = showResult.text.split(operator)

            if (operator == "+"){
                showResult.text = (result[0].toInt() + result[1].toInt()).toString()
            } else if (operator == "-"){
                showResult.text = (result[0].toInt() - result[1].toInt()).toString()
            } else if (operator == "*"){
                showResult.text = (result[0].toInt() * result[1].toInt()).toString()
            } else {
                if (result[1] == "0") {
                    Toast.makeText(this, "nie dziel przez 0", Toast.LENGTH_SHORT).show()
                    showResult.text = ""
                } else {
                    showResult.text = (result[0].toInt() / result[1].toInt()).toString()
                }
            }
        }
        operator = ""
    }

    fun clear(view: View) {
        showResult.text = ""

    }
}
