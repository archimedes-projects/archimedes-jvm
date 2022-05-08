package io.archimedesfw.data.jdbc

import io.archimedesfw.data.sql.criteria.parameter.Parameter
import java.sql.PreparedStatement

class ParameterPreparedStatementSetter(
    private val parameters: List<Parameter<*>>
) : PreparedStatementSetter {

    override fun setValues(ps: PreparedStatement) {
        for (i in parameters.indices) {
            val parameter = parameters[i]
            parameter.set(ps, i + 1)
        }
    }

}
