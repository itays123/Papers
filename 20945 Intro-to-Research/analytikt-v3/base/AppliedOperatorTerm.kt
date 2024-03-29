package analytikt.base

/**
 * A pair of an operator and its arguments of the matching domain is called an Applied Operator Term.
 */
class AppliedOperatorTerm<TArgDomain, TResDomain>(val operator: Operator<TArgDomain, TResDomain>, val args: Collection<Term<TArgDomain>>, domain: DomainDescriptor<TResDomain>) : Term<TResDomain>(domain) {
    override fun <E> put(sub: Term<E>, source: Term<E>): Term<TResDomain> {
        // 2 cases to handle:
        // Put in args, like x^2[2t+1/x] -> (2t+1)^2
        // Put in self, like x^2[t/x^2] -> t
        // When putting in self, note collection laws. E.G: x^4[t/x^2] -> t^2, (x+2)[t/x+1] -> t+1
        val mappedArgs = args.map { it.put(sub, source) }
        if (source is AppliedOperatorTerm<*, *> && source.operator == operator) {
            val res = operator.put(mappedArgs, sub, source.args as Collection<Term<TArgDomain>>)
            if (res != null) return res
        }
        return operator.apply(mappedArgs)
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is AppliedOperatorTerm<*, *> && other.operator == operator && other.args == args
    }

    override fun hashCode(): Int {
        var result = operator.hashCode()
        result = 31 * result + args.hashCode()
        return result
    }

    override fun toString(): String {
        return operator.toString(args)
    }
}