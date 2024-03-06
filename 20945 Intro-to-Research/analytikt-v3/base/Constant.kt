package analytikt.base

class Constant<TDomain>(val value: TDomain, domain: DomainDescriptor<TDomain>): Term<TDomain>(domain) {

    /**
     * We don't allow substituting in constants
     */
    override fun <E> put(sub: Term<E>, source: Term<E>): Term<TDomain> {
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false

        return (other is Constant<*> && value == other.value) || value == other // this makes constant(value) == value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.toString()
    }
}