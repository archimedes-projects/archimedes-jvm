package io.archimedesfw.data.jdbc

import java.sql.PreparedStatement

interface BatchPreparedStatementSetter {

    val batchSize: Int

    fun setValues(ps: PreparedStatement, batchIndex: Int)

}
