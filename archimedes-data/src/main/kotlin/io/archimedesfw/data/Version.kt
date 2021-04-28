package io.archimedesfw.data

import java.time.LocalDateTime

data class Version constructor(
    val start: LocalDateTime,
    val end: LocalDateTime? = null
)
