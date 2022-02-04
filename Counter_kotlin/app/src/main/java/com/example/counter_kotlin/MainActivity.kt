package com.example.counter_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var count = 0
    private lateinit var showCount : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showCount = findViewById(R.id.show_count)

        if (savedInstanceState != null) {
            showCount.text = savedInstanceState.getInt("counter_state").toString()
        }
    }

    fun countUpButton(view: android.view.View) {
        count++
        showCount.text = count.toString()
    }

    fun countDownButton(view: View) {
        count--
        showCount.text = count.toString()
    }

    fun resetButton(view: View) {
        count = 0
        showCount.text = count.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("counter_state", count)
    }
}