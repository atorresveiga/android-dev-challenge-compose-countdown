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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountDownViewModel : ViewModel() {

    var progress by mutableStateOf(0)
        private set
    var state by mutableStateOf(TimerState.Stop)
        private set

    private var totalTime = 0
    var time by mutableStateOf(totalTime)
        private set

    private var countdown: Job? = null

    fun onTimeChanged(newTime: Int) {
        if (state == TimerState.Stop) {
            totalTime = newTime
            time = newTime
            progress = 100
        }
    }

    fun onStateChanged(newState: TimerState) {
        when (newState) {
            TimerState.Running -> {
                if (time > 0) {
                    state = newState
                    countdown = viewModelScope.launch {
                        while (time > 0) {
                            delay(1000)
                            time -= 1
                            progress = (time * 100) / totalTime
                        }
                        delay(300)
                        onStateChanged(TimerState.Stop)
                    }
                }
            }
            TimerState.Stop -> {
                state = newState
                time = 0
                totalTime = 0
                progress = 0
                countdown?.cancel()
                countdown = null
            }
            TimerState.Paused -> {
                if (time > 0) {
                    state = newState
                    countdown?.cancel()
                    countdown = null
                }
            }
        }
    }
}
