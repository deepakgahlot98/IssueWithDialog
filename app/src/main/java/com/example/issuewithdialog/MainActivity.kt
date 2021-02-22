package com.example.issuewithdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext

class MainActivity : AppCompatActivity() {

    val list = arrayListOf<String>("Option 1", "Option 2", "Option 3", "Other")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiSelectComponent(list)
        }
    }
}

@Composable
fun MultiSelectComponent(list: ArrayList<String>) {
    MultiSelectPickList(list)
}