package  ies.sequeros.pmpd.pokemonclean.domain

import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Avatar
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Descripcion
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.ElementId
import ies.sequeros.pmpd.pokemonclean.domain.objectvalues.Nombre
import org.jetbrains.compose.resources.DrawableResource
import kotlin.uuid.Uuid


//constructro privado
class Pokemon private constructor(
    private val id: ElementId,
    private var nombre: Nombre,
    private var descripcion: Descripcion,
    private var avatar: Avatar,
    private var enabled: Boolean = true
) {
    companion object {
        /**
         * Método estático factoría para crear un usuario de forma segura.
         * Solo permite la creación si todos los Value Objects internos son válidos.
         */
        fun create(
            id: Uuid,
            nombre: String,
            descripcion: String,
            avatar: Avatar,
            enable: Boolean = true
        ): Pokemon? {
            // Validamos todos los componentes en la entrada del dominio
            var nombre = Nombre.from(nombre) ?: return null
            var descripcion = Descripcion.from(descripcion) ?: return null
            var elementId = ElementId.from(id) ?: return null
            return Pokemon(
                id = elementId,
                nombre = nombre,
                descripcion = descripcion,
                avatar = avatar,
                enabled = enable

            )
        }
    }
    fun copy(
        id: ElementId = this.id,
        nombre: Nombre = this.nombre,
        descripcion: Descripcion = this.descripcion,
        avatar: Avatar = this.avatar,
        enabled: Boolean = this.enabled,
    ): Pokemon {
        return Pokemon(id, nombre, descripcion, avatar, enabled)
    }
    fun getId(): ElementId {
        return ElementId.from(id.value)
    }
    fun isEnabled(): Boolean {
        return this.enabled
    }
    fun changeNombre(nombre: String) {
        this.nombre = Nombre.from(nombre)!!
    }
    fun getNombre(): Nombre? {
        return Nombre.from(nombre = nombre.value)
    }
fun getDescripcion(): Descripcion? {
    return descripcion
}
    fun getAvatar(): Avatar? {
        return this.avatar
    }
    fun changeDescripcion(descripcion: String) {
        this.descripcion = Descripcion.from(descripcion)!!
    }
    fun changeAvatar(avatar: Avatar) {
         this.avatar = avatar
    }

    fun changeToDisable() {
         this.enabled = false
    }

    fun changeToEnable() {
        this.enabled = true
    }
}
