package analytikt.operators

import analytikt.base.Constant
import analytikt.base.Operator
import analytikt.base.Term

/**
 * Represents a domain that has operands of two different terms, like:
 * - Scaling vectors by scalar
 * - Raising matrices to the power of integers
 */
abstract class DoubleDomainOperator<TArg1, TArg2, TResult> : Operator<Pair<Term<TArg1>, Term<TArg2>>, TResult>() {

    override fun apply(args: Collection<Term<Pair<Term<TArg1>, Term<TArg2>>>>): Term<TResult> {
        // We only allow "constants" (i.e - pair of terms) to be arguments
        if (args.any { it !is Constant })
            throw UnsupportedOperationException("Double Domain operator must accept pairs!")
        return applyPairs(args.map { (it as Constant).value })
    }

    /**
     * Apply method with given pairs of terms
     */
    fun apply(vararg pairs: Pair<Term<TArg1>, Term<TArg2>>): Term<TResult> {
        return applyPairs(pairs.asList())
    }

    abstract fun applyPairs(args: Collection<Pair<Term<TArg1>, Term<TArg2>>>): Term<TResult>

}