package com.coral.launcher.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {

    val viewModel = remember {
        HomeViewModel()
    }

    var refresh by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {
        viewModel.loadVersions {
            refresh++
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "CORAL Launcher",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Minecraft Launcher",
            style = MaterialTheme.typography.bodyMedium
        )

        Button(
            onClick = {
                viewModel.loadVersions {
                    refresh++
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Refresh Versions")
        }

        when {
            viewModel.loading -> {
                CircularProgressIndicator()
            }

            viewModel.error != null -> {
                Text(
                    text = "Error: ${viewModel.error}",
                    color = MaterialTheme.colorScheme.error
                )
            }

            else -> {
                key(refresh) {
                    LazyColumn(
                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {
                        items(viewModel.versions) { version ->

                            Card(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = version.id,
                                        style = MaterialTheme
                                            .typography
                                            .titleMedium
                                    )

                                    Text(
                                        text = version.type
                                    )

                                    Text(
                                        text = version.releaseTime
                                    )

                                    Button(
                                        onClick = {
                                            // Launch akan
                                            // disambungkan
                                            // pada tahap berikutnya.
                                        }
                                    ) {
                                        Text("PLAY")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
