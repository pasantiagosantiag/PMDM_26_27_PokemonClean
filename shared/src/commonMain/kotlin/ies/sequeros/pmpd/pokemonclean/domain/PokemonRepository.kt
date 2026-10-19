package ies.sequeros.pmpd.pokemonclean.domain

import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.ElementId
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Nombre
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

interface  PokemonRepository {
    fun getPokemonById(id: ElementId): Pokemon?
    fun getAllPokemons(): Flow<List<Pokemon>>
    fun removePokemonById(id: ElementId)
    fun removePokemonByName(name: Nombre)
    fun removeAllPokemons()
    fun removePokemon(pokemon: Pokemon)
    fun savePokemon(pokemon: Pokemon)
}