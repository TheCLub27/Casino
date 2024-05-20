package com.example.slotmachine


import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.*
import kotlinx.coroutines.delay
import kotlin.math.max

@Composable
fun TopPanel(
    balance: Int = AppSettings.currentBalance,
    level: Int = AppSettings.currentLevel,
    progress: Float = AppSettings.getProgress(),
    spinsText: String = AppSettings.getSpinsText(),
    navController: NavController,
    onSettingsClick: () -> Unit = {}

) {
     var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(0.5f)) {
            LevelProgressBar(level = level, progress = progress)
            Text(
                text = spinsText,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        Text(
            text = "$balance",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        IconButton(onClick = { showDialog = true}) {
            Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Настройки")
        }
    }

    if (showDialog) {
        SettingsDialog(onDismiss = { showDialog = false }, navController = navController)
    }
}

@Composable
fun LevelProgressBar(level: Int, progress: Float, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(Color.Gray, shape = RoundedCornerShape(10.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(Color(0xFF6650a4), shape = RoundedCornerShape(10.dp))
            )
        }
        Text(
            text = "Ур. $level",
            color = Color.Black,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            ),
            modifier = Modifier
                .wrapContentWidth()
                .padding(top = 4.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDialog(onDismiss: () -> Unit, navController: NavController) {
    var timerStartTime by remember { mutableStateOf<Long?>(null) }
    val currentLevel = AppSettings.currentLevel
    val maxBet = AppSettings.getMaxBet()
    val maxSpins = AppSettings.getSpinsForLevel()
    val rewardAmount = (maxBet * maxSpins) / 2

    var timeRemaining by remember { mutableStateOf(AppSettings.timeRemaining) }

    LaunchedEffect(AppSettings.timerStartTime) {
        AppSettings.timerStartTime?.let { startTime ->
            while (true) {
                val elapsed = System.currentTimeMillis() - startTime
                timeRemaining = max(0, 2 * 60 * 60 * 1000 - elapsed)
                AppSettings.timeRemaining = timeRemaining
                if (timeRemaining <= 0) {
                    AppSettings.timerStartTime = null
                    break
                }
                delay(1000L)
            }
        }
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Настройки") },
        text = {
            Column {
                if (AppSettings.timerStartTime == null) {
                    TextButton(
                        onClick = {
                            AppSettings.currentBalance += rewardAmount
                            AppSettings.timerStartTime = System.currentTimeMillis()
                        }
                    ) {
                        Text("Получить средства")
                    }
                } else {
                    val hours = (timeRemaining / (60 * 60 * 1000)).toInt()
                    val minutes = ((timeRemaining / (60 * 1000)) % 60).toInt()
                    val seconds = ((timeRemaining / 1000) % 60).toInt()
                    Text("$hours:$minutes:$seconds")
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = {
                        navController.navigate("help_screen")
                    }
                ) {
                    Text("Справка")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Закрыть")
            }
        }
    )
}


