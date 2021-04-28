package io.archimedesfw.usecase

internal class LambdaQry(val block: () -> Any) : Query<Any>() {

    override fun run(): Any = block()

}
