package io.archimedesfw.data.sql.dsl.field

class Fields {

    companion object {

        fun <T> value(value: T): Field<T> = ValueField(value)

        fun int(sql: String, alias: String): IntSelectField = IntSqlSelectField(sql, alias)
        fun double(sql: String, alias: String): DoubleSelectField = DoubleSqlSelectField(sql, alias)
        fun string(sql: String, alias: String): StringSelectField = StringSqlSelectField(sql, alias)

    }

}

