package base

import analytikt.base.Constant
import analytikt.base.Variable
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BaseTest {

    val x = Variable("x", StringDomain())
    val hello = Constant("hello", StringDomain())
    @Test
    fun variable() {
        assertEquals(x.toString(), x.name)
        assertEquals(x.put("Constant", x), "Constant")
    }

    @Test
    fun constant() {
        assertEquals(hello.toString(), "hello")
        assertEquals(hello.put("Constant", hello), "hello")
    }

    @Test
    fun unaryOp() {
        val revX = Reverse().apply(x)
        assertEquals(revX.toString(), "Rev x")
        assertEquals(revX.put(hello, x), hello.value.reversed())
        assertEquals(revX.put(hello, Reverse().apply(x)), "hello")
    }

    @Test
    fun naryOp() {
        assertEquals(Join().apply(listOf(hello, hello)), "hellohello")
        assertEquals(Join().apply(listOf(x, hello)).toString(), "Join(x,hello)")
        assertEquals(Join().apply(listOf(hello, hello, x, hello)).toString(), "Join(hellohello,x,hello)")
        assertEquals(Join().apply(listOf(hello, x, hello)).put("world", x), "helloworldhello")
    }

    @Test
    fun complexOp() {
        val joined = Join().apply(x, hello, hello, Reverse().apply(x), hello)
        assertEquals(joined.toString(), "Join(x,hellohello,Rev x,hello)")
        assertEquals(Reverse().apply(joined).toString(), "Join(olleh,x,olleholleh,Rev x)")
        assertEquals(joined.put(Reverse().apply(x), x).toString(), "Join(Rev x,hellohello,x,hello)")
    }
}