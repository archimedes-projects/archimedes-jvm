package io.archimedesfw.data.sql.dsl.field

class Fields {

    companion object {

        fun <T> value(value: T): Field<T> = ValueField(value)

        fun double(sql: String, alias: String): DoubleSelectField = DoubleSqlSelectField(sql, alias)
        fun <E : Enum<E>> enum(sql: String, alias: String): EnumSelectField<E> = EnumSqlSelectField(sql, alias)
        fun int(sql: String, alias: String): IntSelectField = IntSqlSelectField(sql, alias)
        fun string(sql: String, alias: String): StringSelectField = StringSqlSelectField(sql, alias)

    }

}

