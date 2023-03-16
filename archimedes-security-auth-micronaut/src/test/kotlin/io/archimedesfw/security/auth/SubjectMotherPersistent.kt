package io.archimedesfw.security.auth

import jakarta.inject.Singleton

@Singleton
internal class SubjectMotherPersistent(
    private val subjectRepository: SubjectRepository
) {

    private val subjectMother = SubjectMother()

    internal fun subject(username: String = "username", roles: Set<Role> = emptySet()): Subject =
        subjectRepository.save(subjectMother.subject(username, roles))

}
