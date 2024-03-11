package operators

import analytikt.base.Constant
import analytikt.base.Variable
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BinaryOpsTest {

    val x = Variable("x", IntDomain())
    val y = Variable("y", IntDomain())

    @Test
    fun nonAssocNonCommOp() {
        assertThrows<IllegalArgumentException> { BasicSubtraction().apply() }
        assertThrows<IllegalArgumentException> { BasicSubtraction().apply(x) }
        assertThrows<IllegalArgumentException> { BasicSubtraction().apply(x, y, x) }
        assertEquals(BasicSubtraction().apply(x, Constant(1, IntDomain())).toString(), "x-1")
        assertEquals(BasicSubtraction().apply(x, y).toString(), "x-y")
        assertEquals(BasicSubtraction().apply(x, x), Constant(0, IntDomain()))
        assertEquals(BasicSubtraction().apply(Constant(8, IntDomain()), Constant(7, IntDomain())), Constant(1, IntDomain()))
    }

    @Test
    fun nonAssocCommOp() {
        assertThrows<IllegalArgumentException> { Distance().apply() }
        assertThrows<IllegalArgumentException> { Distance().apply(x) }
        assertThrows<IllegalArgumentException> { Distance().apply(x, y, x) }
        assertEquals(Distance().apply(x, Constant(1, IntDomain())), Distance().apply(Constant(1, IntDomain()), x))
        assertEquals(Distance().apply(x, y), Distance().apply(y, x))
        assertEquals(Distance().apply(x, x), Constant(0, IntDomain()))
        assertEquals(Distance().apply(Constant(8, IntDomain()), Constant(7, IntDomain())), Distance().apply(Constant(7, IntDomain()), Constant(8, IntDomain())))
    }
}