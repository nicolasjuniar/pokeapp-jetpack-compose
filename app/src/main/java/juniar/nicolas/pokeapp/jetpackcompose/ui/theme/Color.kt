package juniar.nicolas.pokeapp.jetpackcompose.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

fun typeColor(type: String?): Color {
    return when (type?.lowercase()) {
        "normal" -> Color(0xFFA1A1A1)
        "fire" -> Color(0xFFD53A30)
        "fighting" -> Color(0xFFF08833)
        "water" -> Color(0xFF4C79BC)
        "flying" -> Color(0xFF8EB8E4)
        "grass" -> Color(0xFF5D9D3C)
        "ghost" -> Color(0xFF6D4B97)
        "electric" -> Color(0xFFF2C341)
        "ground" -> Color(0xFF895229)
        "psychic" -> Color(0xFFDC4D79)
        "rock" -> Color(0xFFADA984)
        "ice" -> Color(0xFF78CCF0)
        "bug" -> Color(0xFF95A135)
        "dragon" -> Color(0xFF4C60A9)
        "poison" -> Color(0xFF6B426D)
        "dark" -> Color(0xFF4E403F)
        "steel" -> Color(0xFF74A2B9)
        "fairy" -> Color(0xFFBA80B5)
        else -> Color(0xFFEEEEEE)
    }
}
