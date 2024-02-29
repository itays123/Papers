package analytikt.base

/**
 * Represents a term in a domain which has a known value. E.G - pi, e, 0, 1, i, ...
 */
class Constant<TDomain : Any>(val value: TDomain) : AtomicTerm<TDomain>(value::class) {

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Constant<*>)
            return false

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.toString()
    }
}