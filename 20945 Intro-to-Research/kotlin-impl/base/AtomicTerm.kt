package analytikt.base

/**
 * Represents a term in the domain TDomain which does not have composed terms
 */
abstract class AtomicTerm<TDomain> : Term<TDomain>() {

    override fun put(sub: Term<TDomain>, source: Term<TDomain>): Term<TDomain> {
        if (this == source)
            return sub
        return this
    }

}