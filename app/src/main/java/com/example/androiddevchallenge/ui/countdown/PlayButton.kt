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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R

@Composable
fun PlayButton(state: TimerState, onStateChange: (TimerState) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            modifier = Modifier
                .padding(bottom = 96.dp)
                .size(56.dp),
            onClick = {
                val newState =
                    if (state == TimerState.Stop || state == TimerState.Paused) {
                        TimerState.Running
                    } else {
                        TimerState.Paused
                    }
                onStateChange(newState)
            },
            shape = RoundedCornerShape(
                CornerSize(28.dp),
                CornerSize(28.dp),
                CornerSize(28.dp),
                CornerSize(28.dp)
            ),
            colors = if (state == TimerState.Stop) {
                ButtonDefaults.buttonColors()
            } else {
                ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.background
                )
            }
        ) {
            Icon(
                imageVector = if (state == TimerState.Stop || state == TimerState.Paused) {
                    Icons.Default.PlayArrow
                } else {
                    Icons.Default.Pause
                },
                contentDescription = if (state == TimerState.Stop || state == TimerState.Paused) {
                    stringResource(id = R.string.button_play)
                } else {
                    stringResource(id = R.string.button_pause)
                }
            )
        }
    }
}
