package analytikt.base

/**
 * A Term of a domain is a mathematical entity that is in the domain,
 * such as a rational number in the rational numbers' domain.
 */
abstract class Term<TDomain>(val domain: DomainDescriptor<TDomain>): Entity<TDomain> {

    /**
     * Substitute a term by another one to produce a valid term.
     * @param sub the term to replace with
     * @param source the term to replace its appearances with
     */
    abstract fun<E> put(sub: Term<E>, source: Term<E>): Term<TDomain>

}