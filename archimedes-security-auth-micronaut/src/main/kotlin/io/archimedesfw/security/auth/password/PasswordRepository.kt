package io.archimedesfw.security.auth.password

internal interface PasswordRepository {

    fun save(subjectId: Int, password: PasswordCredential): PasswordCredential

    fun findBySubjectId(subjectId: Int): PasswordCredential?

}
