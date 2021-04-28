package io.archimedesfw.data.jdbc

import java.sql.PreparedStatement

class ArgumentPreparedStatementSetter(
    private val arguments: Array<out Any?>
) : PreparedStatementSetter {

    override fun setValues(ps: PreparedStatement) {
        for (i in arguments.indices) {
            val arg = arguments[i]
            ps.setObject(i + 1, arg)
        }
    }

}
