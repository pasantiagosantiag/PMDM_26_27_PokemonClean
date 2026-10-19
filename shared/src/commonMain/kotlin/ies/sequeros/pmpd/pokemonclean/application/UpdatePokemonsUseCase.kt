package ies.sequeros.pmdm.pokemonclean.application


import ies.sequeros.pmpd.pokemonclean.domain.Pokemon
import ies.sequeros.pmpd.pokemonclean.domain.PokemonRepository
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Avatar
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Descripcion
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.ElementId
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Nombre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.DrawableResource
import kotlin.uuid.Uuid

class UpdatePokemonsUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(id: Uuid, nombre: String, descripcion: String,
                        avatar: Avatar, enable: Boolean = true): Unit {
        val original=repository.getPokemonById(ElementId.from(id))
        if (original != null) {
            val nuevoNombre=Nombre.from(nombre)
            val nuevaDescripcion=Descripcion.from(descripcion)
            if (nuevoNombre == null || nuevaDescripcion == null) {
                throw IllegalArgumentException("Los datos proporcionados para actualizar el Pokémon no cumplen las reglas de validación.")
            }
            val nuevo=original.copy(nombre = nuevoNombre,
                descripcion = nuevaDescripcion, avatar = avatar, enabled = enable)
            repository.savePokemon(nuevo)
        }
        else
            throw IllegalArgumentException("Problemas al actualizar")
    }
}