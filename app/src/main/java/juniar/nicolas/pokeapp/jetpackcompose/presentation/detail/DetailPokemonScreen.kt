package juniar.nicolas.pokeapp.jetpackcompose.presentation.detail

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import juniar.nicolas.pokeapp.jetpackcompose.data.dto.PokemonAbility
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.BaseScreen
import juniar.nicolas.pokeapp.jetpackcompose.ui.theme.typeColor

@Composable
fun PokemonDetailScreen(
    modifier: Modifier = Modifier,
    pokedexNumber: Int,
    navController: NavController = rememberNavController(),
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(pokedexNumber) { viewModel.getDetailPokemon(pokedexNumber) }

    val detailPokemon by viewModel.detailPokemon.collectAsState(initial = null)
    detailPokemon?.let {
        BaseScreen(
            viewModel = viewModel,
            navController = navController
        ) {
            PokemonDetailContent(pokemon = it, modifier = modifier)
        }
    }
}

@Composable
fun PokemonDetailContent(pokemon: DetailPokemon, modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { PokemonHeader(pokemon) }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoChip("Height", "${pokemon.heightCm} cm")
                    InfoChip("Weight", "${pokemon.weightKg} kg")
                    InfoChip("Base XP", "${pokemon.baseExperience}")
                }
            }

            item {
                Text("Types", style = MaterialTheme.typography.titleMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    pokemon.types.forEach { TypeChip(it) }
                }
            }

            item {
                Text("Abilities", style = MaterialTheme.typography.titleMedium)
                AbilitySection(pokemon.abilities.splitAbility())
            }

            item {
                Text("Stats", style = MaterialTheme.typography.titleMedium)
                val total = pokemon.stats.sumOf { it.value }

                Text(
                    text = "Base Stat Total: $total",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF222222),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    pokemon.stats.take(3).forEach { stat ->
                        InfoChip(
                            label = stat.name,
                            value = stat.value.toString(),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    pokemon.stats.takeLast(3).forEach { stat ->
                        InfoChip(
                            label = stat.name,
                            value = stat.value.toString(),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonHeader(pokemon: DetailPokemon) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = pokemon.imageUrl,
            contentDescription = pokemon.name,
            modifier = Modifier.size(180.dp)
        )

        Spacer(Modifier.height(16.dp))

        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${formatPokedexId(pokemon.id)} ${pokemon.name.replaceFirstChar { it.uppercase() }}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
        }
    }
}

@Composable
fun InfoChip(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, style = MaterialTheme.typography.labelSmall)
            Text(value, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun TypeChip(name: String) {
    Surface(
        color = typeColor(name),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            name.uppercase(),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun AbilitySection(text: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            color = Color.Black.copy(alpha = 0.85f),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

fun List<PokemonAbility>.splitAbility() = joinToString(" - ") {
    it.ability.name + if (it.isHidden) " (Hidden Ability)" else ""
}

fun formatPokedexId(id: Int): String {
    return "#${id.toString().padStart(3, '0')}"
}