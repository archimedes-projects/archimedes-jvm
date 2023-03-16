package io.archimedesfw.security.auth

internal interface SubjectRepository {

    fun save(subject: Subject): Subject

    fun findById(id: Int): Subject?

    fun findByPrincipal(name: String): Subject?

}
