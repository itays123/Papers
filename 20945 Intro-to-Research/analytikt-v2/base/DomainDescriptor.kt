package analytikt.base

/**
 * Every term requires a reference to its domain’s Descriptor class.
 * A domain descriptor is the equivalent of a predicate logic structure, model or interpretation
 * which provides constants and operators of the domain.
 * In computer science terms, a domain descriptor is a factory used for getting data about the domain we operate with.
 */
interface DomainDescriptor<TDomain> : Entity<TDomain> {

    /**
     * Safely casts a term of any domain to a term of this domain
     */
    fun parse(term: Term<*>): Term<TDomain> {
        if (term.domain == this) {
            @Suppress("UNCHECKED_CAST")
            return term as Term<TDomain>
        }
        throw DomainCastException("Can't change domains of term")
    }

    fun parse(value: TDomain): Constant<TDomain> {
        return Constant(value, this)
    }

    /**
     * Throw when attempting to change a term's domain
     */
    class DomainCastException(s: String) : UnsupportedOperationException(s)

}
