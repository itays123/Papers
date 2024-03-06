package analytikt.operators

import analytikt.base.*

/**
 * Another common operator type is what we will call Scale operator.
 * These operators accept a term of a domain and a structure of the same domain and return a term of the structure domain.
 * Examples of scaling operators are scaling vectors, matrices, functions and polynomials.
 */
abstract class ScaleOperator<TScalar, TEntity> : Operator<Any, TEntity>() {
    override val name: String
        get() = "*"

    abstract val scalarDomain: DomainDescriptor<TScalar>


    private fun getArgPair(args: Collection<Term<in Any>>): Pair<Term<TScalar>, Term<TEntity>> {
        assertArgCount(args, 2)
        // since args.size is exactly 2, we can use first and last.
        val scalar = scalarDomain.parse(args.first())
        val scalingEntity = resultDomain.parse(args.last())
        return Pair(scalar, scalingEntity)
    }

    override fun apply(args: Collection<Term<in Any>>): Term<TEntity> {
        val argPair = getArgPair(args)
        return applyScale(argPair.first, argPair.second)
    }

    open fun applyScale(scalar: Term<TScalar>, scalingEntity: Term<TEntity>): Term<TEntity> {
        return AppliedOperatorTerm(this, listOf(scalar, scalingEntity), resultDomain)
    }

    override fun toString(args: Collection<Term<in Any>>): String {
        val argPair = getArgPair(args)
        return argPair.first.toString() + "*" + argPair.second.toString()
    }
}