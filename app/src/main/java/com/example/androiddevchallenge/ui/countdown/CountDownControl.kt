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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CountDownControl(
    time: Int,
    onTimeChange: (Int) -> Unit,
    isSelectionMode: Boolean,
    modifier: Modifier = Modifier
) {

    val hours = time / 3600
    val minutes = (time % 3600) / 60
    val seconds = (time % 3600) % 60

    val onHoursChange = fun(value: Int) {
        val newTime = value * 3600 + minutes * 60 + seconds
        onTimeChange(newTime)
    }

    val onMinutesChange = fun(value: Int) {
        val newTime = hours * 3600 + value * 60 + seconds
        onTimeChange(newTime)
    }

    val onSecondsChange = fun(value: Int) {
        val newTime = hours * 3600 + minutes * 60 + value
        onTimeChange(newTime)
    }

    val secondsAndMinutesPeriod = Period(60)
    val hoursPeriod = Period(24)

    if (isSelectionMode) {
        SelectTimeCountdown(
            seconds = seconds,
            minutes = minutes,
            hours = hours,
            onHoursChange = onHoursChange,
            onMinutesChange = onMinutesChange,
            onSecondsChange = onSecondsChange,
            secondsPeriod = secondsAndMinutesPeriod,
            minutesPeriod = secondsAndMinutesPeriod,
            hoursPeriod = hoursPeriod,
            modifier = modifier
        )
    } else {
        DisplayTimeCountdown(
            seconds = secondsAndMinutesPeriod.getValue(seconds),
            minutes = secondsAndMinutesPeriod.getValue(minutes),
            hours = hoursPeriod.getValue(hours),
            modifier = modifier
        )
    }
}

@Composable
fun DisplayTimeCountdown(
    seconds: String,
    minutes: String,
    hours: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .weight(1f)
        )
        PeriodText(
            period = hours,
            modifier = Modifier.weight(2f)
        )
        PeriodDivider(modifier = Modifier.weight(1f))
        PeriodText(
            period = minutes,
            modifier = Modifier.weight(2f)
        )
        PeriodDivider(modifier = Modifier.weight(1f))
        PeriodText(
            period = seconds,
            modifier = Modifier.weight(2f)
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .weight(1f)
        )
    }
}

@Composable
fun SelectTimeCountdown(
    seconds: Int,
    onSecondsChange: (Int) -> Unit,
    minutes: Int,
    onMinutesChange: (Int) -> Unit,
    hours: Int,
    onHoursChange: (Int) -> Unit,
    secondsPeriod: Period,
    minutesPeriod: Period,
    hoursPeriod: Period,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(
            modifier = Modifier
                .height(20.dp)
                .weight(1f)
        )
        BasicPeriodInputText(
            periodValue = hours,
            onPeriodChange = onHoursChange,
            period = hoursPeriod,
            modifier = Modifier.weight(2f)
        )
        PeriodDivider(modifier = Modifier.weight(1f))
        BasicPeriodInputText(
            periodValue = minutes,
            onPeriodChange = onMinutesChange,
            modifier = Modifier.weight(2f),
            period = minutesPeriod
        )
        PeriodDivider(modifier = Modifier.weight(1f))
        BasicPeriodInputText(
            periodValue = seconds,
            onPeriodChange = onSecondsChange,
            modifier = Modifier.weight(2f),
            period = secondsPeriod
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .weight(1f)
        )
    }
}
