/*
 * Copyright 2022 The Android Open Source Project
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

package com.example.reply.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reply.data.LocalEmailsDataProvider
import com.example.reply.ui.theme.AppTheme
import com.example.reply.ui.theme.Purple40
import com.example.reply.ui.theme.Purple80
import com.example.reply.ui.theme.PurpleGrey80


class MainActivity : ComponentActivity() {

    private val viewModel: ReplyHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val uiState by viewModel.uiState.collectAsState()
            AppTheme {
                Surface(tonalElevation = 5.dp) {
                    NewCompDraw()

//                    ReplyApp(
//                        replyHomeUIState = uiState,
//                        closeDetailScreen = {
//                            viewModel.closeDetailScreen()
//                        },
//                        navigateToDetail = { emailId ->
//                            viewModel.setSelectedEmail(emailId)
//                        }
//                    )
                }
            }
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Composable
fun ReplyAppPreviewLight() {
    AppTheme {
        ReplyApp(
            replyHomeUIState = ReplyHomeUIState(
                emails = LocalEmailsDataProvider.allEmails
            )
        )
    }
}

@Composable
fun NewCompDraw() {
    Box(
        modifier = Modifier
            .background(Purple40)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier
            .padding(8.dp)
            .aspectRatio(3 / 2f)
            .fillMaxSize()
            .drawWithCache {
                val path = generatePath(
                    listOf(
                        Balance(100, 52),
                        Balance(30, 4),
                        Balance(200, 300),
                        Balance(90, 10),
                        Balance(888, 777),

                        ), size
                )
                val filledPath = Path()
                filledPath.addPath(path)
                filledPath.lineTo(size.width.toFloat(), size.height.toFloat())
                filledPath.lineTo(0f, size.height)
                filledPath.close()

                val brush = Brush.verticalGradient(
                    listOf(
                        Color.Green.copy(alpha = 0.4f),
                        Color.Transparent
                    )
                )


                onDrawBehind {
                    drawPath(path, Color.Red, style = Stroke(2.dp.toPx()))

                    drawPath(filledPath, brush = brush)
                    val barWithPx = 1.dp.toPx()
                    drawRect(Color.Green, style = Stroke(barWithPx))

                    val verticalLines = 4
                    val verticalSize = size.width / (verticalLines + 1)

                    repeat(verticalLines) { i ->
                        val startX = verticalSize * (i + 1)
                        drawLine(
                            Color.Green,
                            start = Offset(startX, 0f),
                            end = Offset(startX, size.height),
                            strokeWidth = barWithPx
                        )
                    }

                    val horizontalLines = 3
                    val horizontalSize = size.height / (horizontalLines + 1)

                    repeat(horizontalLines) { i ->
                        val startY = horizontalSize * (i + 1)
                        drawLine(
                            Color.Green,
                            start = Offset(0f, startY),
                            end = Offset(size.width, startY),
                            strokeWidth = barWithPx
                        )
                    }
                }
            }
        )
    }
}

fun generatePath(data: List<Balance>, size: Size): Path {
    val path = Path()

    data.forEachIndexed { i, balance ->
        val x = balance.a
        val y = balance.b
        path.lineTo(x.toFloat(), y.toFloat())
    }
    return path
}

data class Balance(val a: Int, val b: Int)

@Preview
@Composable
fun NewCompDrawPreview() {
    NewCompDraw()
}
