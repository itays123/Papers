package operators

import analytikt.base.*
import analytikt.operators.*

class IntDomain : DomainDescriptor<Int> {
    override val constantInstance: Constant<Int>
        get() = parse(1)
}
class Z3Domain : DomainDescriptor<Int> {

    companion object {
        val Zero = Constant(0, Z3Domain())
        val One = Constant(1, Z3Domain())
        val Two = Constant(2, Z3Domain())
    }

    override val constantInstance: Constant<Int>
        get() = parse(1)

    override fun parse(value: Int): Constant<Int> {
        return super.parse(value % 3)
    }

}

/**
 * Non-associative, Non-commutative operator
 * a * b = a-b
 */
class BasicSubtraction : BinaryOperator<Int>() {

    override val resultDomain: DomainDescriptor<Int>
        get() = IntDomain()
    override val name: String
        get() = "-"

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

/**
 * Non-associative, commutative operator
 * a*b = |a-b|
 */
class Distance : BinaryOperator<Int>(), Commutative {

    override val resultDomain: DomainDescriptor<Int>
        get() = IntDomain()
    override val name: String
        get() = "*"

    override fun directApply(terms: Collection<Term<Int>>): Term<Int> {
        val left = terms.first()
        val right = terms.last()
        if (left is Constant && right is Constant)
            return Constant(kotlin.math.abs(left.value - right.value), left.domain)
        if (left is Variable && right is Variable) // should be the same one according to key definition
            return Constant(0, left.domain)
        throw RuntimeException("Cannot handle")
    }

}

/** Non-associative, non-commutative neural element operator
 * For this test, a finite method will be chosen
 *
 *  *   0   1   2
 *  0   0   1   2
 *  1   1   2   1
 *  2   2   0   0
 *
 * Not associative since:
 * (2*2)*1=0*1=1
 * 2*(2*1)=2*0=2
 *
 * Not commutative since:
 * 2*1=0
 * 1*2=1
 *
 * Has neutral element 0
 */
class NeutralElemOpImpl : BinaryOperator<Int>(), HasNeutralElement<Int> {

    override val resultDomain: DomainDescriptor<Int>
        get() = Z3Domain()
    override val name: String
        get() = "*"

    private val resultTable = mapOf(
        Pair(Pair(0,0), 0),
        Pair(Pair(0,1), 1),
        Pair(Pair(0,2), 2),
        Pair(Pair(1,0), 1),
        Pair(Pair(1,1), 2),
        Pair(Pair(1,2), 1),
        Pair(Pair(2,0), 2),
        Pair(Pair(2,1), 0),
        Pair(Pair(2,2), 0),
    )

    override val neutralElement: Int
        get() = 0

    override fun directApply(terms: Collection<Term<Int>>): Term<Int> {
        if (terms.first() !is Constant || terms.last() !is Constant)
            return AppliedOperatorTerm(this, terms, resultDomain)
        val left = (terms.first() as Constant).value
        val right = (terms.last() as Constant).value
        return resultDomain.parse(resultTable[Pair(left, right)] ?: throw UnsupportedOperationException("Binary op accepts values from 0 to 2"))
    }

}


/**
 * Associative, non-commutative operator w/o neutral element
 * For this test, a finite method will be chosen
 *
 *  *   0   1   2
 *  0   0   0   0
 *  1   0   1   2
 *  2   0   0   0
 *
 *
 * Not commutative since:
 * 2*1=0
 * 1*2=2
 *
 */
class AssociativeOpImpl : BinaryOperator<Int>(), Associative {

    override val resultDomain: DomainDescriptor<Int>
        get() = Z3Domain()
    override val name: String
        get() = "*"

    private val resultTable = mapOf(
        Pair(Pair(0,0), 0),
        Pair(Pair(0,1), 0),
        Pair(Pair(0,2), 0),
        Pair(Pair(1,0), 0),
        Pair(Pair(1,1), 1),
        Pair(Pair(1,2), 2),
        Pair(Pair(2,0), 0),
        Pair(Pair(2,1), 0),
        Pair(Pair(2,2), 0),
    )

    override val lazyEvalMap: Map<Term<Int>, Term<Int>>
        get() = mapOf(Pair(resultDomain.parse(0), resultDomain.parse(0)))

    override fun directApply(terms: Collection<Term<Int>>): Term<Int> {
        if (terms.first() !is Constant || terms.last() !is Constant)
            return AppliedOperatorTerm(this, terms, resultDomain)
        val left = (terms.first() as Constant).value
        val right = (terms.last() as Constant).value
        return resultDomain.parse(resultTable[Pair(left, right)] ?: throw UnsupportedOperationException("Binary op accepts values from 0 to 2"))
    }

}

/**
 * Associative, non-commutative operator with neutral element
 * *    0   1   2
 * 0    0   1   2
 * 1    1   1   1
 * 2    2   2   2
 */
class AssocNeutralElemOpImpl : BinaryOperator<Int>(), Associative, HasNeutralElement<Int> {

    override val resultDomain: DomainDescriptor<Int>
        get() = Z3Domain()
    override val name: String
        get() = "*"

    private val resultTable = mapOf(
        Pair(Pair(0,0), 0),
        Pair(Pair(0,1), 1),
        Pair(Pair(0,2), 2),
        Pair(Pair(1,0), 1),
        Pair(Pair(1,1), 1),
        Pair(Pair(1,2), 1),
        Pair(Pair(2,0), 2),
        Pair(Pair(2,1), 2),
        Pair(Pair(2,2), 2),
    )

    override val neutralElement: Int
        get() = 0

    override fun directApply(terms: Collection<Term<Int>>): Term<Int> {
        if (terms.first() !is Constant || terms.last() !is Constant)
            return AppliedOperatorTerm(this, terms, resultDomain)
        val left = (terms.first() as Constant).value
        val right = (terms.last() as Constant).value
        return resultDomain.parse(resultTable[Pair(left, right)] ?: throw UnsupportedOperationException("Binary op accepts values from 0 to 2"))
    }

}

/*
 * TODO: Associative, non-commutative, invertible operator
 *
 * *   0   1   2
 * 0
 * 1
 * 2
 */


 /**
 Associative, commutative operator w/o neural element
 *
  * *   0   1   2
  * 0   1   2   1
  * 1   2   1   2
  * 2   1   2   1
  */
 class Z3Operator4 : BinaryOperator<Int>(), Associative, Commutative {

     override val resultDomain: DomainDescriptor<Int>
         get() = Z3Domain()
     override val name: String
         get() = "*"

     private val resultTable = mapOf(
         Pair(Pair(0,0), 1),
         Pair(Pair(0,1), 2),
         Pair(Pair(0,2), 1),
         Pair(Pair(1,0), 2),
         Pair(Pair(1,1), 1),
         Pair(Pair(1,2), 2),
         Pair(Pair(2,0), 1),
         Pair(Pair(2,1), 2),
         Pair(Pair(2,2), 1),
     )

     override fun directApply(terms: Collection<Term<Int>>): Term<Int> {
         if (terms.first() !is Constant || terms.last() !is Constant)
             return AppliedOperatorTerm(this, terms, resultDomain)
         val left = (terms.first() as Constant).value
         val right = (terms.last() as Constant).value
         return resultDomain.parse(resultTable[Pair(left, right)] ?: throw UnsupportedOperationException("Binary op accepts values from 0 to 2"))
     }

 }

/**
 * Associative, commutative, neutral element but not invertible and without lazy evaluation
 *
 * *    0   1   2
 * 0    0   1   0
 * 1    1   0   1
 * 2    0   1   2
 *
 */
class Z3Operator5 : BinaryOperator<Int>(), Associative, Commutative, HasNeutralElement<Int> {

    override val resultDomain: DomainDescriptor<Int>
        get() = Z3Domain()
    override val name: String
        get() = "*"

    override val neutralElement: Int
        get() = 2

    private val resultTable = mapOf(
        Pair(Pair(0,0), 0),
        Pair(Pair(0,1), 1),
        Pair(Pair(0,2), 0),
        Pair(Pair(1,0), 1),
        Pair(Pair(1,1), 0),
        Pair(Pair(1,2), 1),
        Pair(Pair(2,0), 0),
        Pair(Pair(2,1), 1),
        Pair(Pair(2,2), 2),
    )

    override fun directApply(terms: Collection<Term<Int>>): Term<Int> {
        if (terms.first() !is Constant || terms.last() !is Constant)
            return AppliedOperatorTerm(this, terms, resultDomain)
        val left = (terms.first() as Constant).value
        val right = (terms.last() as Constant).value
        return resultDomain.parse(resultTable[Pair(left, right)] ?: throw UnsupportedOperationException("Binary op accepts values from 0 to 2"))
    }

}

/**
 * Associative, commutative operator with neutral element and lazy evaluation
 * Normal integer multiplication is chosen
 */
class SimpleMultiply : BinaryOperator<Int>(), Associative, Commutative, HasNeutralElement<Int> {

    override val resultDomain: DomainDescriptor<Int>
        get() = IntDomain()
    override val name: String
        get() = "*"
    override val neutralElement: Int
        get() = 1
    override val lazyEvalMap: Map<Term<Int>, Term<Int>>
        get() = mapOf(Pair(resultDomain.parse(0), resultDomain.parse(0)))

    override fun directApply(terms: Collection<Term<Int>>): Term<Int> {
        if (terms.all { it is Constant })
            return resultDomain.parse(terms.fold(1) { acc, term -> acc * (term as Constant).value })
        return AppliedOperatorTerm(this, terms, resultDomain)
    }

}

/**
 * Group operator +(mod 3) in z3 will be picked
 */
class Z3Addition : BinaryOperator<Int>(), Associative, Commutative, Invertible<Int> {

    override val resultDomain: DomainDescriptor<Int>
        get() = Z3Domain()
    override val name: String
        get() = "+"

    override val neutralElement: Int
        get() = 0

    override fun directApply(terms: Collection<Term<Int>>): Term<Int> {
        if (terms.all { it is Constant })
            return resultDomain.parse(terms.fold(0) { acc, term -> acc + (term as Constant).value })
        return AppliedOperatorTerm(this, terms, resultDomain)
    }

    override fun findInverse(term: Term<Int>): Term<Int> {
        return apply(term, resultDomain.parse(2))
    }

}
