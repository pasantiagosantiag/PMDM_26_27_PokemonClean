package ies.sequeros.pmpd.pokemonclean

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "PokemonClean",
    ) {
        App()
    }
}