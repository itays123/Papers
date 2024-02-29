package analytikt.base

import kotlin.reflect.KClass

/**
 * A Term of a domain is a mathematical structure that is in the domain, such as:
 * - A rational number in the rational numbers domain
 * - A Matrix in the matrix domain
 */
abstract class Term<TDomain: Any>(val domain: KClass<out TDomain>) : Structure<TDomain> {


    /**
     * Substitute a term by another one to produce a valid term.
     * @param sub the term to replace with
     * @param source the term to replace its appearances with
     */
    abstract fun put(sub: Term<TDomain>, source: Term<TDomain>): Term<TDomain>

}