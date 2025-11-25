package juniar.nicolas.pokeapp.jetpackcompose.domain.model

data class DetailPokemon(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val heightCm: Int,
    val weightKg: Float,
    val baseExperience: Int,
    val types: List<String>,
    val stats: List<Stat>,
    val abilities: List<String>,
)

data class Stat(val name: String, val value: Int)