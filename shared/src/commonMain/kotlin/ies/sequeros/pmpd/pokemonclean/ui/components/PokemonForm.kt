package ies.sequeros.pmpd.pokemonclean.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Avatar
import ies.sequeros.pmpd.pokemonclean.ui.utils.PokemonAvatarMapper
import ies.sequeros.pmpd.pokemonclean.ui.viewmodels.pokemonform.PokemonFormState

@Composable
fun PokemonForm(
    state: PokemonFormState,
    canEdit: Boolean,
    onNombreChanged: (String) -> Unit,
    onDescripcionChanged: (String) -> Unit,
    onAvatarChange:(Avatar)->Unit,
    onEnabledChanged: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
    onCanEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(16.dp).weight(0.50f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Ejemplo de los componentes de la interfaz
            TextField(
                value = state.nombre,
                onValueChange = onNombreChanged,
                label = { Text("Nombre") },
                enabled = canEdit
            )

            TextField(
                value = state.descripcion,
                onValueChange = onDescripcionChanged,
                label = { Text("Descripción")  },
                enabled = canEdit
            )
            IconButton(
                onClick = onSaveClick,
                enabled = canEdit && state.isValid
            ) {

                Icon(Icons.Filled.Save, contentDescription = "Guardar")
            }
            Switch(
                checked = canEdit,
                onCheckedChange = {
                    onCanEditClick()
                }
            )

        }
        Row(modifier = Modifier.fillMaxWidth().weight(0.10f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Si hay error en las validaciones de los Value Objects, lo pintamos
            state.errorMessage?.let { error ->

                Text(modifier = Modifier.fillMaxHeight(), text = error, color = Color.Red)
            }
        }
        Row(modifier = Modifier.fillMaxWidth().weight(0.40f), horizontalArrangement = Arrangement.Center) {
            // Si hay error en las validaciones de los Value Objects, lo pintamos
            val pokemonsResource = PokemonAvatarMapper.getDrawableResources()
            PokemonImageList(pokemonsResource, state.avatar, onAvatarChange,
                enabled = canEdit)
        }
    }
}