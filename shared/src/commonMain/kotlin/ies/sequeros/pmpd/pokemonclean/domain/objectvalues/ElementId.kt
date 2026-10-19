package ies.sequeros.pmpd.pokemonclean.domain.objectvalues

import kotlin.uuid.Uuid

 class ElementId private constructor (val value: Uuid) {
     val id: Uuid
         get() = value
    companion object {
        fun from(id: Uuid): ElementId {
            return ElementId(id)
        }
    }
}