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
        "fire" -> Color(0xFFFD7D24)
        "water" -> Color(0xFF4592C4)
        "grass" -> Color(0xFF9BCC50)
        "electric" -> Color(0xFFF9CF30)
        "psychic" -> Color(0xFFFA8581)
        "ice" -> Color(0xFF51C4E7)
        "dragon" -> Color(0xFF0B6DC3)
        "dark" -> Color(0xFF707070)
        "fairy" -> Color(0xFFF4BDC9)
        "normal" -> Color(0xFFAAAA99)
        "ground" -> Color(0xFFE0C068)
        "rock" -> Color(0xFFB8A038)
        "bug" -> Color(0xFFA8B820)
        "fighting" -> Color(0xFFC03028)
        "poison" -> Color(0xFFA040A0)
        "ghost" -> Color(0xFF705898)
        "steel" -> Color(0xFFB8B8D0)
        else -> Color(0xFFEEEEEE)
    }
}