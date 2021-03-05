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

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R

@Composable
fun StopButton(state: TimerState, onStateChange: (TimerState) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {

        val transitionData = updateCloseButtonTransitionData(state)

        Button(
            modifier = Modifier
                .padding(bottom = transitionData.paddingBottom, end = transitionData.paddingEnd)
                .size(transitionData.size)
                .alpha(transitionData.alpha),
            onClick = {
                if (state == TimerState.Running || state == TimerState.Paused) {
                    onStateChange(TimerState.Stop)
                }
            },
            shape = RoundedCornerShape(
                CornerSize(transitionData.cornerRadius),
                CornerSize(transitionData.cornerRadius),
                CornerSize(transitionData.cornerRadius),
                CornerSize(transitionData.cornerRadius)
            ),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colors.onError,
                backgroundColor = MaterialTheme.colors.error
            )
        ) {
            Icon(
                modifier = Modifier.alpha(transitionData.alpha),
                imageVector = Icons.Default.Stop,
                contentDescription = stringResource(id = R.string.button_stop)
            )
        }
    }
}

private class CloseButtonTransitionData(
    size: State<Dp>,
    cornerRadius: State<Dp>,
    alpha: State<Float>,
    paddingEnd: State<Dp>,
    paddingBottom: State<Dp>
) {
    val size by size
    val alpha by alpha
    val cornerRadius by cornerRadius
    val paddingEnd by paddingEnd
    val paddingBottom by paddingBottom
}

@Composable
private fun updateCloseButtonTransitionData(state: TimerState): CloseButtonTransitionData {
    val transition = updateTransition(state)
    val size = transition.animateDp { timerState ->
        if (timerState == TimerState.Stop) {
            10.dp
        } else {
            48.dp
        }
    }
    val cornerRadius = transition.animateDp { timerState ->
        if (timerState == TimerState.Stop) {
            5.dp
        } else {
            24.dp
        }
    }
    val alpha = transition.animateFloat(
        transitionSpec = {
            if (TimerState.Running isTransitioningTo TimerState.Stop) {
                keyframes {
                    durationMillis = 50
                }
            } else {
                keyframes {
                    durationMillis = 150
                }
            }
        }
    ) { timerState ->
        if (timerState == TimerState.Stop) {
            0f
        } else {
            1f
        }
    }
    val paddingEnd = transition.animateDp { timerState ->
        if (timerState == TimerState.Stop) {
            60.dp
        } else {
            32.dp
        }
    }
    val paddingBottom = transition.animateDp { timerState ->
        if (timerState == TimerState.Stop) {
            115.dp
        } else {
            96.dp
        }
    }
    return remember(transition) {
        CloseButtonTransitionData(
            size = size,
            cornerRadius = cornerRadius,
            alpha = alpha,
            paddingBottom = paddingBottom,
            paddingEnd = paddingEnd
        )
    }
}
