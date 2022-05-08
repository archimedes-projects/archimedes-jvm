package io.archimedesfw.data.sql.criteria

import io.archimedesfw.data.sql.criteria.Order.Direction.ASC
import io.archimedesfw.data.sql.criteria.Order.Direction.DESC

fun Selection<*>.asc(): Order = Order(this, ASC)

fun Selection<*>.desc(): Order = Order(this, DESC)
