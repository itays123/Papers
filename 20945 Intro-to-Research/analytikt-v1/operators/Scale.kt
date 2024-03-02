package analytikt.operators

import analytikt.base.Operator
import analytikt.base.Structure
import analytikt.base.Term
import kotlin.reflect.KClass

/**
 * Another common operator type is what we will call Scaling operator.
 * These operators accept a term of a domain and a structure of the same domain and return a term of the structure domain.
 * Examples of scaling operators are scaling vectors, matrices, functions and polynomials.
 */
abstract class Scale<TScalarDomain: Any, TStructure: Structure<TScalarDomain>>(val scalarDomain: KClass<out TScalarDomain>, structureDomain: KClass<out TStructure>) : Operator<Any, TStructure>(
    Any::class,
    structureDomain
) {

    companion object {
        const val ARG_COUNT = 2;
    }

    override val name: String
        get() = "*"

    protected fun extractArgs(args: Collection<Term<Any>>): Pair<Term<TScalarDomain>, Term<TStructure>> {
        // Check validity of arguments
        if (args.size != ARG_COUNT)
            throw IllegalArgumentException("Expected scalar and structure product")
        val argArr = args.toTypedArray()
        val scalar = argArr[0]
        val structure = argArr[1]

        if (!scalar.domain.java.isAssignableFrom(scalarDomain.java))
            throw IllegalArgumentException("First argument in scale operator should be a scalar")
        if (structure.domain.java.isAssignableFrom(resultDomain.java))
            throw IllegalArgumentException("Second argument in scale operator should be a structure")

        return Pair(scalar as Term<TScalarDomain>, structure as Term<TStructure>)
    }

    override fun tryApply(args: Collection<Term<Any>>): Term<TStructure>? {
        val argPair = extractArgs(args)
        return tryApply(argPair.first, argPair.second)
    }

    abstract fun tryApply(scalar: Term<TScalarDomain>, structure: Term<TStructure>): Term<TStructure>?

    override fun toString(args: Collection<Term<Any>>): String {
        val argPair = extractArgs(args)
        return argPair.first.toString() + "*" + argPair.second.toString()
    }
}