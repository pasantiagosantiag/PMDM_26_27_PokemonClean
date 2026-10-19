package ies.sequeros.pmpd.pokemonclean

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ies.sequeros.pmdm.pokemonclean.application.AddPokemonsUseCase
import ies.sequeros.pmdm.pokemonclean.application.GetAllPokemonsUseCase
import ies.sequeros.pmdm.pokemonclean.application.GetPokemonByIdUseCase
import ies.sequeros.pmdm.pokemonclean.application.RemovePokemonUseCase
import ies.sequeros.pmdm.pokemonclean.application.UpdatePokemonsUseCase
import ies.sequeros.pmpd.pokemonclean.domain.PokemonRepository
import ies.sequeros.pmpd.pokemonclean.infraestructure.InMemoryRepository
import ies.sequeros.pmpd.pokemonclean.ui.components.PokemonForm
import ies.sequeros.pmpd.pokemonclean.ui.components.PokemonList
import ies.sequeros.pmpd.pokemonclean.ui.viewmodels.app.PokemonsViewModel


class AppContainer {
    val pokemonRepository: PokemonRepository = InMemoryRepository()
    val getAllPokemonsUseCase = GetAllPokemonsUseCase(pokemonRepository)
    val addPokemonsUseCase = AddPokemonsUseCase(pokemonRepository)
    val removePokemonUseCase = RemovePokemonUseCase(pokemonRepository)
    val getPokemonByIdUseCase = GetPokemonByIdUseCase(pokemonRepository)
    val updatePokemonsUseCase = UpdatePokemonsUseCase(pokemonRepository)
}

@Composable
@Preview
fun App() {
    val appContainer = remember { AppContainer() }
    //se definen el view model
    val pokemonsViewModel = viewModel<PokemonsViewModel> {
        PokemonsViewModel(
            appContainer.getAllPokemonsUseCase,
            appContainer.updatePokemonsUseCase,
            appContainer.getPokemonByIdUseCase,
            appContainer.addPokemonsUseCase,
            appContainer.removePokemonUseCase,
        )
    }
    //se obtienen los estados de los viewmodels, son flows transformados
    //listado
    val pokemonsState by pokemonsViewModel.pokemons.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val current by pokemonsViewModel.selected.collectAsState()
    //estado del formulario, para pasarselo
    val formState by pokemonsViewModel.formState.collectAsState()
    //igual para poder editar
    val canEdit by pokemonsViewModel.canEdit.collectAsState()


    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            //el formulario, se le pasan los métodos para cambiar el estado
            PokemonForm(
                formState,
                canEdit,
                { pokemonsViewModel.onNombreChanged(it) },
                { pokemonsViewModel.onDescripcionChanged(it) },
                {
                    pokemonsViewModel.onAvatarChange(it)
                },
                { pokemonsViewModel.onEnabledChanged(it) },
                { pokemonsViewModel.save() },
                {
                    pokemonsViewModel.onCanEditChanged(canEdit.not())
                },
                Modifier.fillMaxWidth().fillMaxHeight(0.40f),
            )
            //la lista de pokemons, se le pasa el seleccionado y la lambda para borrar
            PokemonList(modifier = Modifier.fillMaxSize().fillMaxHeight(0.60f),
                list = pokemonsState,
                selectedItem = current,
                searchQuery = pokemonsViewModel.searchFilter.value,
                onSearchQueryChanged = { searchQuery ->
                    pokemonsViewModel.onSearchQueryChanged(searchQuery)
                },
                onSelected = {
                pokemonsViewModel.setSelectedPokemon(it)


            }, onDelete = {
                pokemonsViewModel.removePokemon(it)
            })
        }
    }
}