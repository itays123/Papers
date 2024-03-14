package operators

import analytikt.base.Constant
import analytikt.base.Variable
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BinaryOpsTest {

    val x = Variable("x", IntDomain())
    val y = Variable("y", IntDomain())
    val t = Variable("t", IntDomain())

    @Test
    fun defaultBinaryOp() {
        // Test a "regular" binary operator

        // Check protection
        assertThrows<IllegalArgumentException> { BasicSubtraction().apply() }
        assertThrows<IllegalArgumentException> { BasicSubtraction().apply(x) }
        assertThrows<IllegalArgumentException> { BasicSubtraction().apply(x, y, x) }

        // Check collecting
        assertEquals(BasicSubtraction().apply(x, Constant(1, IntDomain())).toString(), "x-1")
        assertEquals(BasicSubtraction().apply(x, y).toString(), "x-y")
        assertEquals(BasicSubtraction().apply(x, x), Constant(0, IntDomain()))
        assertEquals(BasicSubtraction().apply(Constant(8, IntDomain()), Constant(7, IntDomain())), Constant(1, IntDomain()))

        // Substitution check
        assertEquals(BasicSubtraction().apply(x, Constant(1, IntDomain()))
            .put(t, BasicSubtraction().apply(x, IntDomain().parse(2))),
            BasicSubtraction().apply(x, Constant(1, IntDomain()))) // noop
        assertEquals(BasicSubtraction().apply(x, Constant(1, IntDomain())) // substitute in args
            .put(BasicSubtraction().apply(t, IntDomain().parse(1)), x)
            .toString(), "(t-1)-1")
        assertEquals(BasicSubtraction().apply(x, Constant(1, IntDomain())) // substitute in operator
            .put(t, BasicSubtraction().apply(x, IntDomain().parse(1))), t)
    }

    @Test
    fun commutativeBinaryOp() {
        // Test a non-associative, commutative operator

        // Check protection
        assertThrows<IllegalArgumentException> { Distance().apply() }
        assertThrows<IllegalArgumentException> { Distance().apply(x) }
        assertThrows<IllegalArgumentException> { Distance().apply(x, y, x) }

        // Check collecting
        assertEquals(Distance().apply(x, Constant(1, IntDomain())), Distance().apply(Constant(1, IntDomain()), x))
        assertEquals(Distance().apply(x, y), Distance().apply(y, x))
        assertEquals(Distance().apply(x, x), Constant(0, IntDomain()))
        assertEquals(Distance().apply(Constant(8, IntDomain()), Constant(7, IntDomain())), Distance().apply(Constant(7, IntDomain()), Constant(8, IntDomain())))

        // Substitution check
        assertEquals(Distance().apply(x, Constant(1, IntDomain()))
            .put(t, Distance().apply(x, IntDomain().parse(2))),
            Distance().apply(x, Constant(1, IntDomain()))) // noop
        assertEquals(Distance().apply(x, Constant(1, IntDomain())) // substitute in args
            .put(Distance().apply(t, IntDomain().parse(1)), x),
            Distance().apply(Distance().apply(IntDomain().parse(1), t), IntDomain().parse(1)))
        assertEquals(Distance().apply(x, Constant(1, IntDomain())) // substitute in operator
            .put(t, Distance().apply(x, IntDomain().parse(1))), t)
    }

    @Test
    fun neutralElementBinaryOp() {
        // Test a non-associative, non-commutative, neutral element operator

        // Check protection
        assertEquals(NeutralElemOpImpl().apply(), Z3Domain.Zero)
        assertEquals(NeutralElemOpImpl().apply(x), x)
        assertThrows<IllegalArgumentException> { NeutralElemOpImpl().apply(x, y, x) }

        // Check Neutral element removal
        assertEquals(NeutralElemOpImpl().apply(x, Z3Domain.Zero), x)
        assertEquals(NeutralElemOpImpl().apply(Z3Domain.Zero, x), x)
        assertEquals(NeutralElemOpImpl().apply(Z3Domain.Zero, Z3Domain.One), Z3Domain.One)
        assertEquals(NeutralElemOpImpl().apply(Z3Domain.One, Z3Domain.Zero), Z3Domain.One)

        // Check collection
        assertEquals(NeutralElemOpImpl().apply(x, Z3Domain.One).toString(), "x*1")
        assertEquals(NeutralElemOpImpl().apply(x, y).toString(), "x*y")
    }

    @Test
    fun associativeBinaryOp() {
        // Test associative operators
        val x = Variable("x", Z3Domain())
        val y = Variable("y", Z3Domain())
        val t = Variable("t", Z3Domain())

        // Check protection
        assertThrows<IllegalArgumentException> { AssociativeOpImpl().apply() }
        assertThrows<IllegalArgumentException> { AssociativeOpImpl().apply(x) }
        assertEquals(AssociativeOpImpl().apply(x, y, x).toString(), "x*y*x")

        // Flattening check
        assertEquals(AssociativeOpImpl().apply(x, Z3Domain.One, AssociativeOpImpl().apply(Z3Domain.Two, y)).toString(), "x*2*y")
        assertEquals(AssociativeOpImpl().apply(Z3Domain.One, AssociativeOpImpl().apply(Z3Domain.One, AssociativeOpImpl().apply(Z3Domain.One, Z3Domain.Two))), Z3Domain.Two)

        // Lazy evaluation checks
        assertEquals(AssociativeOpImpl().apply(x, y, x, Z3Domain.Zero, x, y, Z3Domain.One), Z3Domain.Zero) // lazy eval table
        assertEquals(AssociativeOpImpl().apply(x, y, y, Z3Domain.Two, x, x, x, Z3Domain.Two, Z3Domain.One, y, x), Z3Domain.Zero) // lazy eval after collection

        // Check collection
        val term = AssociativeOpImpl().apply(x, y, y, Z3Domain.Two, x, x, x, Z3Domain.One, Z3Domain.Two, y, x)
        assertEquals(term.toString(), "x*(y*y)*2*(x*x*x)*2*y*x")
        assertEquals(term.put(t, AssociativeOpImpl().apply(x, y)).toString(), "t*y*2*(x*x*x)*2*y*x")
    }


}