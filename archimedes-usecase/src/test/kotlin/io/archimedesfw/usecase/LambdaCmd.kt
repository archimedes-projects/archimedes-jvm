package io.archimedesfw.usecase

internal class LambdaCmd(val block: () -> Any) : Command<Any>() {

    override fun run(): Any = block()

}
