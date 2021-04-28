package io.archimedesfw.security

interface SecurityContextHolder {
    fun get(): SecurityContext
    fun set(securityContext: SecurityContext)
}
