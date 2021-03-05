/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.countdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.isFocused
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.amber50

data class Period(val top: Int) {

    private val placeholder = "0".repeat((top - 1).toString().length)
    private val length = (top - 1).toString().length

    fun getValue(value: Int): String {
        val period = if (value < top) value else 0
        return placeholder.plus(period).takeLast(length)
    }

    fun getValueOrLast(value: Int): String {
        val period = if (value < top) value else value.toString().last()
        return placeholder.plus(period).takeLast(length)
    }
}

@Composable
fun PeriodText(period: String, modifier: Modifier = Modifier) {
    Text(
        text = period,
        modifier = modifier,
        style = MaterialTheme.typography.h2,
        textAlign = TextAlign.Center
    )
}

@Composable
fun PeriodDivider(modifier: Modifier = Modifier) {
    Text(
        text = ":",
        modifier = modifier,
        style = MaterialTheme.typography.h2,
        textAlign = TextAlign.Center
    )
}

/**
 * Basic [BasicTextField] for inputting a [Period].
 *
 * @param period (state) current period to display
 * @param onPeriodChange (event) request the text change state
 * @param modifier the modifier for this element
 * @param onImeAction (event) notify caller of [ImeAction.Done] events
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BasicPeriodInputText(
    periodValue: Int,
    onPeriodChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    period: Period = Period(60),
    onImeAction: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val readablePeriod = period.getValue(periodValue)

    val textFieldValue = TextFieldValue(
        text = readablePeriod,
        selection = TextRange(readablePeriod.length)
    )

    val isFocused = remember { mutableStateOf(false) }

    val textColor = if (MaterialTheme.colors.isLight) {
        Color.White
    } else {
        amber50
    }

    BasicTextField(
        value = textFieldValue,
        onValueChange = {
            val stringValue = period.getValueOrLast(it.text.toIntOrNull() ?: 0)
            val numericValue = stringValue.toInt()
            onPeriodChange(numericValue)
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
                keyboardController?.hideSoftwareKeyboard()
            }
        ),
        modifier = modifier.onFocusChanged { state -> isFocused.value = state.isFocused },
        textStyle = MaterialTheme.typography.h2.copy(color = textColor),
        cursorBrush = SolidColor(Color.Transparent),
        decorationBox = { innerTextField ->
            if (isFocused.value) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colors.onBackground.copy(alpha = .05f)),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    innerTextField()
                    Divider(
                        color = textColor, thickness = 4.dp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            } else {
                innerTextField()
            }
        }
    )
}

@Preview
@Composable
fun PeriodPreview() {
    PeriodText("08")
}
