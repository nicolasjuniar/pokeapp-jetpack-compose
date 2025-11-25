package juniar.nicolas.pokeapp.jetpackcompose.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.BaseScreen

@Composable
fun PokemonDetailScreen(
    modifier: Modifier = Modifier,
    pokemonName: String,
    navController: NavController = rememberNavController(),
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(pokemonName) { viewModel.getDetailPokemon(pokemonName) }

    val detailPokemon by viewModel.detailPokemon.collectAsState(initial = null)
    detailPokemon?.let {
        BaseScreen(
            viewModel = viewModel,
            navController = navController,
            title = pokemonName
        ) {
            PokemonDetailContent(pokemon = it, modifier = modifier)
        }
    }
}

@Composable
fun PokemonDetailContent(pokemon: DetailPokemon, modifier: Modifier = Modifier) {
    val bgColor = typeColor(pokemon.types.firstOrNull())
    Surface(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = pokemon.imageUrl,
                        contentDescription = pokemon.name,
                        modifier = Modifier.size(180.dp),
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    pokemon.name.replaceFirstChar { it.uppercase() },
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "#${pokemon.id}",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoChip(label = "Height", value = "${pokemon.heightCm} cm")
                    InfoChip(label = "Weight", value = "${pokemon.weightKg} kg")
                    InfoChip(label = "Base XP", value = "${pokemon.baseExperience}")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Types", style = MaterialTheme.typography.titleMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    pokemon.types.forEach { TypeChip(it) }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Stats", style = MaterialTheme.typography.titleMedium)
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                pokemon.stats.forEach { stat ->
                    StatRow(name = stat.name, value = stat.value)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Ability", style = MaterialTheme.typography.titleMedium)
                Text(pokemon.abilities[0] ?: "-")
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun InfoChip(label: String, value: String) {
    Column(
        Modifier
            .background(Color(0xFFEFEFEF), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(label, fontSize = 12.sp, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TypeChip(name: String) {
    Box(
        Modifier
            .background(typeColor(name), RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(name.uppercase(), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun StatRow(
    name: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = value.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}


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