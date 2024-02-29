package analytikt.base

import kotlin.reflect.KClass

/**
 * Represents a term in the domain TDomain which does not have composed terms
 */
abstract class AtomicTerm<TDomain : Any>(domain: KClass<out TDomain>) : Term<TDomain>(domain) {

    override fun put(sub: Term<TDomain>, source: Term<TDomain>): Term<TDomain> {
        if (this == source)
            return sub
        return this
    }

}