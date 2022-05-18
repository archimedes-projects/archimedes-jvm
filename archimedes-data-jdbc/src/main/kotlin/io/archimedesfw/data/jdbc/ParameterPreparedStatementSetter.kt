package io.archimedesfw.data.jdbc

import io.archimedesfw.data.sql.criteria.parameter.Parameter
import java.sql.PreparedStatement

data class ParameterPreparedStatementSetter(
    private val parameters: List<Parameter<*>>,
    private val initialIndex: Int = 1
) : PreparedStatementSetter {

    override fun setValues(ps: PreparedStatement) {
        var index = initialIndex
        parameters.forEach {
            it.set(ps, index)
            index++
        }
    }

}
