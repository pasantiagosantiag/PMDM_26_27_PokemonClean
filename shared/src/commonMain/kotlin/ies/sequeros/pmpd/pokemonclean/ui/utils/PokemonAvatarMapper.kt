package ies.sequeros.pmpd.pokemonclean.ui.utils

import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Avatar
import org.jetbrains.compose.resources.DrawableResource
import pokemonclean.shared.generated.resources.Res
import pokemonclean.shared.generated.resources._103
import pokemonclean.shared.generated.resources._12
import pokemonclean.shared.generated.resources._168
import pokemonclean.shared.generated.resources._169
import pokemonclean.shared.generated.resources.bola

object  PokemonAvatarMapper {
    fun toDrawable(avatar: Avatar): DrawableResource{
        return when(avatar){
            Avatar.PIKACHU -> Res.drawable._12
            Avatar.CHARIZARD -> Res.drawable._103
            Avatar.BULBASAUR -> Res.drawable._169
            Avatar.SQUIRTLE -> Res.drawable._168
            Avatar.BOLA_DEFECTO -> Res.drawable.bola
        }
    }
    fun getDrawableResources(): List<DrawableResource>{
        return listOf(
            Res.drawable._12,
            Res.drawable._103,
            Res.drawable._168,
            Res.drawable._169,
            Res.drawable.bola
        )
    }
    fun toAvatar(image: DrawableResource): Avatar{
        return when(image){
            Res.drawable.bola -> Avatar.BOLA_DEFECTO
            Res.drawable._12-> Avatar.PIKACHU
            Res.drawable._103 -> Avatar.CHARIZARD
            Res.drawable._169 -> Avatar.BULBASAUR

            else -> {
                Avatar.BOLA_DEFECTO
            }
        }
    }
}