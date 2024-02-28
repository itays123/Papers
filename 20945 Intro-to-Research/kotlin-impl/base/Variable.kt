package analytikt.base

class Variable<TDomain>(val name: String) : AtomicTerm<TDomain>() {
    override fun equals(other: Any?): Boolean {
        if (other !is Variable<*>)
            return false

        return name == other.name
    }
}