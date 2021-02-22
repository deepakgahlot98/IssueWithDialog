package com.example.issuewithdialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

val  CONSTANT_CHAR = "$"

@Composable
fun ChoiceReadOnlyTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: @Composable () -> Unit
) {
    Box {
        TextField(
            value = value,
            backgroundColor = Color.White,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            trailingIcon = { Icon(Icons.Default.ExpandMore, "expand-more") },
            textStyle = TextStyle(color = colorResource(id = R.color.black)),
            label = label,
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = onClick)
        )
    }
}

@Composable
fun MultiSelectDialog(
    title: String,
    optionList: ArrayList<String>,
    defaultsSelected: ArrayList<String>,
    onSubmitButtonClick: (ArrayList<String>) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var selectedOptions = remember { mutableStateOf(defaultsSelected.toMutableList()) }
    var customTextEntered = remember { mutableStateOf( false ) }

    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Surface {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    modifier = Modifier.padding(
                        end = 24.dp,
                        start = 24.dp,
                        top = 24.dp,
                        bottom = 24.dp
                    ),
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(500),
                    fontStyle = FontStyle.Normal,
                    color = colorResource(id = R.color.black)

                )

                Spacer(modifier = Modifier.preferredWidth(10.dp))

                for (item in optionList) {
                    var checkedState = remember { mutableStateOf(selectedOptions.value.contains(item)) }

                    // Handling the custom option - Text response that is been added to the List
                    var customText = ""
                    selectedOptions.value.forEach {
                        if (it.contains(CONSTANT_CHAR) && optionList.indexOf(item) == optionList.lastIndex) {
                            checkedState.value = true
                            customText = it.split(CONSTANT_CHAR)[1]
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = checkedState.value,
                                onClick = {
                                    if (!checkedState.value) {
                                        checkedState.value = true
                                        if (optionList.indexOf(item) != optionList.size - 1) {
                                            selectedOptions.value.add(item)
                                        }
                                    } else {
                                        if (optionList.indexOf(item) == optionList.size - 1) {
                                            selectedOptions.value.removeIf {
                                                it.contains(CONSTANT_CHAR)
                                            }
                                        }
                                        checkedState.value = false
                                        selectedOptions.value.remove(item)
                                    }
                                }
                            )
                            .padding(bottom = 24.dp)
                            .padding(end = 32.dp)
                            .padding(start = 24.dp)
                    ) {
                        RadioButton(
                            selected = checkedState.value,
                            onClick = { },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.black,)
                                   )
                        )
                        Text(
                            text = item, fontStyle = FontStyle.Normal, modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    if (optionList.indexOf(item) == optionList.size - 1 && checkedState.value) {
                        var text by remember { mutableStateOf(TextFieldValue(customText)) }
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            backgroundColor = colorResource(id = R.color.white),
                            value = text,
                            singleLine = true,
                            placeholder = { Text("Please Select") },
                            onValueChange = {
                                text = it
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                    onDone = {
                                        selectedOptions.value.add("Other" + CONSTANT_CHAR + text.text)
                                        customTextEntered.value = true
                                    }
                                ),
                            inactiveColor = colorResource(id = R.color.black),
                            activeColor = colorResource(id = R.color.teal_700)
                        )
                    }
                }

                Divider(modifier = Modifier.fillMaxWidth(), color = colorResource(id = R.color.black), thickness = 1.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    TextButton(
                        onClick = {
                            onDismissRequest.invoke()
                        },
                    ) {
                        Text(
                            text = "CANCEL",
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Normal,
                            color = colorResource(id = R.color.teal_700)
                        )
                    }

                    Spacer(modifier = Modifier.preferredWidth(18.dp))

                    TextButton(
                        enabled = customTextEntered.value,
                        onClick = {
                            onSubmitButtonClick.invoke(selectedOptions.value as ArrayList<String>)
                            onDismissRequest.invoke()
                        }
                    ) {
                        val color = if (!customTextEntered.value) {
                            colorResource(id = R.color.black)
                        } else {
                            colorResource(id = R.color.teal_200)
                        }
                        Text(
                            text = "SELECT",
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Normal,
                            color = color
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MultiSelectPickList(
    list: ArrayList<String>,
) {
    val dialogState = remember { mutableStateOf(false) }
    val textState = remember { mutableStateOf("") }
    val labelSate = remember { mutableStateOf("Please Select") }
    var selectedOptions = remember { mutableStateOf(ArrayList<String>()) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (dialogState.value) {
            MultiSelectDialog(
                title = "Please Select",
                optionList = list,
                defaultsSelected = selectedOptions.value,
                onSubmitButtonClick = {
                    selectedOptions.value = it
                    textState.value = ""
                    it.forEach {
                        if (it.contains(CONSTANT_CHAR)) {
                            textState.value = textState.value + it.split("$")[1]
                        } else {
                            textState.value = textState.value + it
                        }
                    }
                }
            ) {
                dialogState.value = false
            }
        }
    }

    Column {
        ChoiceReadOnlyTextField(
            value = TextFieldValue(textState.value),
            onValueChange = { textState.value = it.text },
            onClick = { dialogState.value = true },
            label = {
                Text(text = labelSate.value)
            }
        )
    }
}