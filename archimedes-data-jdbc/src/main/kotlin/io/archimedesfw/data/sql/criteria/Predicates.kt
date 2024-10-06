package io.archimedesfw.data.sql.criteria

import io.archimedesfw.data.sql.criteria.BinaryOperator.Companion.EQ
import io.archimedesfw.data.sql.criteria.BinaryOperator.Companion.GE
import io.archimedesfw.data.sql.criteria.BinaryOperator.Companion.GT
import io.archimedesfw.data.sql.criteria.BinaryOperator.Companion.IN
import io.archimedesfw.data.sql.criteria.BinaryOperator.Companion.LE
import io.archimedesfw.data.sql.criteria.BinaryOperator.Companion.LIKE
import io.archimedesfw.data.sql.criteria.BinaryOperator.Companion.LT
import io.archimedesfw.data.sql.criteria.BinaryOperator.Companion.NE
import io.archimedesfw.data.sql.criteria.Expressions.Companion.parameter
import io.archimedesfw.data.sql.criteria.Expressions.Companion.parameterAny
import io.archimedesfw.data.sql.criteria.Expressions.Companion.parameterArray
import io.archimedesfw.data.sql.criteria.UnaryOperator.Companion.IS_NOT_NULL
import io.archimedesfw.data.sql.criteria.UnaryOperator.Companion.IS_NULL
import java.math.BigDecimal

class Predicates {

    companion object {

        fun predicate(sql: String): Predicate = SqlPredicate(sql)

        fun <T> predicate(left: Expression<T>, operator: BinaryOperator, right: Expression<T>): Predicate =
            BinaryPredicate(left, operator, right)

        fun <T> predicate(x: Expression<T>, operator: UnaryOperator): Predicate =
            UnaryPredicate(x, operator)

        fun and(vararg predicate: Predicate): Predicate = and(predicate.asList())
        fun and(predicates: List<Predicate>): Predicate = AndPredicate(predicates)

        fun or(vararg predicate: Predicate): Predicate = or(predicate.asList())
        fun or(predicates: List<Predicate>): Predicate = OrPredicate(predicates)

        // Equality:

        inline fun <T> eq(x: Expression<T>, y: Expression<T>): Predicate = predicate(x, EQ, y)
        inline fun <T> eq(x: Expression<T>, y: T): Predicate = eq(x, parameterAny(y))

        inline fun eq(x: Expression<Boolean>, y: Boolean): Predicate = eq(x, parameter(y))
        inline fun eq(x: Expression<BigDecimal>, y: BigDecimal): Predicate = eq(x, parameter(y))
        inline fun eq(x: Expression<Byte>, y: Byte): Predicate = eq(x, parameter(y))
        inline fun eq(x: Expression<Double>, y: Double): Predicate = eq(x, parameter(y))
        inline fun <E : Enum<E>> eq(x: Expression<E>, y: E): Predicate = eq(x, parameter(y))
        inline fun eq(x: Expression<Float>, y: Float): Predicate = eq(x, parameter(y))
        inline fun eq(x: Expression<Int>, y: Int): Predicate = eq(x, parameter(y))
        inline fun eq(x: Expression<Long>, y: Long): Predicate = eq(x, parameter(y))
        inline fun eq(x: Expression<Short>, y: Short): Predicate = eq(x, parameter(y))
        inline fun eq(x: Expression<String>, y: String): Predicate = eq(x, parameter(y))

        inline fun <T> ne(x: Expression<T>, y: Expression<T>): Predicate = predicate(x, NE, y)
        inline fun <T> ne(x: Expression<T>, y: T): Predicate = ne(x, parameterAny(y))

        inline fun ne(x: Expression<Boolean>, y: Boolean): Predicate = ne(x, parameter(y))
        inline fun ne(x: Expression<BigDecimal>, y: BigDecimal): Predicate = ne(x, parameter(y))
        inline fun ne(x: Expression<Byte>, y: Byte): Predicate = ne(x, parameter(y))
        inline fun ne(x: Expression<Double>, y: Double): Predicate = ne(x, parameter(y))
        inline fun <E : Enum<E>> ne(x: Expression<E>, y: E): Predicate = ne(x, parameter(y))
        inline fun ne(x: Expression<Float>, y: Float): Predicate = ne(x, parameter(y))
        inline fun ne(x: Expression<Int>, y: Int): Predicate = ne(x, parameter(y))
        inline fun ne(x: Expression<Long>, y: Long): Predicate = ne(x, parameter(y))
        inline fun ne(x: Expression<Short>, y: Short): Predicate = ne(x, parameter(y))
        inline fun ne(x: Expression<String>, y: String): Predicate = ne(x, parameter(y))

        inline fun <T> isNull(x: Expression<T>): Predicate = predicate(x, IS_NULL)
        inline fun <T> isNotNull(x: Expression<T>): Predicate = predicate(x, IS_NOT_NULL)

        // Comparisons for generic operands:

        inline fun <T : Comparable<T>> lt(x: Expression<T>, y: Expression<T>): Predicate = predicate(x, LT, y)
        inline fun <T : Comparable<T>> lt(x: Expression<T>, y: T): Predicate = lt(x, parameterAny(y))

        inline fun lt(x: Expression<Boolean>, y: Boolean): Predicate = lt(x, parameter(y))
        inline fun lt(x: Expression<BigDecimal>, y: BigDecimal): Predicate = lt(x, parameter(y))
        inline fun lt(x: Expression<Byte>, y: Byte): Predicate = lt(x, parameter(y))
        inline fun lt(x: Expression<Double>, y: Double): Predicate = lt(x, parameter(y))
        inline fun lt(x: Expression<Float>, y: Float): Predicate = lt(x, parameter(y))
        inline fun lt(x: Expression<Int>, y: Int): Predicate = lt(x, parameter(y))
        inline fun lt(x: Expression<Long>, y: Long): Predicate = lt(x, parameter(y))
        inline fun lt(x: Expression<Short>, y: Short): Predicate = lt(x, parameter(y))
        inline fun lt(x: Expression<String>, y: String): Predicate = lt(x, parameter(y))


        inline fun <T : Comparable<T>> le(x: Expression<T>, y: Expression<T>): Predicate = predicate(x, LE, y)
        inline fun <T : Comparable<T>> le(x: Expression<T>, y: T): Predicate = le(x, parameterAny(y))

        inline fun le(x: Expression<Boolean>, y: Boolean): Predicate = le(x, parameter(y))
        inline fun le(x: Expression<BigDecimal>, y: BigDecimal): Predicate = le(x, parameter(y))
        inline fun le(x: Expression<Byte>, y: Byte): Predicate = le(x, parameter(y))
        inline fun le(x: Expression<Double>, y: Double): Predicate = le(x, parameter(y))
        inline fun le(x: Expression<Float>, y: Float): Predicate = le(x, parameter(y))
        inline fun le(x: Expression<Int>, y: Int): Predicate = le(x, parameter(y))
        inline fun le(x: Expression<Long>, y: Long): Predicate = le(x, parameter(y))
        inline fun le(x: Expression<Short>, y: Short): Predicate = le(x, parameter(y))
        inline fun le(x: Expression<String>, y: String): Predicate = le(x, parameter(y))

        inline fun <T : Comparable<T>> gt(x: Expression<T>, y: Expression<T>): Predicate = predicate(x, GT, y)
        inline fun <T : Comparable<T>> gt(x: Expression<T>, y: T): Predicate = gt(x, parameterAny(y))

        inline fun gt(x: Expression<Boolean>, y: Boolean): Predicate = gt(x, parameter(y))
        inline fun gt(x: Expression<BigDecimal>, y: BigDecimal): Predicate = gt(x, parameter(y))
        inline fun gt(x: Expression<Byte>, y: Byte): Predicate = gt(x, parameter(y))
        inline fun gt(x: Expression<Double>, y: Double): Predicate = gt(x, parameter(y))
        inline fun gt(x: Expression<Float>, y: Float): Predicate = gt(x, parameter(y))
        inline fun gt(x: Expression<Int>, y: Int): Predicate = gt(x, parameter(y))
        inline fun gt(x: Expression<Long>, y: Long): Predicate = gt(x, parameter(y))
        inline fun gt(x: Expression<Short>, y: Short): Predicate = gt(x, parameter(y))
        inline fun gt(x: Expression<String>, y: String): Predicate = gt(x, parameter(y))

        inline fun <T : Comparable<T>> ge(x: Expression<T>, y: Expression<T>): Predicate = predicate(x, GE, y)
        inline fun <T : Comparable<T>> ge(x: Expression<T>, y: T): Predicate = ge(x, parameterAny(y))

        inline fun ge(x: Expression<Boolean>, y: Boolean): Predicate = ge(x, parameter(y))
        inline fun ge(x: Expression<BigDecimal>, y: BigDecimal): Predicate = ge(x, parameter(y))
        inline fun ge(x: Expression<Byte>, y: Byte): Predicate = ge(x, parameter(y))
        inline fun ge(x: Expression<Double>, y: Double): Predicate = ge(x, parameter(y))
        inline fun ge(x: Expression<Float>, y: Float): Predicate = ge(x, parameter(y))
        inline fun ge(x: Expression<Int>, y: Int): Predicate = ge(x, parameter(y))
        inline fun ge(x: Expression<Long>, y: Long): Predicate = ge(x, parameter(y))
        inline fun ge(x: Expression<Short>, y: Short): Predicate = ge(x, parameter(y))
        inline fun ge(x: Expression<String>, y: String): Predicate = ge(x, parameter(y))

        // String functions:

        inline fun <T : CharSequence> like(x: Expression<T>, y: Expression<T>): Predicate = predicate(x, LIKE, y)
        inline fun <T : CharSequence> like(x: Expression<T>, y: T): Predicate = like(x, parameterAny(y))

        inline fun like(x: Expression<String>, y: String): Predicate = like(x, parameter(y))

        // In array functions:

        inline fun <T> `in`(x: Expression<T>, values: List<T>): Predicate =
            predicate(x, IN, parameterArray(values))

        @JvmName("insideBigDecimals")
        inline fun `in`(x: Expression<BigDecimal>, values: List<BigDecimal>): Predicate =
            predicate(x, IN, parameter(values))

        @JvmName("insideDoubles")
        inline fun `in`(x: Expression<Double>, values: List<Double>): Predicate =
            predicate(x, IN, parameter(values))

        @JvmName("insideEnums")
        inline fun <E : Enum<E>> `in`(x: Expression<E>, values: List<E>): Predicate =
            predicate(x, IN, parameter(values))

        @JvmName("insideFloats")
        inline fun `in`(x: Expression<Float>, values: List<Float>): Predicate =
            predicate(x, IN, parameter(values))

        @JvmName("insideInts")
        inline fun `in`(x: Expression<Int>, values: List<Int>): Predicate =
            predicate(x, IN, parameter(values))

        @JvmName("insideLongs")
        inline fun `in`(x: Expression<Long>, values: List<Long>): Predicate =
            predicate(x, IN, parameter(values))

        @JvmName("insideShorts")
        inline fun `in`(x: Expression<Short>, values: List<Short>): Predicate =
            predicate(x, IN, parameter(values))

        @JvmName("insideStrings")
        inline fun `in`(x: Expression<String>, values: List<String>): Predicate =
            predicate(x, IN, parameter(values))

    }

}
