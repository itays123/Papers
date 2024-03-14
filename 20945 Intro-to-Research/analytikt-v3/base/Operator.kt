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
abstract class Operator<TArgDomain, TResDomain> {
    abstract val resultDomain: DomainDescriptor<TResDomain>
    abstract val name: String

    /**
     * Overridable lazy evaluation map
     * In a pair (key, value), if key is present in the argument list, immediately evaluate to value
     */
    protected open val lazyEvalMap: Map<Term<TArgDomain>, Term<TResDomain>> = mapOf()

    protected fun tryLazyEvaluate(args: Collection<Term<TArgDomain>>): Term<TResDomain>? {
        if (lazyEvalMap.isEmpty())
            return null
        for (term in args) {
            val lazyEval = lazyEvalMap[term]
            if (lazyEval != null)
                return lazyEval
        }
        return null
    }

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
        return tryLazyEvaluate(args) ?: AppliedOperatorTerm(this, args, resultDomain)
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
    open fun put(
        args: Collection<Term<TArgDomain>>,
        sub: Term<*>, sourceArgs:
        Collection<Term<TArgDomain>>): Term<TResDomain>? {
        if (args == sourceArgs)
            return resultDomain.parse(sub)
        return null
    }

    /**
     * Check equality (in computer science terms) between two operators
     */
    override fun equals(other: Any?): Boolean {
        return other != null
                && other is Operator<*, *>
                && other.name == name
                && resultDomain.contains(other.resultDomain)
                && other.resultDomain.contains(resultDomain)
    }

    override fun hashCode(): Int {
        var result = resultDomain.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }


    override fun toString(): String {
        return name
    }

    /**
     * Generates a string representing the operator.
     * Default implementation is prefix form.
     */
    open fun toString(args: Collection<Term<TArgDomain>>): String {
        var res = name
        val parenthesize = args.size > 1
        if (parenthesize)
            res += "("
        else
            res += " "
        args.forEachIndexed { index, term ->
            if (index > 0)
                res += ","
            res += stringifyTerm(term)
        }
        if (parenthesize)
            res += ")"
        return res
    }

    /**
     * Determine whether a term should be parenthesized.
     * For example, term x^2 in sum x^2+3 should not be parenthesized.
     */
    protected open fun shouldParenthesizeTerm(term: Term<TArgDomain>): Boolean {
        return term is AppliedOperatorTerm<*, *>
    }

    protected fun stringifyTerm(term: Term<TArgDomain>) = if (shouldParenthesizeTerm(term)) "($term)" else term.toString()

}