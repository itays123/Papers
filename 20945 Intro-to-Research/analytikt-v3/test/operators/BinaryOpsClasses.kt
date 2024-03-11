package operators

import analytikt.base.Constant
import analytikt.base.DomainDescriptor
import analytikt.base.Term
import analytikt.base.Variable
import analytikt.operators.BinaryOperator
import analytikt.operators.Commutative

class IntDomain : DomainDescriptor<Int>

// Non-associative, Non-commutative operator
/**
 * a * b = a-b
 */
class BasicSubtraction : BinaryOperator<Int>() {

    override val resultDomain: DomainDescriptor<Int>
        get() = IntDomain()
    override val name: String
        get() = "-"

    override fun getKey(term: Term<Int>): Term<Int> {
        if (term is Constant)
            return Constant(1, term.domain)
        return term
    }

    override fun directApply(terms: Collection<Term<Int>>): Term<Int> {
        if (terms.size != 2) throw RuntimeException()
        val left = terms.first()
        val right = terms.last()
        if (left is Constant && right is Constant)
            return Constant(left.value - right.value, left.domain)
        if (left is Variable && right is Variable) // should be the same one according to key definition
            return Constant(0, left.domain)
        throw RuntimeException()
    }

}

// TODO: Non-associative, commutative operator

/**
 * a*b = |a-b|
 */
class Distance : BinaryOperator<Int>(), Commutative {

    override val resultDomain: DomainDescriptor<Int>
        get() = IntDomain()
    override val name: String
        get() = "*"


    override fun getKey(term: Term<Int>): Term<Int> {
        if (term is Constant)
            return Constant(1, term.domain)
        return term
    }

    override fun directApply(terms: Collection<Term<Int>>): Term<Int> {
        if (terms.size != 2) throw RuntimeException("Terms size is not 2")
        val left = terms.first()
        val right = terms.last()
        if (left is Constant && right is Constant)
            return Constant(kotlin.math.abs(left.value - right.value), left.domain)
        if (left is Variable && right is Variable) // should be the same one according to key definition
            return Constant(0, left.domain)
        throw RuntimeException("Cannot handle")
    }

}

// TODO: Non-associative, neural element operator


// TODO: Associative, non-commutative operator


// TODO: Associative, commutative operator w/o neural element


// TODO: Associative, commutative operator with neutral element


// TODO: Group operator