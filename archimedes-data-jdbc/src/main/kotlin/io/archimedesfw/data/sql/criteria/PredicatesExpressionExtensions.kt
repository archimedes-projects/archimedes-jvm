package io.archimedesfw.data.sql.criteria

import java.math.BigDecimal

// Equality:

inline fun <T> Expression<T>.eq(y: Expression<T>): Predicate = Predicates.eq(this, y)
inline fun <T> Expression<T>.eq(y: T): Predicate = Predicates.eq(this, y)

inline fun Expression<Boolean>.eq(y: Boolean): Predicate = Predicates.eq(this, y)
inline fun Expression<BigDecimal>.eq(y: BigDecimal): Predicate = Predicates.eq(this, y)
inline fun Expression<Byte>.eq(y: Byte): Predicate = Predicates.eq(this, y)
inline fun Expression<Double>.eq(y: Double): Predicate = Predicates.eq(this, y)
inline fun <E : Enum<E>> Expression<E>.eq(y: E): Predicate = Predicates.eq(this, y)
inline fun Expression<Float>.eq(y: Float): Predicate = Predicates.eq(this, y)
inline fun Expression<Int>.eq(y: Int): Predicate = Predicates.eq(this, y)
inline fun Expression<Long>.eq(y: Long): Predicate = Predicates.eq(this, y)
inline fun Expression<Short>.eq(y: Short): Predicate = Predicates.eq(this, y)
inline fun Expression<String>.eq(y: String): Predicate = Predicates.eq(this, y)

inline fun <T> Expression<T>.ne(y: Expression<T>): Predicate = Predicates.ne(this, y)
inline fun <T> Expression<T>.ne(y: T): Predicate = Predicates.ne(this, y)

inline fun Expression<Boolean>.ne(y: Boolean): Predicate = Predicates.ne(this, y)
inline fun Expression<BigDecimal>.ne(y: BigDecimal): Predicate = Predicates.ne(this, y)
inline fun Expression<Byte>.ne(y: Byte): Predicate = Predicates.ne(this, y)
inline fun Expression<Double>.ne(y: Double): Predicate = Predicates.ne(this, y)
inline fun <E : Enum<E>> Expression<E>.ne(y: E): Predicate = Predicates.ne(this, y)
inline fun Expression<Float>.ne(y: Float): Predicate = Predicates.ne(this, y)
inline fun Expression<Int>.ne(y: Int): Predicate = Predicates.ne(this, y)
inline fun Expression<Long>.ne(y: Long): Predicate = Predicates.ne(this, y)
inline fun Expression<Short>.ne(y: Short): Predicate = Predicates.ne(this, y)
inline fun Expression<String>.ne(y: String): Predicate = Predicates.ne(this, y)

// Comparisons for generic operands:

inline fun <T : Comparable<T>> Expression<T>.lt(y: Expression<T>): Predicate = Predicates.lt(this, y)
inline fun <T : Comparable<T>> Expression<T>.lt(y: T): Predicate = Predicates.lt(this, y)

inline fun Expression<Boolean>.lt(y: Boolean): Predicate = Predicates.lt(this, y)
inline fun Expression<BigDecimal>.lt(y: BigDecimal): Predicate = Predicates.lt(this, y)
inline fun Expression<Byte>.lt(y: Byte): Predicate = Predicates.lt(this, y)
inline fun Expression<Double>.lt(y: Double): Predicate = Predicates.lt(this, y)
inline fun Expression<Float>.lt(y: Float): Predicate = Predicates.lt(this, y)
inline fun Expression<Int>.lt(y: Int): Predicate = Predicates.lt(this, y)
inline fun Expression<Long>.lt(y: Long): Predicate = Predicates.lt(this, y)
inline fun Expression<Short>.lt(y: Short): Predicate = Predicates.lt(this, y)
inline fun Expression<String>.lt(y: String): Predicate = Predicates.lt(this, y)

inline fun <T : Comparable<T>> Expression<T>.le(y: Expression<T>): Predicate = Predicates.le(this, y)
inline fun <T : Comparable<T>> Expression<T>.le(y: T): Predicate = Predicates.le(this, y)

inline fun Expression<Boolean>.le(y: Boolean): Predicate = Predicates.le(this, y)
inline fun Expression<BigDecimal>.le(y: BigDecimal): Predicate = Predicates.le(this, y)
inline fun Expression<Byte>.le(y: Byte): Predicate = Predicates.le(this, y)
inline fun Expression<Double>.le(y: Double): Predicate = Predicates.le(this, y)
inline fun Expression<Float>.le(y: Float): Predicate = Predicates.le(this, y)
inline fun Expression<Int>.le(y: Int): Predicate = Predicates.le(this, y)
inline fun Expression<Long>.le(y: Long): Predicate = Predicates.le(this, y)
inline fun Expression<Short>.le(y: Short): Predicate = Predicates.le(this, y)
inline fun Expression<String>.le(y: String): Predicate = Predicates.le(this, y)

inline fun <T : Comparable<T>> Expression<T>.gt(y: Expression<T>): Predicate = Predicates.gt(this, y)
inline fun <T : Comparable<T>> Expression<T>.gt(y: T): Predicate = Predicates.gt(this, y)

inline fun Expression<Boolean>.gt(y: Boolean): Predicate = Predicates.gt(this, y)
inline fun Expression<BigDecimal>.gt(y: BigDecimal): Predicate = Predicates.gt(this, y)
inline fun Expression<Byte>.gt(y: Byte): Predicate = Predicates.gt(this, y)
inline fun Expression<Double>.gt(y: Double): Predicate = Predicates.gt(this, y)
inline fun Expression<Float>.gt(y: Float): Predicate = Predicates.gt(this, y)
inline fun Expression<Int>.gt(y: Int): Predicate = Predicates.gt(this, y)
inline fun Expression<Long>.gt(y: Long): Predicate = Predicates.gt(this, y)
inline fun Expression<Short>.gt(y: Short): Predicate = Predicates.gt(this, y)
inline fun Expression<String>.gt(y: String): Predicate = Predicates.gt(this, y)

inline fun <T : Comparable<T>> Expression<T>.ge(y: Expression<T>): Predicate = Predicates.ge(this, y)
inline fun <T : Comparable<T>> Expression<T>.ge(y: T): Predicate = Predicates.ge(this, y)

inline fun Expression<Boolean>.ge(y: Boolean): Predicate = Predicates.ge(this, y)
inline fun Expression<BigDecimal>.ge(y: BigDecimal): Predicate = Predicates.ge(this, y)
inline fun Expression<Byte>.ge(y: Byte): Predicate = Predicates.ge(this, y)
inline fun Expression<Double>.ge(y: Double): Predicate = Predicates.ge(this, y)
inline fun Expression<Float>.ge(y: Float): Predicate = Predicates.ge(this, y)
inline fun Expression<Int>.ge(y: Int): Predicate = Predicates.ge(this, y)
inline fun Expression<Long>.ge(y: Long): Predicate = Predicates.ge(this, y)
inline fun Expression<Short>.ge(y: Short): Predicate = Predicates.ge(this, y)
inline fun Expression<String>.ge(y: String): Predicate = Predicates.ge(this, y)

// String functions:

inline fun <T : CharSequence> Expression<T>.like(y: Expression<T>): Predicate = Predicates.like(this, y)
inline fun <T : CharSequence> Expression<T>.like(y: T): Predicate = Predicates.like(this, y)

inline fun Expression<String>.like(y: String): Predicate = Predicates.like(this, y)

// In functions:

inline fun <T> Expression<T>.`in`(values: List<T>): Predicate =
    Predicates.`in`(this, values)

@JvmName("insideBigDecimals")
inline fun Expression<BigDecimal>.`in`(values: List<BigDecimal>): Predicate = Predicates.`in`(this, values)

@JvmName("insideDoubles")
inline fun Expression<Double>.`in`(values: List<Double>): Predicate = Predicates.`in`(this, values)

@JvmName("insideEnums")
inline fun <E : Enum<E>> Expression<E>.`in`(values: List<E>): Predicate = Predicates.`in`(this, values)

@JvmName("insideFloats")
inline fun Expression<Float>.`in`(values: List<Float>): Predicate = Predicates.`in`(this, values)

@JvmName("insideInts")
inline fun Expression<Int>.`in`(values: List<Int>): Predicate = Predicates.`in`(this, values)

@JvmName("insideLongs")
inline fun Expression<Long>.`in`(values: List<Long>): Predicate = Predicates.`in`(this, values)

@JvmName("insideShorts")
inline fun Expression<Short>.`in`(values: List<Short>): Predicate = Predicates.`in`(this, values)

@JvmName("insideStrings")
inline fun Expression<String>.`in`(values: List<String>): Predicate = Predicates.`in`(this, values)
