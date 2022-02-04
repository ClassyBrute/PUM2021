package com.example.physicsquiz_kotlin

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean)
