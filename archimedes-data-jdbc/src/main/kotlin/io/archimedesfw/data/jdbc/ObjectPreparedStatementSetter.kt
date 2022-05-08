package io.archimedesfw.data.jdbc

import java.sql.PreparedStatement

class ObjectPreparedStatementSetter(
    private val objects: List<Any?>
) : PreparedStatementSetter {

    override fun setValues(ps: PreparedStatement) {
        for (i in objects.indices) {
            val arg = objects[i]
            ps.setObject(i + 1, arg)
        }
    }

}
