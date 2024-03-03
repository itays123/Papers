package analytikt.base

/**
 * A pair of an operator and its arguments of the matching domain is called an Applied Operator Term.
 */
class AppliedOperatorTerm<TAccept, TArgsDomain>(val operator: Operator<TAccept, TArgsDomain>, val args: TAccept, domain: DomainDescriptor<TArgsDomain>) : Term<TArgsDomain>(domain) {
    override fun <E> put(sub: Term<E>, source: Term<E>): Term<TArgsDomain> {
        // 2 cases to handle:
        // Put in args, like x^2[2t+1/x] -> (2t+1)^2
        // Put in self, like x^2[t/x^2] -> t
        // When putting in self, note collection laws. E.G: x^4[t/x^2] -> t^2, (x+2)[t/x+1] -> x+1
        if (this == source)
            return domain.parse(sub)
        if (source is AppliedOperatorTerm<*, *> && source.operator == operator) {
            val newArgs = operator.composeFrom(args, source.args as TAccept)
            if (newArgs != null)
                return operator.putInArgs(newArgs, sub, source as Term<E>)
        }
        return operator.putInArgs(args, sub, source)
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is AppliedOperatorTerm<*, *> && other.operator == operator && other.args == args
    }

    override fun hashCode(): Int {
        var result = operator.hashCode()
        result = 31 * result + (args?.hashCode() ?: 0)
        return result
    }
}