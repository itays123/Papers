package operators

import analytikt.base.Constant
import analytikt.base.Variable
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BinaryOpsTest {

    val x = Variable("x", Z3Domain())
    val y = Variable("y", Z3Domain())
    val t = Variable("t", Z3Domain())

    @Test
    fun defaultBinaryOp() {
        // Test a "regular" binary operator

        // Check protection
        assertThrows<IllegalArgumentException> { Z3Subtraction().apply() }
        assertThrows<IllegalArgumentException> { Z3Subtraction().apply(x) }
        assertThrows<IllegalArgumentException> { Z3Subtraction().apply(x, y, x) }

        // Check collecting
        assertEquals(Z3Subtraction().apply(x, Z3Domain.One).toString(), "x-1")
        assertEquals(Z3Subtraction().apply(x, y).toString(), "x-y")
        assertEquals(Z3Subtraction().apply(x, x), Z3Domain.Zero)
        assertEquals(Z3Subtraction().apply(Z3Domain.Two, Z3Domain.One), Z3Domain.One)

        // Substitution check
        assertEquals(Z3Subtraction().apply(x, Z3Domain.One)
            .put(t, Z3Subtraction().apply(x, Z3Domain.Two)),
            Z3Subtraction().apply(x, Z3Domain.One)) // noop
        assertEquals(Z3Subtraction().apply(x, Z3Domain.One) // substitute in args
            .put(Z3Subtraction().apply(t, Z3Domain.One), x)
            .toString(), "(t-1)-1")
        assertEquals(Z3Subtraction().apply(x, Z3Domain.One) // substitute in operator
            .put(t, Z3Subtraction().apply(x, Z3Domain.One)), t)
    }

    @Test
    fun commutativeBinaryOp() {
        // Test a non-associative, commutative operator

        // Check protection
        assertThrows<IllegalArgumentException> { Z3CommutativeImpl().apply() }
        assertThrows<IllegalArgumentException> { Z3CommutativeImpl().apply(x) }
        assertThrows<IllegalArgumentException> { Z3CommutativeImpl().apply(x, y, x) }

        // Check collecting
        assertEquals(Z3CommutativeImpl().apply(x, Z3Domain.One), Z3CommutativeImpl().apply(Z3Domain.One, x))
        assertEquals(Z3CommutativeImpl().apply(x, y), Z3CommutativeImpl().apply(y, x))
        assertEquals(Z3CommutativeImpl().apply(Z3Domain.One, Z3Domain.Two), Z3CommutativeImpl().apply(Z3Domain.Two, Z3Domain.One))

        // Substitution check
        assertEquals(Z3CommutativeImpl().apply(x, Z3Domain.One)
            .put(t, Z3CommutativeImpl().apply(x, Z3Domain.Two)),
            Z3CommutativeImpl().apply(x, Z3Domain.One)) // noop
        assertEquals(Z3CommutativeImpl().apply(x, Z3Domain.One) // substitute in args
            .put(Z3CommutativeImpl().apply(t, Z3Domain.One), x),
            Z3CommutativeImpl().apply(Z3CommutativeImpl().apply(Z3Domain.One, t), Z3Domain.One))
        assertEquals(Z3CommutativeImpl().apply(x, Z3Domain.One) // substitute in operator
            .put(t, Z3CommutativeImpl().apply(x, Z3Domain.One)), t)
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