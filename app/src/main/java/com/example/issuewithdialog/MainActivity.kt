package com.example.issuewithdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.activity.compose.registerForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainActivity : AppCompatActivity() {

    val list = arrayListOf<String>("Section 1", "Section 2", "Secton 3", "Section 4")

    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GenerateUI(list)
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun GenerateUI(list: ArrayList<String>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    ) {
        CardItem(list)
    }
}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun CardItem(list: ArrayList<String>) {
    val modifierNoError = Modifier
        .fillMaxSize()
        .padding(bottom = 55.dp)
    val modifierError = Modifier
        .fillMaxSize()
        .padding(bottom = 95.dp)
    LazyColumn(modifier = modifierNoError) {
        itemsIndexed(list) { _, card ->
            ExpandableCard(
                card,
            )
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun ExpandableCard(
    card: String,
) {
    val expanded = remember { mutableStateOf(false) }

    Card(
        contentColor = Color.White,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 10.dp,
                vertical = 8.dp
            )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .padding(20.dp, 14.dp, 0.dp, 14.dp)
                    .fillMaxWidth()
                    .animateContentSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = card,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 10.dp, 0.dp)
                        .width(280.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                IconButton(
                    onClick = { expanded.value = !expanded.value },
                    modifier = Modifier.padding(end = 15.dp),
                    content = {
                        if (expanded.value) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_expand),
                                contentDescription = "",
                                modifier = Modifier.rotate(0f),
                                tint = Color.Black
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_expandless),
                                contentDescription = "",
                                modifier = Modifier.rotate(0f),
                                tint = Color.Black
                            )
                        }
                    }
                )
            }
            //Add the expandable content here
            ExpandableContent(
                visible = expanded.value,
                initialVisibility = expanded.value,
            )
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun ExpandableContent(
    visible: Boolean = true,
    initialVisibility: Boolean = false,
) {
    val listofQuestion = arrayListOf<String>(
        "This is Question 1","This is Question 2","This is Question 3", "Sub-Section" ,"This is Question 4","This is Question 5"
    )
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = FADE_IN_ANIMATION_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(EXPAND_ANIMATION_DURATION))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = FADE_OUT_ANIMATION_DURATION,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(COLLAPSE_ANIMATION_DURATION))
    }
    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Divider(color = Color.Gray, thickness = 1.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 20.dp, end = 12.dp, bottom = 10.dp)
        ) {
            for (question in listofQuestion) {
                ViewItem(
                    question = question,
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun ViewItem(
    question: String
) {
    val levelOne = question.contains("Sub-Section")
    if (levelOne) {
        Column(
            modifier = Modifier.padding(start = 0.dp, top = 10.dp).animateContentSize()
        ) {
            Column {
                val expanded = remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(0.dp, 5.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                        .animateContentSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = question,
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 0.dp),
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        maxLines = 1
                    )
                    IconButton(
                        onClick = { expanded.value = !expanded.value },
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .width(20.dp),
                        content = {
                            if (expanded.value) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_expand),
                                    contentDescription = "",
                                    modifier = Modifier.rotate(0f),
                                    tint = Color(R.color.black)
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_expandless),
                                    contentDescription = "",
                                    modifier = Modifier.rotate(0f),
                                    tint = Color(R.color.black)
                                )
                            }
                        }
                    )
                }
                AnimatedVisibility(
                    visible = expanded.value,
                    initiallyVisible = false,
                    modifier = Modifier.wrapContentHeight(Alignment.Bottom),
                ) {
                    val listofQuestion = arrayListOf<String>(
                        "This is Question 1","This is Question 2","This is Question 3", "Sub-Section" ,"This is Question 4","This is Question 5"
                    )
                    Divider(color = Color.Gray, thickness = 1.dp)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, start = 0.dp, end = 10.dp).animateContentSize()
                    ) {
                        for (subQuestion in listofQuestion) {
                            Text(
                                modifier = Modifier.padding(50.dp),
                                text = subQuestion,
                                color = colorResource(id = R.color.black)
                            )
                        }
                    }
                }
            }
        }
    } else {
        Column {
            Text(
                modifier = Modifier.padding(top = 4.dp, start = 0.dp, end = 0.dp, bottom = 0.dp),
                text = question,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
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