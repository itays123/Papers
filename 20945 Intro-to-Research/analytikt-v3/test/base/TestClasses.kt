package base

import analytikt.base.*

// String domain -
class StringDomain : DomainDescriptor<String>

class Join() : Operator<String, String>() {
    override val resultDomain: DomainDescriptor<String>
        get() = StringDomain()
    override val name: String
        get() = "Join"

    override fun apply(args: Collection<Term<String>>): Term<String> {
        val reducedArgs: MutableList<Term<String>> = mutableListOf()
        for (arg in args) {
            if (reducedArgs.isNotEmpty() && reducedArgs.last() is Constant && arg is Constant) {
                reducedArgs[reducedArgs.size - 1] = Constant((reducedArgs.last() as Constant).value + arg.value, arg.domain)
            }
            else
                reducedArgs.add(arg)
        }
        if (reducedArgs.size == 1)
            return reducedArgs.last()
        return super.apply(reducedArgs)
    }

}

class Reverse : Operator<String, String>() {
    override val resultDomain: DomainDescriptor<String>
        get() = StringDomain()
    override val name: String
        get() = "Rev"

    override fun apply(args: Collection<Term<String>>): Term<String> {
        if (args.size != 1)
            throw IllegalArgumentException("Expected argument list with length of 1")
        when (val arg = args.last()) {
            is Constant -> return Constant(arg.value.reversed(), arg.domain)
            is AppliedOperatorTerm<*, *> -> {
                if (arg.operator is Reverse)
                    return arg.args.last() as Term<String>
                if (arg.operator is Join) {
                    return Join().apply((arg.args as Collection<Term<String>>).reversed().map { apply(it) })
                }
            }
        }
        return super.apply(args)
    }

}