package io.archimedesfw.data.tx.micronaut

import io.micronaut.core.annotation.Introspected

@Introspected
internal data class TableDto(
    val id: Int, val field: Int
)
