package analytikt.base

/**
 * Represents a term that cannot be split further
 */
abstract class AtomicTerm<TDomain>(domain: DomainDescriptor<TDomain>) : Term<TDomain>(domain) {

    override fun <E> put(sub: Term<E>, source: Term<E>): Term<TDomain> {
        if (this == source) {
            return try {
                domain.parse(sub)
            } catch (e: DomainDescriptor.DomainCastException) {
                this
            }
        }
        return this


    }

}