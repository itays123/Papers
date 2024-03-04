package analytikt.base

import kotlin.jvm.Throws

/**
 * A mathematical operator is a type of structure that takes argument(s) of type TAccept and returns a term of domain TResDomain. Examples of operators are:
 * The negation operator on the boolean domain, which takes a boolean value and returns the opposite one
 * The determinant function, which takes a matrix of domain D and returns a number within this domain.
 * The sine function, which takes a real number and returns a real number.
 *
 * The TAccept type can be a Term of some domain, a Pair of terms, or a collection of terms.
 * We maintain flexibility by using a generic type to suit all cases
 */
abstract class Operator<TArgDomain, TResDomain>: Entity<TResDomain> {
    abstract val resultDomain: DomainDescriptor<TResDomain>
    abstract val name: String

    /**
     * Interface method
     */
    fun apply(vararg args: Term<TArgDomain>): Term<TResDomain> {
        return apply(args.asList())
    }

    /**
     * Accept values if needed and apply the operator
     */
    open fun apply(args: Collection<Term<TArgDomain>>): Term<TResDomain> {
        return AppliedOperatorTerm(this, args, resultDomain)
    }

    /**
     * Manipulate arguments such that param source will be an argument for the function
     * @param args the original arguments of the operator
     * @param sub the substitution
     * @param sourceArgs the arguments to replace
     * @sample
     * Power.put((x,4), t, (x,2)) -> t^2
     * Add.put(x+2, t, x+1) -> t+1
     * LogicalAnd.put(p and q and r, s, p and q) -> s and r
     */
    open fun put(args: Collection<Term<TArgDomain>>, sub: Term<*>, sourceArgs: Collection<Term<TArgDomain>>): Term<TResDomain> {
        if (args == sourceArgs)
            return resultDomain.parse(sub)
        return apply(args)
    }

    /**
     * Assert argument count
     * @param args the arguments to check
     * @param count the required argument count
     * @throws IllegalArgumentException
     */
    @Throws(IllegalArgumentException::class)
    protected fun assertArgCount(args: Collection<Term<TArgDomain>>, count: Int) {
        if (args.size != count) throw IllegalArgumentException("Function $name accepts exactly $count operand(s)")
    }

    /**
     * Check equality (in computer science terms) between two operators
     */
    override fun equals(other: Any?): Boolean {
        return other != null && other is Operator<*, *> && other.name == name && compareDomains(resultDomain, other.resultDomain)
    }

    override fun hashCode(): Int {
        var result = resultDomain.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    /**
     * Generates a string representing the operator.
     * Default implementation is prefix form.
     */
    open fun toString(args: Collection<Term<TArgDomain>>): String {
        var res = name
        var delim = ""
        val parenthesize = args.size > 1 || args.any { it !is AtomicTerm<*> }
        if (parenthesize)
            res += "("
        else
            res += " "
        args.forEach {
            res += delim + it.toString()
            delim = ","
        }
        if (parenthesize)
            res += ")"
        return res
    }

}