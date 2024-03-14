package analytikt.operators

import analytikt.base.*
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

    val neutralElement: TDomain

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

/*
************************ Putters - Used for placing args in associative operators **********
 */


/**
 * Helps collect an associative binary operator's arguments using polymorphism
 */
private abstract class Putter<TDomain, out TColl: MutableCollection<Term<TDomain>>> {

    abstract val terms: TColl

    /**
     * Replace (once) occurrences of targetTerms with an occurrence of sub
     * @throws UnsupportedOperationException didn't manage to replace
     */
    @Throws(UnsupportedOperationException::class)
    abstract fun replace(targetTerms: Collection<Term<TDomain>>, sub: Term<TDomain>)
}

/**
 * Substitutes (once) arguments for commutative operators
 */
private class CommutativePutter<TDomain>: Putter<TDomain, MutableSet<Term<TDomain>>>() {

    override val terms: MutableSet<Term<TDomain>>
        get() = HashSet() // use TreeSet() for sorted terms!

    override fun replace(targetTerms: Collection<Term<TDomain>>, sub: Term<TDomain>) {
        targetTerms.forEach { if (!terms.remove(it)) throw UnsupportedOperationException("Could not replace") }
        terms.add(sub)
    }

}

/**
 * Substitutes (once) terms for non-commutative operators
 */
private class NonCommutativePutter<TDomain>: Putter<TDomain, MutableList<Term<TDomain>>>() {

    override val terms: MutableList<Term<TDomain>>
        get() = mutableListOf()

    override fun replace(targetTerms: Collection<Term<TDomain>>, sub: Term<TDomain>) {
        val startIndex = terms.windowed(targetTerms.size).indexOfFirst { it == targetTerms }
        if (startIndex == -1)
            throw UnsupportedOperationException("Replacement unsuccessful")
        terms[startIndex] = sub
        for (i in 1..<terms.size)
            terms.removeAt(startIndex + i)
    }

}

/**
 * Represents a (domain-closed) Binary operators: These are operators who take exactly two operands and return a term of the same domain. This would include:
 * Addition of numbers, vectors, matrices, …
 * Multiplying numbers, matrices, …
 * Logical and, or, xor
 * Set union, intersection, difference
 * Cartesian product of domains and sets
 *
 * We should be able to distinguish, and simplify accordingly, when binary operators are commutative and associative, and whether they have a neutral element. Some binary operators have an inverse operator which returns a term that, combined with the original one, will produce the neutral element when applied with the result.
 *
 * If almost all elements of a domain have an inverse element (like the real numbers in multiplication, which are all invertible but 0), or at least an infinite amount of them is (like square matrices of rank n in the square matrix set over multiplication), the operator should still implement the Invertible method and throw an exception if the term provided is not invertible.
 *
 */
abstract class BinaryOperator<TDomain : Any> : Operator<TDomain, TDomain>() {

    override fun apply(args: Collection<Term<TDomain>>): Term<TDomain> {
        /*
        * Applying values to the binary operator involves many phases:
        * 1. Protection
        * 2. List flattening
        * 3. Lazy evaluation
        * 4. Element collection
        * 5. Identity element removal
        *
        * 1. Protection.
        * Ensure the right amount of arguments is given.
        * If the operator has a neutral element, we accept less than 2 elements and append the neutral element to the rest.
        * If the operator is associative, we accept more than 2 elements.
        */
        if (this !is HasNeutralElement<*> && args.size < 2)
            throw IllegalArgumentException("Binary operator expects at least two arguments")
        if (this !is Associative && args.size > 2)
            throw IllegalArgumentException("Precedence order should be specified explicitly in non-associative operators")

        /*
        * 2. List flattening.
        * If associative, flatten argument list. p and (p and q) is actually p and p and q.
        */
        val flattenedArgList: Collection<Term<TDomain>>
        if (this is Associative) {
            flattenedArgList = mutableListOf()
            appendArgs(flattenedArgList, args)
        }
        else
            flattenedArgList = args
        /*
        * 3. Lazy evaluation.
        * If an associative operator *has* a specific argument in its argument list,
         */
        val lazyEval = tryLazyEvaluate(flattenedArgList)
        if (lazyEval != null)
            return lazyEval

        /*
        * 4. Element collection.
        * Separate arguments to sets, each set is a set where applying the operator directly at it would produce a single element.
        * 5. Filter out neutral element (during collection)
         */
        val collector: Collector<TDomain> = if (this is Commutative)
            CommutativeCollector()
        else
            NonCommutativeCollector()
        flattenedArgList.forEach { collector.appendToKey(getKey(it), it) }

        val newArgs = collector
                .map { list -> if (list.size == 1) list.first() else directApply(list) }
                .filter { this !is HasNeutralElement<*> || it != neutralElement } // 5. Filter out neutral element

        return when {
            newArgs.isEmpty() && this is HasNeutralElement<*> -> resultDomain.parse(neutralElement as TDomain)
            newArgs.size == 1 -> newArgs.last()
            else -> super.apply(newArgs)
        }
    }

    /**
     * Appends term to argument list recursively
     */
    private fun appendArgs(flattenedArgsList: MutableList<Term<TDomain>>, args: Collection<Term<TDomain>>) {
        args.forEach {term ->
            if (term !is AppliedOperatorTerm<*, *> || term.operator != this)
                flattenedArgsList.add(term)
            else
                appendArgs(flattenedArgsList, term.args as Collection<Term<TDomain>>)
        }
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
        if (term is Constant)
            return term.domain.constantInstance
        return term
    }

    /**
     * Applies the operator for terms that have the same key.
     * @throws UnsupportedOperationException if terms provided are not of the same key
     */
    protected abstract fun directApply(terms: Collection<Term<TDomain>>): Term<TDomain>


    /**
     * Substitute args of a binary operator.
     *
     * This handles simple collections, where the substitution should only be applied ONCE:
     * (p and q and r)[t/p and q] -> t and r
     *
     * It can't tell when the substitution is more complex, as in:
     * (x^2-2x+8)[t/x-1] -> t^2+7
     * (4x)[t/2x] -> 2t
     * (x^4)[t/x^2] -> t^2
     * (e^(2x))[t/e^x] -> t^2
     */
    open override fun put(
        args: Collection<Term<TDomain>>,
        sub: Term<*>,
        sourceArgs: Collection<Term<TDomain>>
    ): Term<TDomain>? {
        if (this !is Associative)
            return super.put(args, sub, sourceArgs)

        try {
            val putter: Putter<TDomain, MutableCollection<Term<TDomain>>> = if (this is Commutative)
                CommutativePutter()
            else
                NonCommutativePutter()

            putter.terms.addAll(args)
            putter.replace(sourceArgs, resultDomain.parse(sub))
            return apply(putter.terms)
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * Print in infix style
     */
    override fun toString(args: Collection<Term<TDomain>>): String {
        return args.fold("") {
            acc, term ->
                if (acc == "")
                    stringifyTerm(term)
                else
                    "$acc$name${stringifyTerm(term)}"
        }
    }

}