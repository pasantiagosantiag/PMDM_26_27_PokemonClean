package ies.sequeros.pmpd.pokemonclean.ui.viewmodels.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.pmdm.pokemonclean.application.*
import ies.sequeros.pmpd.pokemonclean.domain.Pokemon
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Avatar
import ies.sequeros.pmpd.pokemonclean.ui.viewmodels.pokemonform.PokemonFormState
import kotlinx.coroutines.flow.*

class PokemonsViewModel(
    private val getAllPokemonsUseCase: GetAllPokemonsUseCase,
    private val updatePokemonsUseCase: UpdatePokemonsUseCase,
    private val getPokemonByIdUseCase: GetPokemonByIdUseCase,
    private val addPokemonUseCase: AddPokemonsUseCase,
    private val removePokemonUseCase: RemovePokemonUseCase
) : ViewModel() {

    private val _formState: MutableStateFlow<PokemonFormState> = MutableStateFlow(PokemonFormState())
    val formState: StateFlow<PokemonFormState> = _formState

    private val _selected: MutableStateFlow<Pokemon?> = MutableStateFlow(null)
    val selected: StateFlow<Pokemon?> = _selected
    private val _canEdit: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val canEdit: StateFlow<Boolean> = _canEdit

    private val _searchFilter: MutableStateFlow<String> = MutableStateFlow("")
    val searchFilter: StateFlow<String> = _searchFilter
    val pokemons = combine(getAllPokemonsUseCase.invoke(), _searchFilter) { pokemons, searchFilter ->
        if (searchFilter.isBlank()) {
            pokemons
        } else {
            pokemons.filter {
                it.getNombre()?.value?.contains(searchFilter, ignoreCase = true) ?: false ||
                        it.getDescripcion()?.value?.contains(searchFilter, ignoreCase = true) ?: false
            }
        }
    }.stateIn(
        scope = viewModelScope, // El scope del ViewModel, gestor de corrutinas,operaciones sencillas
        //se ejecuta en el hilo principal
        started = SharingStarted.WhileSubscribed(5000), // Mantiene activo el flujo 5s tras perder observadores
        initialValue = emptyList() // Valor inicial mientras emite el caso de uso
    )

    fun setSelectedPokemon(pokemon: Pokemon?) {
        _selected.update { pokemon }
        _formState.update {
            if (pokemon == null) {
                PokemonFormState()
            } else {
                this.pokemonToState(pokemon = pokemon)
            }
        }
    }

    fun removePokemon(pokemon: Pokemon) {
        removePokemonUseCase.invoke(pokemon = pokemon)
        val originalUuid = pokemon.getId().value
        val selectedId = _selected.value?.getId()?.value
        if (selectedId != null &&
            selectedId == originalUuid
        ) {
            setSelectedPokemon(null)
        }
    }

    private fun pokemonToState(pokemon: Pokemon): PokemonFormState {
        return PokemonFormState(
            nombre = pokemon.getNombre()?.value ?: "",
            descripcion = pokemon.getDescripcion()?.value ?: "",
            avatar = pokemon.getAvatar(),
            enabled = pokemon.isEnabled()

        )
    }

    fun onSearchQueryChanged(query: String) {
        _searchFilter.value = query
    }

    fun onAvatarChange(avatar: Avatar) {
        _formState.update {
            _formState.value.copy(avatar = avatar)
        }
    }

    fun onNombreChanged(nuevoNombre: String) {
        _formState.update {
            val error = validateNombre(nuevoNombre)
            _formState.value.copy(
                nombre = nuevoNombre,
                errorMessage = error
            )
        }
    }

    private fun validateNombre(nuevoNombre: String): String? {
        if (nuevoNombre.isBlank()) {
            return "El nombre no puede estar vacio"
        } else if (nuevoNombre.length < 10) {
            return "El nombre es demasiado corto"
        } else if (nuevoNombre.length > 50)
            return "El nombre es demasiado largo"
        return null
    }

    fun onCanEditChanged(canEdit: Boolean) {
        _canEdit.value = canEdit
    }

    fun onDescripcionChanged(nuevaDescripcion: String) {
        _formState.value = _formState.value.copy(descripcion = nuevaDescripcion)
    }

    fun onEnabledChanged(nuevoEstado: Boolean) {
        _formState.value = _formState.value.copy(enabled = nuevoEstado)
    }

    fun setEditable() {
        _canEdit.value = true
    }

    fun setUnEditable() {
        _canEdit.value = false
    }

    fun save() {
        if (formState.value.isValid) {
            try {
                val isEditing = selected.value != null

                if (isEditing) {
                    updatePokemonsUseCase.invoke(
                        id = selected.value!!.getId().id!!,
                        nombre = formState.value.nombre,
                        descripcion = formState.value.descripcion,
                        avatar = formState.value.avatar ?: Avatar.BOLA_DEFECTO,
                        enable = formState.value.enabled
                    )
                } else {
                    addPokemonUseCase.invoke(
                        nombre = formState.value.nombre,
                        descripcion = formState.value.descripcion,
                        avatar = formState.value.avatar ?: Avatar.BOLA_DEFECTO,
                        enable = formState.value.enabled
                    )
                }


                setSelectedPokemon(null)

                setUnEditable()

            } catch (e: IllegalArgumentException) {
                _formState.value = _formState.value.copy(errorMessage = e.message)
            }
        }
    }
}


