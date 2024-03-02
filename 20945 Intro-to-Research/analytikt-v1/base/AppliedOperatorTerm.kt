package analytikt.base

/**
 * A pair of an operator and its arguments of the matching domain is called an Applied Operator Term.
 */
class AppliedOperatorTerm<TArgsDomain: Any, TResultDomain: Any>(val operator: Operator<TArgsDomain, TResultDomain>, val args: Collection<Term<TArgsDomain>>): Term<TResultDomain>(operator.resultDomain) {

    override fun put(sub: Term<TResultDomain>, source: Term<TResultDomain>): Term<TResultDomain> {
        if (this == source) {
            return sub
        }
        if (operator.isClosed())
            return operator.apply(args.map { it.put(sub as Term<TArgsDomain>, source as Term<TArgsDomain>) })
        return this
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is AppliedOperatorTerm<*,*> && other.operator == operator && other.args == args
    }

    override fun toString(): String {
        return operator.toString(args)
    }

    override fun hashCode(): Int {
        var result = operator.hashCode()
        result = 31 * result + args.hashCode()
        return result
    }
}