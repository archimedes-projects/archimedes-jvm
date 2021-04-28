package io.archimedesfw.usecase.security

import io.archimedesfw.security.Security
import io.archimedesfw.usecase.Command
import io.archimedesfw.usecase.Query
import io.archimedesfw.usecase.UseCase
import javax.inject.Singleton

@Singleton
internal class SecurityRegistry() {

    private val conditions: Map<Class<out UseCase<*>>, List<String>> = mapOf(
        Command::class.java to listOf("aw"),
        Query::class.java to listOf("ar")
    )

//    fun add(useCaseClass: Class<out UseCase>, permissions: List<String>) {
//        val previousPermissions = conditions.put(useCaseClass, permissions)
//        if (previousPermissions != null) {
//            throw RuntimeException(
//                "Cannot assign twice the permissions for use case: $useCaseClass, " +
//                        "previous permissions: $previousPermissions, " +
//                        "actual permissions: $permissions"
//            )
//        }
//    }

    fun check(useCase: UseCase<*>) {
        val permissions = getConditions(useCase)
        for (p in permissions) {
            if (!Security.has(p)) {
                throw RuntimeException("user has not permission")
            }
        }
    }

    private fun getConditions(useCase: UseCase<*>): List<String> {
        return conditions[useCase.javaClass]
            ?: throw IllegalStateException("Security is not configured for use case ${useCase::class.qualifiedName}")
    }

}
