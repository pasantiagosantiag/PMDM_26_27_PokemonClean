package ies.sequeros.pmdm.pokemonclean.application

import ies.sequeros.pmpd.pokemonclean.domain.Pokemon
import ies.sequeros.pmpd.pokemonclean.domain.PokemonRepository
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.ElementId
import kotlin.uuid.Uuid

class GetPokemonByIdUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(id: ElementId): Pokemon? {
        return repository.getPokemonById(id)
    }
}