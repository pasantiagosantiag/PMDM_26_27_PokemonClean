package ies.sequeros.pmpd.pokemonclean.infraestructure

import ies.sequeros.pmpd.pokemonclean.domain.Pokemon
import ies.sequeros.pmpd.pokemonclean.domain.PokemonRepository
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Avatar
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.ElementId
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Nombre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import pokemonclean.shared.generated.resources.Res
import pokemonclean.shared.generated.resources.*
import kotlin.uuid.Uuid

class InMemoryRepository: PokemonRepository {
    //como esta en memoria se deja un estado, pero si viene de otro sitio
    //un flow
    private val _pokemons: MutableStateFlow<List<Pokemon>> = MutableStateFlow(
        listOfNotNull(
            Pokemon.create(
                Uuid.parse("550e8400-e29b-41d4-a716-446655440000"),
                "Pikachu",
                "El ratón eléctrico, mascota de la franquicia",
                Avatar.BOLA_DEFECTO
               
            ),
            Pokemon.create(
                Uuid.parse("550e8400-e29b-41d4-a716-446655440001"),
                "Bulbasaur",
                "Tiene una semilla en el lomo desde que nace",
                Avatar.CHARIZARD
            ),
            Pokemon.create(
                Uuid.parse("550e8400-e29b-41d4-a716-446655440002"),
                "Charmander",
                "La llama de su cola indica su estado de salud",
                Avatar.SQUIRTLE
            ),
            Pokemon.create(
                Uuid.parse("550e8400-e29b-41d4-a716-446655440003"),
                "Squirtle",
                "Se refugia en su caparazón ante el peligro",
                Avatar.BULBASAUR
            ),
            Pokemon.create(
                Uuid.parse("550e8400-e29b-41d4-a716-446655440004"),
                "Jigglypuff",
                "Canta nanas hasta dormir a sus oyentes",
                Avatar.SQUIRTLE
            ),
            Pokemon.create(
                Uuid.parse("550e8400-e29b-41d4-a716-446655440005"),
                "Meowth",
                "Adora las cosas brillantes y relucientes",
                Avatar.CHARIZARD
            ),
            Pokemon.create(
                Uuid.parse("550e8400-e29b-41d4-a716-446655440006"),
                "Psyduck",
                "Sufre dolores de cabeza constantes",
                Avatar.PIKACHU
            ),
            Pokemon.create(
                Uuid.parse("550e8400-e29b-41d4-a716-446655440007"),
                "Machop",
                "Levanta pesas para entrenar sus músculos",
                Avatar.BULBASAUR            ),
            Pokemon.create(
                Uuid.parse("550e8400-e29b-41d4-a716-446655440008"),
                "Gengar",
                "Se esconde en las sombras de sus víctimas",
                Avatar.BOLA_DEFECTO
            ),
        )
    )
    override fun getAllPokemons(): Flow<List<Pokemon>> {
        return this._pokemons
    }
    override fun getPokemonById(id: ElementId): Pokemon? {
        return _pokemons.value.firstOrNull { it.getId().value == id.value }
    }


    override fun removePokemonById(id: ElementId) {
        _pokemons.update { lista ->
            lista.filter { pokemon -> id.value != pokemon.getId().value }
        }
    }
    override fun removePokemonByName(name: Nombre) {
        _pokemons.update { lista ->
            lista.filter { pokemon -> name.value != pokemon.getNombre()?.value }
        }
    }
    override fun removeAllPokemons() {
        _pokemons.update { emptyList() }
    }
    override fun removePokemon(pokemon: Pokemon) {
        _pokemons.update { lista ->
            lista.filter { pokemon2 -> pokemon.getId().value != pokemon2.getId().value }
        }
    }

    override fun savePokemon(pokemon: Pokemon) {
        val existingPokemon = getPokemonById(pokemon.getId())
        if (existingPokemon != null) {
            _pokemons.update { lista ->
                lista.map { current ->
                    if (current.getId().value == pokemon.getId().value) pokemon else current
                }
            }
        } else {
            _pokemons.update { lista ->
                lista + pokemon
            }
        }
    }
}