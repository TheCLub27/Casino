package com.example.slotmachine

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.slotmachine.ui.theme.SlotMachineTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import kotlin.math.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object AppSettings {
    var currentBalance by mutableStateOf(2000)
    var currentLevel by mutableStateOf(1)
    var maxSpins by mutableStateOf(0)

    var timerStartTime by mutableStateOf<Long?>(null)
    var timeRemaining by mutableStateOf(0L)

    fun getMaxBet(): Int {
        return 50 * (2.0.pow(currentLevel - 1)).toInt()
    }

    fun getSpinsForLevel(): Int {
        return 20 + 5 * (currentLevel - 1)
    }

    fun updateLevelProgress() {
        if (maxSpins < getSpinsForLevel()) {
            maxSpins++
        } else {
            currentLevel++
            maxSpins = 0
        }
    }

    fun getProgress(): Float {
        return maxSpins / getSpinsForLevel().toFloat()
    }

    fun getSpinsText(): String {
        return "$maxSpins / ${getSpinsForLevel()}"
    }
}


class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        loadSettings()
        setContent {
            SlotMachineApp()
        }
    }

    private fun loadSettings() {
        AppSettings.currentBalance = sharedPreferences.getInt("currentBalance", 2000)
        AppSettings.currentLevel = sharedPreferences.getInt("currentLevel", 1)
        AppSettings.maxSpins = sharedPreferences.getInt("maxSpins", 0)
        AppSettings.timerStartTime = sharedPreferences.getLong("timerStartTime", 0L).takeIf { it != 0L }
        AppSettings.timeRemaining = sharedPreferences.getLong("timeRemaining", 0L)

    }

    private fun saveSettings() {
        sharedPreferences.edit {
            putInt("currentBalance", AppSettings.currentBalance)
            putInt("currentLevel", AppSettings.currentLevel)
            putInt("maxSpins", AppSettings.maxSpins)
            putLong("timerStartTime", AppSettings.timerStartTime ?: 0L)
            putLong("timeRemaining", AppSettings.timeRemaining)
        }
    }

    override fun onStop() {
        super.onStop()
        saveSettings()
    }
}


@Composable
fun SlotMachineApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main_screen"
    ) {
        composable("main_screen") {
            MainScreen(navController = navController)
        }
        composable("slot_machine_screen_dota") {
            SlotMachineScreen(navController = navController,)
        }
        composable("help_screen"){
            HelpScreen(navController = navController)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SlotMachineAppPreview (){
    SlotMachineTheme {
        SlotMachineApp()
    }
}