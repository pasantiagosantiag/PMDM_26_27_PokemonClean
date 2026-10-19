package ies.sequeros.pmpd.pokemonclean.domain.objectvalues

class Descripcion private constructor(
    val texto: String,
) {
    val value: String
        get() = texto
    companion object {
        fun from(texto: String): Descripcion? {
            // Regla de negocio: Ninguno de los campos puede estar vacío
            if (texto.isBlank() ) {
                return null
            }
            return Descripcion(texto)
        }
    }
    override fun toString(): String = value
}
