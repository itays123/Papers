package analytikt.operators

import analytikt.base.Operator
import analytikt.base.Term
import kotlin.reflect.KClass

/**
 * One type of operator is called a Relation. This is an operator whose result domain is the boolean domain. While traditionally in mathematics relations are not instances of Operators but a separate structure of their own, the abstractions we provide give enough flexibility for the inheritance to be well-defined. Examples of relations are:
 * A Term of a domain being in a set of the domain
 * A number being greater than another number
 * A Point being in a shape
 * 3 points being on the same line
 * A Relation has a fixed number of arguments
 */
abstract class Relation<TArg: Any>(argDomain: KClass<out TArg>) : Operator<TArg, Boolean>(argDomain, Boolean::class) {

    protected abstract val numOfArgs: Int

    override fun tryApply(args: Collection<Term<TArg>>): Term<Boolean>? {
        if (args.size != numOfArgs)
            throw IllegalArgumentException("Argument count mismatch")
        return tryApply(args.toTypedArray())
    }

    /**
     * Apply the relation knowing it has exactly the matching number of operands
     */
    protected abstract fun tryApply(args: Array<Term<TArg>>): Term<Boolean>?

}