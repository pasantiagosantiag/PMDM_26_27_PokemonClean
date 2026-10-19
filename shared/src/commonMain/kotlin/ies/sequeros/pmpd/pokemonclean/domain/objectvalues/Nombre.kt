package ies.sequeros.pmpd.pokemonclean.domain.objectvalues

//constructor privado, solo se puede crear desde el método
//estático from
class Nombre private constructor(
    val nombre: String,

) {
    //no se puede hacer set, es invariante
    val value: String
        get() = nombre
    companion object {
        fun from(nombre: String): Nombre? {

            // Regla de negocio: Ninguno de los campos puede estar vacío
            if (nombre.isBlank() ) {
                return null
            }

            return Nombre(nombre)
        }
    }
    override fun toString(): String = value
}
