package io.archimedesfw.security.auth.persistence.jdbc

import io.micronaut.core.type.Argument
import io.micronaut.json.JsonMapper
import jakarta.inject.Singleton

@Singleton
internal class MapToJsonConverter(
    private val jsonMapper: JsonMapper
) {

    internal fun toJson(map: Map<String, Any>): String =
        if (map.isEmpty()) ""
        else jsonMapper.writeValueAsString(map)

    internal fun toMap(json: String): Map<String, Any> =
        if (json.isBlank()) emptyMap()
        else jsonMapper.readValue(json, Argument.mapOf(String::class.java, Any::class.java))

}
