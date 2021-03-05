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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun QuickTimeSelection(modifier: Modifier = Modifier, onTimeChange: (Int) -> Unit) {
    val quickSelectionTimes = List(10) { 30 * (it + 1) }
    LazyRow(modifier = modifier) {
        items(quickSelectionTimes) { item ->
            MinutesText(
                item,
                Modifier
                    .padding(10.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            CornerSize(12.dp),
                            CornerSize(12.dp),
                            CornerSize(12.dp),
                            CornerSize(12.dp)
                        )
                    )
                    .clickable {
                        onTimeChange(item)
                    }
                    .padding(vertical = 8.dp, horizontal = 12.dp)
            )
        }
    }
}

@Composable
fun MinutesText(timeInSeconds: Int, modifier: Modifier = Modifier) {
    val period = Period(60)
    val minutes = period.getValue(timeInSeconds / 60)
    val seconds = period.getValue(timeInSeconds % 60)

    Text("$minutes:$seconds", modifier, style = MaterialTheme.typography.subtitle1)
}
