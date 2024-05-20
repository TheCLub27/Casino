package com.example.slotmachine

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.random.Random

data class ReelItem(val value: Int, @DrawableRes val imageResource: Int)



fun generateRandomReelElements(): List<ReelItem> {
    val allElements = (0..10).toList()
    val uniqueElements = allElements.shuffled().take(3)

    return uniqueElements.map { randomValue ->
        val imageResource = when (randomValue) {
            0 -> R.drawable.axe
            1 -> R.drawable.bs
            2 -> R.drawable.bane
            3 -> R.drawable.od
            4 -> R.drawable.mayas
            5 -> R.drawable.sven
            6 -> R.drawable.tb
            7 -> R.drawable.sf
            8 -> R.drawable.wr
            9 -> R.drawable.faseless
            10 -> R.drawable.jag
            else -> R.drawable.grid_slot
        }
        ReelItem(randomValue, imageResource)
    }
}

@Composable
fun SlotReels(reels: List<List<ReelItem>>) {
    LazyRow(
        horizontalArrangement = Arrangement.Center
    ) {
        items(reels) { reel ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                reel.forEach { item ->
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .padding(4.dp)

                    ) {
                        Image(
                            painter = painterResource(id = item.imageResource),
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}

fun checkWinningCombos(reels: List<List<ReelItem>>): Boolean {
    // Определение линий выплат
    val payLines = listOf(
        listOf(1, 1, 1, 1, 1), // Горизонтальная линия посередине
        listOf(0, 0, 0, 0, 0), // Горизонтальная линия сверху
        listOf(2, 2, 2, 2, 2), // Горизонтальная линия снизу
        listOf(0, 1, 2, 1, 0), // Линия "V"
        listOf(2, 1, 0, 1, 2)  // Перевернутая "V"
    )

    // Проверка каждой линии выплат
    for (line in payLines) {
        val firstReelSymbol = reels[0][line[0]].value
        var count = 1

        for (i in 1 until line.size) {
            if (reels[i][line[i]].value == firstReelSymbol) {
                count++
            } else {
                break
            }
        }

        if (count >= 3) {
            return true
        }
    }

    return false
}

fun calculateWinningCoefficient(reels: List<List<ReelItem>>): Int {
    val payLines = listOf(
        listOf(1, 1, 1, 1, 1), // Горизонтальная линия посередине
        listOf(0, 0, 0, 0, 0), // Горизонтальная линия сверху
        listOf(2, 2, 2, 2, 2), // Горизонтальная линия снизу
        listOf(0, 1, 2, 1, 0), // Линия "V"
        listOf(2, 1, 0, 1, 2)  // Перевернутая "V"
    )

    val winCoefficients = mapOf(
        1 to listOf(1, 2, 95),
        2 to listOf(1, 2, 95),
        3 to listOf(1, 2, 95),
        4 to listOf(1, 2, 95),
        5 to listOf(1, 2, 95),
        6 to listOf(1, 2, 95),
        7 to listOf(1, 2, 95),
        8 to listOf(2, 4, 190),
        9 to listOf(5, 10, 475),
        0 to listOf(10, 20, 950),
        10 to listOf(20, 40, 2000),
    )

    for (line in payLines) {
        val firstReelSymbol = reels[0][line[0]].value
        var count = 1

        for (i in 1 until line.size) {
            if (reels[i][line[i]].value == firstReelSymbol) {
                count++
            } else {
                break
            }
        }

        if (count >= 3) {
            return winCoefficients[firstReelSymbol]?.get(count - 3) ?: 0
        }
    }

    return 0
}

@Composable
fun SlotMachineScreen(navController: NavController) {
    var currentBet by remember { mutableStateOf(10) }
    var reels by remember { mutableStateOf(List(5) { generateRandomReelElements() }) }
    var winningAmount by remember { mutableStateOf(0) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopPanel(navController = navController)
        SlotReels(reels = reels)
        WinningAmountField(winningAmount = winningAmount)
        BetFields(currentBet = currentBet, onBetChange = { currentBet = it })

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(8.dp)
            )
        }

        SpinButton(AppSettings.currentBalance, currentBet) {
            if (AppSettings.currentBalance >= currentBet) {
                // Обновление барабанов
                reels = List(5) { generateRandomReelElements() }

                // Обновление баланса: вычитание ставки
                AppSettings.currentBalance -= currentBet

                // Проверка выигрышных комбинаций
                if (checkWinningCombos(reels)) {
                    val coefficient = calculateWinningCoefficient(reels)
                    winningAmount = coefficient * currentBet
                    AppSettings.currentBalance += winningAmount
                } else {
                    winningAmount = 0
                }

                // Обновление прогресса по максимальным ставкам
                if (currentBet == AppSettings.getMaxBet()) {
                    AppSettings.updateLevelProgress()
                }

                errorMessage = ""
            } else {
                errorMessage = "Недостаточно средств для ставки!"
            }
        }
        ReturnToGamesButton(navController)
    }
}





@Composable
fun WinningAmountField(winningAmount: Int) {
    Text(
        text = "Winning Amount: $winningAmount",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
}

@Composable
fun BetFields(currentBet: Int, onBetChange: (Int) -> Unit) {
    val maxBet = AppSettings.getMaxBet()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = {
            if (currentBet > 10) {
                val newBet = when {
                    currentBet >= 1000 -> currentBet - 100
                    currentBet >= 100 -> currentBet - 50
                    else -> currentBet - 10
                }
                onBetChange(newBet)
            }
        }) {
            Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null)
        }

        BasicTextField(
            value = "Bet: $currentBet",
            onValueChange = { },
            modifier = Modifier.width(IntrinsicSize.Min)
        )

        TextButton(onClick = {
            if (currentBet < maxBet && currentBet < AppSettings.currentBalance) {
                val newBet = when {
                    currentBet >= 1000 -> currentBet + 100
                    currentBet >= 100 -> currentBet + 50
                    else -> currentBet + 10
                }
                onBetChange(newBet)
            }
        }) {
            Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null)
        }
    }
}





@Composable
fun SpinButton(currentBalance: Int, currentBet: Int, onSpinClicked: () -> Unit) {
    Button(
        onClick = {
            onSpinClicked()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text("Крутить")
    }
}

@Composable
fun ReturnToGamesButton(navController: NavController) {
    Button(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
            Text("Вернуться к играм")
        }
    }
}
