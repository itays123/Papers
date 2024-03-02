package analytikt.base

import kotlin.reflect.KClass

class Variable<TDomain : Any>(val name: String, domain: KClass<out TDomain>) : AtomicTerm<TDomain>(domain) {
    override fun equals(other: Any?): Boolean {
        if (other !is Variable<*>)
            return false

        return name == other.name && domain == other.domain
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return name
    }
}