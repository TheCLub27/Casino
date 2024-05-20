// HelpScreen.kt
package com.example.slotmachine

import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HelpScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Справка",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Добро пожаловать в мое приложение игровых автоматов Slot Machine Overdrive (SMO).",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Обо мне",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "На момент создания приложения студент 3-его курса факультета прикладной информатики в прекрасном вузе КубГАУ. Приложение было создано исключительно в учебных и развлекательных целях.",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.myself),
            contentDescription = "Описание изображения",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Как играть:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "1. Сделайте ставку.\n2. Нажмите кнопку 'Крутить'.\n3. Ожидайте результата.",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Ниже приведена таблица с описанием слотов и коэффицентами",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Table()



        Text(
            text = "Правила игры:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Автомат имеет 5 выйгрышных линий, использующих количество слотов в комбинации от 3 до 5. Сами выйгрышные линии расположены следующим образом: по горизантали -> верхний, средний, нижний ряды, ∨ и ∧ -образные линии. Максимальная ставка пользователя ограничена его уровнем. Для повышения уровня необходимо совершить указанное в верхнем левом углу количество вращений на максимальной ставке. Если у игрока закончился баланс, то в окне настроек расположена кнопка 'Получить средства'. Нажав на нее, игрок получит на свой баланс сумму равную половине от того, сколько требуется для повышения текущего уровня с нуля. Данная функция доступна раз в 2 часа. Удачной игры. Если захотите поблагодарить за такой отличный игровой автомат по доке 2, то отблагодарить создателя можно ниже",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LinkText ()

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
    }
}

@Composable
fun LinkText() {
    val context = LocalContext.current
    val annotatedString = buildAnnotatedString {
        val linkText = "Наборы, кейсы, что-нибудь подобное"
        val linkUrl = "https://steamcommunity.com/tradeoffer/new/?partner=768845306&token=rjp_vbfP"

        pushStringAnnotation(tag = "URL", annotation = linkUrl)
        withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
            append(linkText)
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                    context.startActivity(intent)
                }
        }
    )
}


@Composable
fun Table() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TableRow("Комбинация \nиз 3 слотов", "Размер выплаты", "Шанс")
        TableRow(content = R.drawable.bane, payoutSize = "", chance = "")
        TableRow(content = R.drawable.bs, payoutSize = "", chance = "")
        TableRow(content = R.drawable.mayas, payoutSize = "", chance = "")
        TableRow(content = R.drawable.od, payoutSize = "x1", chance = "0,07041")
        TableRow(content = R.drawable.sf, payoutSize = "", chance = "")
        TableRow(content = R.drawable.sven, payoutSize = "", chance = "")
        TableRow(content = R.drawable.tb, payoutSize = "", chance = "")
        TableRow("Комбинация из \n4 этих слотов", "x2", "0,01565")
        TableRow("Комбинация из \n5 этих слотов", "x95", "0,00347")
        TableRow("Комбинация из \n3, 4, 5 слотов", "Размер выплаты", "Шанс")
        TableRow(content = R.drawable.wr, "x2", "0,01006")
        TableRow(content = R.drawable.wr, "x4", "0,00224")
        TableRow(content = R.drawable.wr, "x190", "0,000497")
        TableRow("Комбинация из \n3, 4, 5 слотов", "Размер выплаты", "Шанс")
        TableRow(content = R.drawable.faseless, "x5", "0,01006")
        TableRow(content = R.drawable.faseless, "x10", "0,00224")
        TableRow(content = R.drawable.faseless, "x475", "0,000497")
        TableRow("Комбинация из \n3, 4, 5 слотов", "Размер выплаты", "Шанс")
        TableRow(content = R.drawable.axe, "x10", "0,0007")
        TableRow(content = R.drawable.axe, "x20", "0,000062")
        TableRow(content = R.drawable.axe, "x950", "0,0000062")
    }
}



@Composable
fun TableRow(content: Any, payoutSize: String, chance: String) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 4.dp)
            .fillMaxWidth(),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (content) {
            is Int -> {
                Image(
                    painter = painterResource(id = content),
                    contentDescription = null,
                    modifier = Modifier.size(34.dp) // Размер изображения
                )
            }
            is String -> {
                Text(
                    text = content,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Text(
            text = payoutSize,
            modifier = Modifier.padding(start = 8.dp)
        )

        Text(
            text = chance,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}









