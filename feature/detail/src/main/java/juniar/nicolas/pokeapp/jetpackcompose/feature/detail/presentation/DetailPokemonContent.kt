package juniar.nicolas.pokeapp.jetpackcompose.feature.detail.presentation

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.PokemonAbility
import juniar.nicolas.pokeapp.jetpackcompose.core.ui.component.BaseScaffold
import juniar.nicolas.pokeapp.jetpackcompose.core.ui.theme.typeColor

@Composable
fun DetailPokemonContent(
    modifier: Modifier = Modifier,
    state: DetailPokemonState = DetailPokemonState(),
    onEvent: (DetailPokemonEvent) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    state.detailPokemon?.let { pokemon ->
        BaseScaffold(
            modifier = modifier,
            onBackClick = {
                onBackClick()
            },
            isLoading = false
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    PokemonHeader(clickFavoriteIcon = {
                        onEvent(DetailPokemonEvent.ClickFavoriteIcon)
                    }, isFavorite = state.isFavorite, pokemon = state.detailPokemon)
                }

                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        HorizontalValueContainer("Height", "${pokemon.heightCm} cm")
                        HorizontalValueContainer("Weight", "${pokemon.weightKg} kg")
                        HorizontalValueContainer("Base XP", "${pokemon.baseExperience}")
                    }
                }

                item {
                    Text("Types", style = MaterialTheme.typography.titleMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        pokemon.types.forEach { PokemonType(it) }
                    }
                }

                item {
                    Text("Abilities", style = MaterialTheme.typography.titleMedium)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        tonalElevation = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = pokemon.abilities.splitAbility(),
                            color = Color.Black.copy(alpha = 0.85f),
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
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
                            HorizontalValueContainer(
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
                            HorizontalValueContainer(
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
}

@Composable
fun PokemonHeader(
    clickFavoriteIcon: () -> Unit,
    isFavorite: Boolean,
    pokemon: DetailPokemon
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = { clickFavoriteIcon() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .zIndex(2f)
                    .padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(28.dp)
                )
            }
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.Center)
                    .fillMaxWidth()
            )
        }

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
fun HorizontalValueContainer(label: String, value: String, modifier: Modifier = Modifier) {
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
fun PokemonType(name: String) {
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

fun List<PokemonAbility>.splitAbility() = joinToString(" - ") {
    it.abilityName + if (it.isHidden) " (Hidden Ability)" else ""
}

fun formatPokedexId(id: Int): String {
    return "#${id.toString().padStart(3, '0')}"
}

@Preview
@Composable
fun DetailPokemonContentPreview() {
    DetailPokemonContent()
}