package ies.sequeros.pmpd.pokemonclean

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform