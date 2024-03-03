import analytikt.base.*

// String domain -
class StringDomain : DomainDescriptor<String>

class Join: Operator<Collection<Term<String>>, String>(StringDomain()) {
    override val name: String
        get() = "Join"

    override fun apply(args: Collection<Term<String>>): Term<String> {
        val reducedArgs: MutableList<Term<String>> = mutableListOf()
        for (arg in args) {
            if (reducedArgs.isEmpty())
                reducedArgs.add(arg)
            if (reducedArgs.last() is Constant && arg is Constant) {
                reducedArgs[reducedArgs.size - 1] = Constant((reducedArgs.last() as Constant).value + arg.value, arg.domain)
            }
        }
        return super.apply(args)
    }

    override fun composeFrom(
        args: Collection<Term<String>>,
        targetArgs: Collection<Term<String>>
    ): Collection<Term<String>>? {
        return null
    }

}

class Reverse : Operator<Term<String>, String>(StringDomain()) {
    override val name: String
        get() = "Rev"

    override fun apply(args: Term<String>): Term<String> {
        when (args) {
            is Constant -> return Constant(args.value.reversed(), args.domain)
            is Variable -> return super.apply(args)
            is AppliedOperatorTerm<*, *> -> {
                if (args.operator is Reverse)
                    return args.args as Term<String>
                if (args.operator is Join) {
                    args.operator.let { return it.apply((args.args as Collection<Term<String>>).reversed().map { apply(it) }) }
                }
            }
        }
        return super.apply(args)
    }

    override fun composeFrom(args: Term<String>, targetArgs: Term<String>): Term<String>? {
        return null
    }
}