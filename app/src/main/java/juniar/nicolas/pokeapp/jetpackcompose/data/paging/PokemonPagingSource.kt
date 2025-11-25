package juniar.nicolas.pokeapp.jetpackcompose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import juniar.nicolas.pokeapp.jetpackcompose.data.api.PokeApi
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Pokemon
import java.io.IOException

class PokemonPagingSource(
    private val api: PokeApi,
    private val pageSize: Int = 10
) : PagingSource<Int, Pokemon>() {

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            anchorPage?.prevKey?.plus(pageSize) ?: anchorPage?.nextKey?.minus(pageSize)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val offset = params.key ?: 0
        return try {
            val response = api.getPokemons(offset = offset, limit = pageSize)
            val items: List<Pokemon> = response.results.map { dto ->
                Pokemon(
                    name = dto.name,
                    pokedexNumber = parsePokedexNumberFromUrl(dto.url)
                )
            }
            val nextOffset =
                if (response.next != null && items.isNotEmpty()) offset + pageSize else null

            LoadResult.Page(
                data = items,
                prevKey = if (offset == 0) null else offset - pageSize,
                nextKey = nextOffset
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

fun parsePokedexNumberFromUrl(url: String): Int {
    return url.trimEnd('/').split('/').last().toIntOrNull() ?: -1
}