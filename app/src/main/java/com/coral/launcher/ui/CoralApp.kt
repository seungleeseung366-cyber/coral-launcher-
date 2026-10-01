package com.coral.launcher.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coral.launcher.renderer.RendererManager

@Composable
fun CoralApp() {
    val rendererManager = remember { RendererManager() }
    var selectedRenderer by remember { mutableStateOf("auto") }

    MaterialTheme {
        Scaffold { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "CORAL Launcher",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Minecraft Java Launcher",
                    style = MaterialTheme.typography.bodyLarge
                )

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Renderer",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(text = "Selected: $selectedRenderer")

                        rendererManager.all().forEach { renderer ->
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    selectedRenderer = renderer.info.id
                                }
                            ) {
                                Text(renderer.info.name)
                            }
                        }
                    }
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                ) {
                    Text("PLAY")
                }
            }
        }
    }
}
