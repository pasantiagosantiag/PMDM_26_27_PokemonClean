package ies.sequeros.pmpd.pokemonclean.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ies.sequeros.pmpd.pokemonclean.domain.Pokemon
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Avatar
import ies.sequeros.pmpd.pokemonclean.ui.utils.PokemonAvatarMapper
import org.jetbrains.compose.resources.painterResource

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    list: List<Pokemon>,
    selectedItem: Pokemon? = null,
    searchQuery: String = "",
    onSearchQueryChanged: (String) -> Unit = {},
    onSelected: (pokemon: Pokemon) -> Unit,
    onDelete: (pokemon: Pokemon) -> Unit,

    ) {

    Column(modifier = modifier.fillMaxWidth()) {

        // Cuadro de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { onSearchQueryChanged(it) }, // Se comunica hacia arriba
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Buscar Pokémon...") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(list.size) {
                var cardModifier = Modifier.padding(4.dp)
                if (selectedItem != null
                    &&
                    selectedItem.getId().value == list[it].getId().value
                ) {
                    cardModifier = cardModifier.border(2.dp, Color.Black)
                }
                OutlinedCard(
                    modifier = cardModifier,
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(
                                PokemonAvatarMapper.toDrawable(
                                    list[it].getAvatar() ?: Avatar.BOLA_DEFECTO
                                )
                            ),
                            contentDescription = list[it].getNombre()?.value ?: "",
                            modifier = Modifier.size(56.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = list[it].getNombre()?.value ?: "",
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            onSelected(list[it])
                        }) {
                            Icon(
                                imageVector = Icons.Default.TouchApp,
                                contentDescription = "Seleccionar"
                            )
                        }
                        IconButton(onClick = {
                            onDelete(list[it])

                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar"
                            )
                        }
                    }
                }


            }
        }

    }
}