package com.example.slotmachine

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slotmachine.ui.theme.SlotMachineTheme

data class SlotItem(val iconResId: Int, val title: String, val onClick: () -> Unit)

@Composable
fun SlotGrid(slots: List<SlotItem>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(slots.size) { index ->
            SlotItem(slot = slots[index], modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun SlotItem(slot: SlotItem, modifier: Modifier = Modifier) {
    val painter = painterResource(id = slot.iconResId)

    Surface(
        modifier = Modifier
            .clickable { slot.onClick() }
            .padding(16.dp),
        color = MaterialTheme.colorScheme.surface,
        contentColor = LocalContentColor.current
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(painter = painter, contentDescription = null )
            Text(text = slot.title,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center)
        }
    }
}