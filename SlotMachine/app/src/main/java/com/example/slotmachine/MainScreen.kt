package com.example.slotmachine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.slotmachine.viewmodel.MainViewModel
import com.example.slotmachine.ui.theme.SlotMachineTheme
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopPanel(navController = navController)

        SlotGrid(slots = listOf(
            SlotItem(iconResId = R.drawable.dota_slot, title = "Dota 2 Slots", onClick = {navController.navigate("slot_machine_screen_dota")}),
            SlotItem(iconResId = R.drawable.card_slot, title = "Card Slots", onClick = {}),
            SlotItem(iconResId = R.drawable.code_slot, title = "Code Slots", onClick = {}),
            SlotItem(iconResId = R.drawable.grid_slot, title = "#", onClick = {}),
            SlotItem(iconResId = R.drawable.grid_slot, title = "#", onClick = {}),
            SlotItem(iconResId = R.drawable.grid_slot, title = "#", onClick = {}),
            SlotItem(iconResId = R.drawable.grid_slot, title = "#", onClick = {}),
            SlotItem(iconResId = R.drawable.grid_slot, title = "#", onClick = {}),
        ))
    }
}

@Composable
fun SlotButton(iconResId: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Slot" /*style = MaterialTheme.typography.body2*/)
    }
}

