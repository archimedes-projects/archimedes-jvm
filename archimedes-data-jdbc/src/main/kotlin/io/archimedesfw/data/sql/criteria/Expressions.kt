package io.archimedesfw.data.sql.criteria

import io.archimedesfw.data.sql.criteria.parameter.ArrayParameter
import io.archimedesfw.data.sql.criteria.parameter.BigDecimalParameter
import io.archimedesfw.data.sql.criteria.parameter.BooleanParameter
import io.archimedesfw.data.sql.criteria.parameter.ByteParameter
import io.archimedesfw.data.sql.criteria.parameter.DoubleParameter
import io.archimedesfw.data.sql.criteria.parameter.EnumParameter
import io.archimedesfw.data.sql.criteria.parameter.FloatParameter
import io.archimedesfw.data.sql.criteria.parameter.IntParameter
import io.archimedesfw.data.sql.criteria.parameter.LongParameter
import io.archimedesfw.data.sql.criteria.parameter.ObjectParameter
import io.archimedesfw.data.sql.criteria.parameter.ShortParameter
import io.archimedesfw.data.sql.criteria.parameter.StringParameter
import java.math.BigDecimal

class Expressions {

    companion object {

        inline fun <T> expression(sql: String, alias: String = ""): Expression<T> = SqlExpression(sql, alias)

        // Single parameters:

        inline fun parameter(value: Boolean?, sql: String = "?", alias: String = ""): BooleanParameter =
            BooleanParameter(value, sql, alias)

        inline fun parameter(value: BigDecimal?, sql: String = "?", alias: String = ""): BigDecimalParameter =
            BigDecimalParameter(value, sql, alias)

        inline fun parameter(value: Byte?, sql: String = "?", alias: String = ""): ByteParameter =
            ByteParameter(value, sql, alias)

        inline fun parameter(value: Double?, sql: String = "?", alias: String = ""): DoubleParameter =
            DoubleParameter(value, sql, alias)

        inline fun <E : Enum<E>> parameter(value: E?, sql: String = "?", alias: String = ""): EnumParameter<E> =
            EnumParameter(value, sql, alias)

        inline fun parameter(value: Float?, sql: String = "?", alias: String = ""): FloatParameter =
            FloatParameter(value, sql, alias)

        inline fun parameter(value: Int?, sql: String = "?", alias: String = ""): IntParameter =
            IntParameter(value, sql, alias)

        inline fun parameter(value: Long?, sql: String = "?", alias: String = ""): LongParameter =
            LongParameter(value, sql, alias)

        inline fun parameter(value: Short?, sql: String = "?", alias: String = ""): ShortParameter =
            ShortParameter(value, sql, alias)

        inline fun parameter(value: String?, sql: String = "?", alias: String = ""): StringParameter =
            StringParameter(value, sql, alias)

        inline fun <T> parameterAny(value: T?, sql: String = "?", alias: String = ""): ObjectParameter<T> =
            ObjectParameter(value, sql, alias)

        // Array parameters:

        @JvmName("parameterBigDecimals")
        inline fun parameter(values: List<BigDecimal>): ArrayParameter<BigDecimal> =
            ArrayParameter(values.map { BigDecimalParameter(it) })

        @JvmName("parameterDoubles")
        inline fun parameter(values: List<Double>): ArrayParameter<Double> =
            ArrayParameter(values.map { DoubleParameter(it) })

        @JvmName("parameterEnums")
        inline fun <E : Enum<E>> parameter(values: List<E>): ArrayParameter<E> =
            ArrayParameter(values.map { EnumParameter(it) })

        @JvmName("parameterFloats")
        inline fun parameter(values: List<Float>): ArrayParameter<Float> =
            ArrayParameter(values.map { FloatParameter(it) })

        @JvmName("parameterInts")
        inline fun parameter(values: List<Int>): ArrayParameter<Int> = ArrayParameter(values.map { IntParameter(it) })

        @JvmName("parameterLongs")
        inline fun parameter(values: List<Long>): ArrayParameter<Long> =
            ArrayParameter(values.map { LongParameter(it) })

        @JvmName("parameterShorts")
        inline fun parameter(values: List<Short>): ArrayParameter<Short> =
            ArrayParameter(values.map { ShortParameter(it) })

        @JvmName("parameterStrings")
        inline fun parameter(values: List<String>): ArrayParameter<String> =
            ArrayParameter(values.map { StringParameter(it) })

        inline fun <T> parameterArray(values: List<T>): ArrayParameter<T> =
            ArrayParameter(values.map { ObjectParameter(it) })

    }

}
