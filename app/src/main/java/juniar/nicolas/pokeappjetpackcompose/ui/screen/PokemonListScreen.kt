package juniar.nicolas.pokeappjetpackcompose.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import juniar.nicolas.pokeappjetpackcompose.ui.viewmodel.PokemonListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(vm: PokemonListViewModel = viewModel()) {
    val pokemons by vm.pokemons.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Pokédex") }) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(pokemons) { (id, name) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("#$id")
                    Text(name)
                }
                Divider()
            }
        }
    }
}