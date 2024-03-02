import analytikt.base.Constant
import analytikt.base.Term
import analytikt.operators.Relation
import analytikt.operators.Scale
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class GreaterThan : Relation<Int>(Int::class) {
    override val numOfArgs: Int
        get() = 2

    override fun tryApply(args: Array<Term<Int>>): Term<Boolean>? {
        val arg0 = args[0]
        val arg1 = args[1]
        if (arg0 is Constant<*> && arg1 is Constant<*>) {
            return Constant((arg0.value as Int) > (arg1.value as Int))
        }
        return null
    }

    override val name: String
        get() = ">"
}

class OperatorsTest {

    @Test
    fun scaleExtractArgs() {

    }

    @Test
    fun apply() {
    }
}