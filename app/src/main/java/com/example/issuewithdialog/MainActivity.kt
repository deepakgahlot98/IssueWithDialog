package com.example.issuewithdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.registerForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttachmentComponent()
        }
    }
}

@Preview
@Composable
fun AttachmentComponent() {

    var fileName = remember { mutableStateOf(ArrayList<String>()) }
    var array = arrayOf("application/pdf")
    var files = ArrayList<String>()
    var fileAttached = remember { mutableStateOf(false) }


    Column {

        val registerTakeFile = registerForActivityResult(
            ActivityResultContracts.OpenMultipleDocuments()
        ) {
            for (i in 0 until it.size) {
                var uri = it.get(i)
                files.add(uri.path!!)
            }
            fileName.value = files
        }

        Text(
            text = "Attachment",
            color = colorResource(id = R.color.black)
        )
        if (fileAttached.value) {
            FileAttached(item = fileName.value)
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colorResource(id = R.color.black)
            ),
            onClick = {
                registerTakeFile.launch(array)
                fileAttached.value = true
            }
        ) {
            Icon(painterResource(id = R.drawable.attach), contentDescription = "attach")
            Text(
                fontSize = 14.sp,
                text = "Add Files",
            )
        }
    }
}

@Composable
fun FileAttached(item: ArrayList<String>) {
    Column {
        item.forEach {
            Row {
                Text(
                    text = it,
                    color = colorResource(id = R.color.black)
                )
            }
        }
    }
}