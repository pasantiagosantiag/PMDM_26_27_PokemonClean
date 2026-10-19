package ies.sequeros.pmpd.pokemonclean.ui.viewmodels.pokemonform

import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Avatar

data class PokemonFormState(
    val nombre: String = "",
    val descripcion: String = "",
    val avatar: Avatar? = null,
    val errorMessage: String? = null,
    val enabled: Boolean = false,

    ) {
    val isValid: Boolean
        get() = errorMessage == null &&
             //   avatar != null &&
                nombre.isNotBlank() &&
                descripcion.isNotBlank() &&
                avatar != null
}