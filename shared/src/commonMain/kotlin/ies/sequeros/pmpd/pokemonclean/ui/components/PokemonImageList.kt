package ies.sequeros.pmpd.pokemonclean.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Avatar
import ies.sequeros.pmpd.pokemonclean.ui.utils.PokemonAvatarMapper
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun PokemonImageList(
    pokemonsResource: List<DrawableResource>, selected: Avatar?,
    onSelect: (avatar: Avatar) -> Unit,
    enabled: Boolean=false
) { // Reemplaza 'Any' por tu tipo de datos
    val drawableIds = PokemonAvatarMapper.getDrawableResources()
    //cambiar a estado para recomposicion
    val selectedState = mutableStateOf(selected?.let {
        PokemonAvatarMapper.toDrawable(selected)
    })
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize()
    ) {
        // En Compose es mejor usar 'itemsIndexed' en lugar de un bucle 'for' tradicional
        itemsIndexed(pokemonsResource) { index, pokemon ->
            if (index < drawableIds.size) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        // Fondo gris claro o secundario para resaltar el avatar
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                        // Hace que el avatar reaccione visualmente al tocarlo (efecto Ripple)
                        .clickable {
                            onSelect(PokemonAvatarMapper.toAvatar(drawableIds[index]))
                        }
                        .padding(8.dp), // Margen interno entre el fondo y la imagen
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(drawableIds[index]),
                        contentDescription = "Avatar de Pokémon",

                        modifier = Modifier.fillMaxHeight().sizeIn(128.dp).
                        alpha(if (enabled) 1.0f else 0.5f).
                        clickable(
                            enabled = enabled,
                            onClick = {

                                    onSelect(PokemonAvatarMapper.toAvatar((drawableIds[index])))

                            })
                            // Agrega el borde solo si el id actual coincide con el seleccionado
                            .then(
                                if (selectedState.value != null &&
                                    selectedState.value == drawableIds[index]) {
                                    Modifier.border(
                                        2.dp,
                                        Color.Blue,
                                        CircleShape
                                    ) // Cambia el grosor, color y forma a tu gusto
                                } else {
                                    Modifier
                                }
                            )
                    )
                }
            }
        }
    }
}