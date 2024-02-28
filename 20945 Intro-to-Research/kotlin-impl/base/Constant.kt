package analytikt.base

class Constant<TDomain>(val value: TDomain) : AtomicTerm<TDomain>() {

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Constant<*>)
            return false

        return value == other.value
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }
}