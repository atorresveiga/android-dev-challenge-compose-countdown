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
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBackground(state: TimerState, progress: Int) {

    BoxWithConstraints {
        val realMaxHeight = maxHeight

        BoxWithConstraints(
            modifier = Modifier
                .layout { measurable, constraints ->
                    val sideSize = constraints.maxWidth / 2 + constraints.maxHeight
                    val bigConstraints = constraints.copy(maxWidth = sideSize, maxHeight = sideSize)
                    val placeable = measurable.measure(bigConstraints)
                    layout(placeable.width, placeable.height) {
                        placeable.place(0, 0)
                    }
                },
            contentAlignment = Alignment.BottomCenter
        ) {

            val transitionData = updateProgressTransitionData(
                state = state,
                progress = progress,
                maxHeight = maxHeight,
                realMaxHeight
            )

            Box(
                modifier = Modifier
                    .padding(bottom = transitionData.padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {

                Column(
                    modifier = Modifier
                        .width(transitionData.width)
                        .height(transitionData.height)
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(
                                CornerSize(transitionData.cornerRadius),
                                CornerSize(transitionData.cornerRadius),
                                CornerSize(transitionData.cornerRadius),
                                CornerSize(transitionData.cornerRadius)
                            )
                        ),
                ) {}
            }
        }
    }
}

// Holds the animation values.
private class ProgressTransitionData(
    padding: State<Dp>,
    height: State<Dp>,
    width: State<Dp>,
    cornerRadius: State<Dp>
) {
    val padding by padding
    val height by height
    val width by width
    val cornerRadius by cornerRadius
}

@Composable
private fun updateProgressTransitionData(
    state: TimerState,
    progress: Int,
    maxHeight: Dp,
    realMaxHeight: Dp
): ProgressTransitionData {
    val bottomOffset = (maxHeight - realMaxHeight) / 2
    val transition = updateTransition(state)
    val padding = transition.animateDp(
        transitionSpec = {
            when {
                TimerState.Stop isTransitioningTo TimerState.Running -> {
                    keyframes {
                        durationMillis = 351
                        bottomOffset + 44.dp at 35
                        0.dp at 351
                    }
                }
                else -> {
                    keyframes {
                        durationMillis = 300
                        delayMillis = 150
                        bottomOffset at 0
                    }
                }
            }
        }
    ) { timerState ->
        if (timerState == TimerState.Stop) {
            bottomOffset + 96.dp
        } else {
            bottomOffset
        }
    }
    val height = transition.animateDp(
        transitionSpec = {
            when {
                TimerState.Running isTransitioningTo TimerState.Stop ||
                    TimerState.Paused isTransitioningTo TimerState.Stop -> {
                    keyframes {
                        durationMillis = 300
                        delayMillis = 150
                        248.dp at 1
                    }
                }
                TimerState.Stop isTransitioningTo TimerState.Running -> {
                    keyframes {
                        durationMillis = 350
                    }
                }
                else -> {
                    keyframes {
                        durationMillis = 350
                    }
                }
            }
        }
    ) { timerState ->
        when (timerState) {
            TimerState.Stop -> {
                56.dp
            }
            else -> {
                realMaxHeight * progress / 100
            }
        }
    }

    val width = transition.animateDp(
        transitionSpec = {
            when {
                TimerState.Running isTransitioningTo TimerState.Stop ||
                    TimerState.Paused isTransitioningTo TimerState.Stop -> {
                    keyframes {
                        durationMillis = 300
                        delayMillis = 150
                        248.dp at 1
                    }
                }
                TimerState.Stop isTransitioningTo TimerState.Running -> {
                    keyframes {
                        durationMillis = 350
                    }
                }
                else -> {
                    keyframes {
                        durationMillis = 350
                    }
                }
            }
        }
    ) { timerState ->
        if (timerState == TimerState.Stop) {
            56.dp
        } else {
            realMaxHeight // to keep ripple circular form
        }
    }

    val cornerRadius = transition.animateDp(
        transitionSpec = {
            when {
                TimerState.Stop isTransitioningTo TimerState.Running -> {
                    keyframes {
                        durationMillis = 350
                        realMaxHeight / 2 at 345
                    }
                }
                else -> {
                    keyframes {
                        durationMillis = 300
                        delayMillis = 150
                        124.dp at 1
                    }
                }
            }
        }
    ) { timerState ->
        if (timerState == TimerState.Stop) {
            28.dp
        } else {
            0.dp
        }
    }

    return remember(transition) {
        ProgressTransitionData(
            padding = padding,
            height = height,
            width = width,
            cornerRadius = cornerRadius
        )
    }
}

@Preview(widthDp = 360, heightDp = 480)
@Composable
fun ProgressBackgroundPreview() {
    ProgressBackground(state = TimerState.Stop, progress = 100)
}
