package ies.sequeros.pmdm.pokemonclean.application


import ies.sequeros.pmpd.pokemonclean.domain.Pokemon
import ies.sequeros.pmpd.pokemonclean.domain.PokemonRepository
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Avatar
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Descripcion
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Nombre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.DrawableResource
import kotlin.uuid.Uuid

class AddPokemonsUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(nombre: String,
                        descripcion: String,
                        avatar: Avatar, enable: Boolean = true): Unit {
        val pokemon = Pokemon.create(Uuid.random(),
            nombre,
            descripcion,
            avatar,
            enable)
        if (pokemon != null) {
            return repository.savePokemon(pokemon)
        } else
            throw IllegalArgumentException("Problemas al crear el pokemon")
    }
}