package juniar.nicolas.pokeapp.jetpackcompose.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<State, Event, Signal>(initialState: State) :
    ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state

    private val _signal = MutableSharedFlow<Signal>()
    val signal: SharedFlow<Signal> = _signal

    protected fun setState(reducer: State.() -> State) {
        _state.value = _state.value.reducer()
    }

    protected suspend fun sendSignal(signal: Signal) {
        _signal.emit(signal)
    }

    protected suspend fun sendSignal(listSignal: List<Signal>) {
        listSignal.forEach {
            _signal.emit(it)
        }
    }

    fun onEvent(event: Event) {
        handleEvent(event)
    }

    protected open fun handleEvent(event: Event) {}
}
