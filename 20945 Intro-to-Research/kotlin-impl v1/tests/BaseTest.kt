import analytikt.base.AppliedOperatorTerm
import analytikt.base.Constant
import analytikt.base.Term
import analytikt.base.Variable
import analytikt.base.Operator
import com.sun.org.apache.xpath.internal.operations.Neg
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

class Negate : Operator<Boolean, Boolean>(
    Boolean::class,
    Boolean::class
) {
    override val name: String
        get() = "!"

    override fun tryApply(args: Collection<Term<Boolean>>): Term<Boolean> {
        // Get and extract 1 argument
        if (args.size != 1)
            throw IllegalArgumentException("Negate accepts 1 argument only")
        val arg = args.toTypedArray()[0]

        // constants
        if (arg is Constant<*>) {
            return Constant(!(arg.value as Boolean))
        }

        // apply identity neg neg x = x
        if (arg is AppliedOperatorTerm<*, *> && arg.operator == this) {
            return arg.args.toTypedArray()[0] as Term<Boolean>
        }

        return super.apply(args)
    }
}

class BaseTest {

    @Test
    fun variable() {
        val x = Variable("x", Boolean::class)
        assertNotEquals(x, Constant(true))
        assertNotEquals(x, Constant(false))
        assertEquals(x, Variable("x", Boolean::class))
        assertNotEquals(x, Variable("x", String::class))
    }

    @Test
    fun operator() {
        val x = Variable("x", Boolean::class)
        val y = Negate().apply(x)
        assertEquals(x.domain, y.domain)
        assertThrows<IllegalArgumentException> { Negate().apply(x, Constant(false)) }
        assertThrows<IllegalArgumentException> { Negate().apply() }
    }

    @Test
    fun put() {
        val x = Variable("x", Boolean::class)
        val t = Variable("x", Boolean::class)
        val y = Negate().apply(x)
        assertEquals(y.put(Negate().apply(t), x), t)
        assertEquals(y.put(Constant(false), x), true)
        assertEquals(y.put(Negate().apply(x), x), x)
    }
}
