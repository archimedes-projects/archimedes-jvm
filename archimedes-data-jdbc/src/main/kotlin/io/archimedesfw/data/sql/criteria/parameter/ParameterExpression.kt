package io.archimedesfw.data.sql.criteria.parameter

import io.archimedesfw.data.sql.criteria.Expression

interface ParameterExpression<T> : Parameter<T>, Expression<T>
