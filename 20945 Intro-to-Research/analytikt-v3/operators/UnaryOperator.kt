package analytikt.operators

import analytikt.base.Constant
import analytikt.base.Operator
import analytikt.base.Term

/**
 * Represents a unary operator, like:
 * - Real functions sine, cosine, natural log, exp, etc...
 * - The determinant function
 */
abstract class UnaryOperator<TArgDomain, TResDomain> : Operator<TArgDomain, TResDomain>() {

    override fun apply(args: Collection<Term<TArgDomain>>): Term<TResDomain> {
        // protection - only allow for 1 argument
        if (args.size != 1)
            throw IllegalArgumentException("Unary operator accepts 1 argument")
        if (args.first() !is Constant)
            return super.apply(args)
        return evaluate((args.first() as Constant).value)
    }

    /**
     * Directly evaluate the result, given that the argument's value is known and given
     */
    abstract fun evaluate(arg: TArgDomain): Term<TResDomain>


}