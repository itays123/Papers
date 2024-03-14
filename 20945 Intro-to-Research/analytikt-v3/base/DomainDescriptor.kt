package analytikt.base

/**
 * Every term requires a reference to its domain’s Descriptor class.
 * A domain descriptor is the equivalent of a predicate logic structure, model or interpretation
 * which provides constants and operators of the domain.
 * In computer science terms, a domain descriptor is a factory used for getting data about the domain we operate with.
 */
interface DomainDescriptor<TDomain> {

    /**
     * An instance of the domain set to use in various cases
     */
    val constantInstance: Constant<TDomain>

    /**
     * Safely casts a term of any domain to a term of this domain
     */
    fun parse(term: Term<*>): Term<TDomain> {
        if (contains(term.domain) && term.domain.contains(this)) {
            @Suppress("UNCHECKED_CAST")
            return term as Term<TDomain>
        }
        throw DomainCastException("Can't change domains of term")
    }

    fun parse(value: TDomain): Constant<TDomain> {
        return Constant(value, this)
    }

    /**
     * Check if given domain is contained in the domain.
     */
    fun contains(other: DomainDescriptor<*>): Boolean {
        return this.javaClass == other.javaClass
    }

    /**
     * Throw when attempting to change a term's domain
     */
    class DomainCastException(s: String) : UnsupportedOperationException(s)

}
