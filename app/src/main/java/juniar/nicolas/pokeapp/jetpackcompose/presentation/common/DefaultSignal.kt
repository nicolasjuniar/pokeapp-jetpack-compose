package juniar.nicolas.pokeapp.jetpackcompose.presentation.common

sealed interface DefaultSignal {
    data class ShowToast(val message: String) : DefaultSignal
}
