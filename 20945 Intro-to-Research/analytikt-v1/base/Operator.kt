package analytikt.base

import kotlin.reflect.KClass

/**
 * A mathematical operator is a type of structure that takes 1 or more mathematical terms of domain TArgDomain and returns a term of domain TResDomain. Examples of operators are:
 * The negation operator on the boolean domain, which takes a boolean value and returns the opposite one
 * The determinant function, which takes a matrix of domain D and returns a number within this domain.
 * The sine function, which takes a real number and returns a real number.
 *
 */
abstract class Operator<TArgDomain: Any, TResultDomain: Any>(val argDomain: KClass<out TArgDomain>, val resultDomain: KClass<out TResultDomain>) : Structure<TResultDomain> {

    /**
     * Name of the operator, like det, +, *, exp, ln, sin, etc.
     */
    abstract val name: String

    /**
     * Check if operator is closed - if its args are of the same domain as its result
     */
    fun isClosed(): Boolean {
        return argDomain == resultDomain
    }

    /**
     * Apply the operator
     */
    fun apply(vararg args: Term<TArgDomain>): Term<TResultDomain> {
        return apply(args.asList())
    }

    /**
     * Apply the operator
     */
    fun apply(args: Collection<Term<TArgDomain>>): Term<TResultDomain> {
        return tryApply(args) ?: AppliedOperatorTerm(this, args)
    }

    protected abstract fun tryApply(args: Collection<Term<TArgDomain>>): Term<TResultDomain>?

    /**
     * Create a string representation of the operator
     */
    open fun toString(args: Collection<Term<TArgDomain>>): String {
        var res: String = name
        var delim = ""
        val parenthesize = args.size > 1 || args.any { it !is AtomicTerm<*> }
        if (parenthesize)
            res += "("
        args.forEach {
            res += delim + it.toString()
            delim = ","
        }
        if (parenthesize)
            res += "("
        return res
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is Operator<*,*> && other.argDomain == argDomain && other.resultDomain == resultDomain && other.name == name
    }

    override fun hashCode(): Int {
        var result = argDomain.hashCode()
        result = 31 * result + resultDomain.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

}