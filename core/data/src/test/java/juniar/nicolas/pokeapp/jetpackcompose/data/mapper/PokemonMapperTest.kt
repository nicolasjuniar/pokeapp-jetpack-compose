package juniar.nicolas.pokeapp.jetpackcompose.data.mapper

import juniar.nicolas.pokeapp.jetpackcompose.core.common.CommonConstant.POKEMON_IMAGE_URL
import juniar.nicolas.pokeapp.jetpackcompose.core.data.dto.DetailPokemonResponse
import juniar.nicolas.pokeapp.jetpackcompose.core.data.dto.NamedResource
import juniar.nicolas.pokeapp.jetpackcompose.core.data.dto.PokemonAbility
import juniar.nicolas.pokeapp.jetpackcompose.core.data.dto.PokemonStat
import juniar.nicolas.pokeapp.jetpackcompose.core.data.dto.PokemonType
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.entity.PokemonEntity
import juniar.nicolas.pokeapp.jetpackcompose.core.data.mapper.PokemonMapper
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Stat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonMapperTest {

    private val mapper = PokemonMapper()

    @Test
    fun `toDomain maps api response correctly`() {
        val response = DetailPokemonResponse(
            id = 1,
            name = "bulbasaur",
            height = 7,          // decimeter
            weight = 69,         // hectogram
            baseExperience = 64,
            types = listOf(
                PokemonType(
                    slot = 0,
                    type = NamedResource(name = "grass", url = "type/grass")
                )
            ),
            stats = listOf(
                PokemonStat(
                    baseStat = 45,
                    stat = NamedResource(name = "hp", url = "stat/hp")
                )
            ),
            abilities = listOf(
                PokemonAbility(
                    ability = NamedResource(name = "overgrow", url = "ability/overgrow"),
                    isHidden = false
                )
            )
        )

        val result = mapper.toDomain(response)

        assertEquals(1, result.id)
        assertEquals("bulbasaur", result.name)

        assertEquals("${POKEMON_IMAGE_URL}1.png", result.imageUrl)
        assertEquals(70, result.heightCm)
        assertEquals(6.9f, result.weightKg)

        assertEquals(listOf("grass"), result.types)
        assertEquals(listOf(Stat("hp", 45)), result.stats)
        assertEquals(
            listOf(
                juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.PokemonAbility(
                    abilityName = "overgrow",
                    isHidden = false
                )
            ),
            result.abilities
        )
    }

    @Test
    fun `toDomain maps PokemonEntity to Pokemon correctly`() {
        val entity = PokemonEntity(
            id = 25,
            name = "pikachu",
            url = "pokemon/pikachu"
        )

        val result = mapper.toDomain(entity)

        assertEquals(25, result.pokedexNumber)
        assertEquals("pikachu", result.name)
    }
}
