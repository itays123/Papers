package analytikt.operators

import analytikt.base.*
import java.util.HashMap
import kotlin.jvm.Throws

/*
******************************** Binary Operator Properties *****************************
 */

/**
 * Represents an operator f such that for all a,b,c in the domain:
 * f(f(a,b),c)=f(a,f(b,c))
 */
interface Associative

/**
 * Represents an operator f such that for all a,b in the domain:
 * f(a,b)=f(b,a)
 */
interface Commutative

/**
 * Represents an operator f such that exists an element e such that for all a in the domain:
 * f(a,e)=f(e,a)=a
 */
interface HasNeutralElement<TDomain> {

    fun getNeutralElement(): TDomain

}

/**
 * Represents a binary operator with a neutral element e such that for all a exists an element a^{-1},
 * such that f(a, a^{-1}) = f(a^{-1}, a)=e
 */
interface Invertible<TDomain> : HasNeutralElement<TDomain> {

    @Throws(NoInverseException::class)
    fun findInverse(term: Term<TDomain>): Term<TDomain>

}

class NoInverseException(operator: Operator<*,*>, term: Term<*>) : ArithmeticException("Term $term has no inverse in operator $operator")


/*
************************* Collectors - Used for collecting args in associative operators **********
 */


/**
 * Helps collect an associative binary operator's arguments using polymorphism
 */
private abstract class Collector<TDomain> : Iterable<List<Term<TDomain>>> {
    /**
     * Append an expression to the matching list, according to its key
     */
    abstract fun appendToKey(key: Term<TDomain>, term: Term<TDomain>)
}

/**
 * Collects arguments for commutative operators
 */
private class CommutativeCollector<TDomain>: Collector<TDomain>() {

    val map: MutableMap<Term<TDomain>, MutableList<Term<TDomain>>> = HashMap() // use TreeMap() for sorted terms!

    override fun appendToKey(key: Term<TDomain>, term: Term<TDomain>) {
        map.getOrPut(key) { mutableListOf() }
            .add(term)
    }

    override fun iterator(): Iterator<List<Term<TDomain>>> {
        return map.values.iterator()
    }

}

/**
 * Collects arguments for non-commutative terms
 */
private class NonCommutativeCollector<TDomain>: Collector<TDomain>() {

    val keyList: MutableList<Term<TDomain>> = mutableListOf()
    val termList: MutableList<MutableList<Term<TDomain>>> = mutableListOf()

    override fun appendToKey(key: Term<TDomain>, term: Term<TDomain>) {
        if (keyList.size == 0 || keyList.last() != key) {
            keyList.add(key)
            termList.add(mutableListOf(term))
        }
        else
            termList.last().add(term)
    }

    override fun iterator(): Iterator<List<Term<TDomain>>> {
        return termList.iterator()
    }

}


/**
 * Represents a (domain-closed) Binary operators: These are operators who take exactly two operands and return a term of the same domain. This would include:
 * Addition of numbers, vectors, matrices, …
 * Multiplying numbers, matrices, …
 *
 *
 * We should be able to distinguish, and simplify accordingly, when binary operators are commutative and associative, and whether or not they have a neutral element. Some binary operators have an inverse operator which returns a term that, combined with the original one, will produce the neutral element when applied with the result.
 *
 * If almost all elements of a domain have an inverse element (like the real numbers in multiplication, which are all invertible but 0), or at least an infinite amount of them is (like square matrices of rank n in the square matrix set over multiplication), the operator should still implement the Invertible method and throw an exception if the term provided is not invertible.
 *
 */
abstract class BinaryOperator<TDomain : Any> : Operator<TDomain, TDomain>() {

    final override fun apply(args: Collection<Term<TDomain>>): Term<TDomain> {
        return apply(args, false)
    }

    private fun apply(args: Collection<Term<TDomain>>, collected: Boolean): Term<TDomain> {
        when {
            args.isEmpty() -> {
                if (this is HasNeutralElement<*>)
                    return resultDomain.parse(getNeutralElement() as TDomain)
                else throw IllegalArgumentException("Binary operator expects at least two arguments")
            }
            args.size == 1 -> {
                // If f is a binary operator with a neutral element e, we assume f(z) is f(z,e).
                // For example, +x = 0+x, *y = 1*y, etc.
                if (this is HasNeutralElement<*>)
                    return args.last()
                else throw IllegalArgumentException("Binary operator expects at least two arguments")
            }
            this !is Associative -> {
                // If the operator is not associative, we don't allow accepting more than 2 arguments
                if (args.size > 2)
                    throw IllegalArgumentException("Precedence order should be specified explicitly in non-associative operators")
                if ((args.first() is Constant<*> && args.last() is Constant<*>)
                    ||  getKey(args.first()) == getKey(args.last()))
                    return directApply(args)
                return super.apply(args)
            }
            collected -> {
                return super.apply(args)
            }
        }

        // TODO:
        // 1. Flatten list - this has to happen right now, and shouldn't happen again
        // 2. Collecting elements using the getKey() method, according commutativity rules.
        // 3. Identity element removal - this should happen after collecting elements,
        // such that a collection won't accidentally return an identity element

        val collector: Collector<TDomain> = if (this is Commutative)
            CommutativeCollector()
        else
            NonCommutativeCollector()

        for (term in args) {
            if (term !is AppliedOperatorTerm<*, *> || term.operator != this)
                collector.appendToKey(getKey(term), term)
            else // flatten list
                (term.args as Collection<Term<TDomain>>).forEach() { collector.appendToKey(getKey(it), it) }
        }

        return apply(
            collector.map { list -> directApply(list) }
                .filter { this !is HasNeutralElement<*> || it != getNeutralElement() }, // 3. Filter out neutral element
            true
            )
    }

    /**
     * Gets a term and reduces it a more basic term,
     * such that terms with the same key should produce a simplified term when applied the relation.
     *
     * Requirements:
     * - It is assumed that two constants should have the same key.
     * - It is required that if the method is invertible, a term and its inverse should produce the same key
     *
     * @sample
     * For example, in addition:
     * 2x^2 -> x^2
     * And in multiplication:
     * A^5 -> A
     * In logical or
     * neg p -> p
     */
    protected open fun getKey(term: Term<TDomain>): Term<TDomain> {
        return term
    }

    /**
     * Applies the operator for terms that have the same key.
     * @throws UnsupportedOperationException if terms provided are not of the same key
     */
    protected abstract fun directApply(terms: Collection<Term<TDomain>>): Term<TDomain>

    override fun put(
        args: Collection<Term<TDomain>>,
        sub: Term<*>,
        sourceArgs: Collection<Term<TDomain>>
    ): Term<TDomain>? {
        when {
            this is Commutative && this is Associative -> {

            }
            this is Associative -> {

            }
            this is Commutative && args.size == 2 && sourceArgs.size == 2 -> {

            }
        }
        return super.put(args, sub, sourceArgs)
    }

    /**
     * Print in infix style
     */
    override fun toString(args: Collection<Term<TDomain>>): String {
        return super.toString(args)
    }

}