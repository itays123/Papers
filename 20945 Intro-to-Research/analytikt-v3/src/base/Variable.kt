package analytikt.base

/**
 * Represents a term in TDomain whose value is unknown
 */
class Variable<TDomain>(val name: String, domain: DomainDescriptor<TDomain>) : AtomicTerm<TDomain>(domain) {
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