package ies.sequeros.pmdm.pokemonclean.application

import ies.sequeros.pmpd.pokemonclean.domain.Pokemon
import ies.sequeros.pmpd.pokemonclean.domain.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class RemovePokemonUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(pokemon: Pokemon) {
        return repository.removePokemon(pokemon)
    }
}