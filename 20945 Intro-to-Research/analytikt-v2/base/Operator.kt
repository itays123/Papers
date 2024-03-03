package analytikt.base

/**
 * A mathematical operator is a type of structure that takes argument(s) of type TAccept and returns a term of domain TResDomain. Examples of operators are:
 * The negation operator on the boolean domain, which takes a boolean value and returns the opposite one
 * The determinant function, which takes a matrix of domain D and returns a number within this domain.
 * The sine function, which takes a real number and returns a real number.
 *
 * The TAccept type can be a Term of some domain, a Pair of terms, or a collection of terms.
 * We maintain flexibility by using a generic type to suit all cases
 */
abstract class Operator<TAccept, TResDomain>(val resultDomain: DomainDescriptor<TResDomain>): Entity<TResDomain> {
    abstract val name: String

    /**
     * Apply the operator
     */
    open fun apply(args: TAccept): Term<TResDomain> {
        return AppliedOperatorTerm(this, args, resultDomain)
    }

    /**
     * Apply operator with term sub instead of term source if found in args
     */
    fun<E> putInArgs(args: TAccept, sub: Term<E>, source: Term<E>): Term<TResDomain> {
        when (args) {
            is Term<*> -> return apply(args.put(sub, source) as TAccept)
            is Pair<*, *> -> return if (args.first is Term<*> && args.second is Term<*>) {
                apply(
                    Pair(
                        (args.first as Term<*>).put(sub, source),
                        (args.second as Term<*>).put(sub, source)
                    ) as TAccept)
            } else throw UnsupportedOperationException("Operator requires pair of terms") // shouldn't happen
            is Collection<*> -> return if (args.all { it is Term<*> }) {
                apply(args.map { (it as Term<*>).put(sub, source) } as TAccept)
            } else throw UnsupportedOperationException("Operator requires collection of terms") // shouldn't happen
        }
        throw UnsupportedOperationException("Operator requires term, pair of terms or collection of terms")
    }

    /**
     * Manipulate arguments such that param source will be an argument for the function
     * @param args the original arguments
     * @param targetArgs the arguments to insert
     * @sample
     * Power.composeFrom(x^4, x^2) -> (x^2)^2
     * Add.composeFrom(x+2, x+1) -> (x+1)+1
     * LogicalAnd.composeFrom(p and q and r, r) -> (p and q) and r
     */
    abstract fun composeFrom(args: TAccept, targetArgs: TAccept): TAccept?

    /**
     * Check equality (in computer science terms) between two operators
     */
    override fun equals(other: Any?): Boolean {
        return other != null && other is Operator<*, *> && other.name == name && resultDomain == other.resultDomain
    }

    override fun hashCode(): Int {
        var result = resultDomain.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    // String methods

    /**
     * Generates a string representing the operator.
     * Default implementation is prefix form.
     */
    open fun toString(args: TAccept): String {
        var result = name
        when (args) {
            is Term<*> -> result += " " + args
            is Pair<*, *> -> result += "(" + args.first + "," + args.second + ")"
            is Collection<*> -> result += args.joinToString(",", "(", ")")
        }
        return result
    }

}