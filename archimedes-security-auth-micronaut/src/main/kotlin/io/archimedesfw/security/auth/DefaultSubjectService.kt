package io.archimedesfw.security.auth

import io.archimedesfw.security.auth.Subject.Companion.NEW
import jakarta.inject.Singleton
import java.security.Principal

@Singleton
internal class DefaultSubjectService(
    private val subjectRepository: SubjectRepository,
) : SubjectService {

    override fun create(
        principal: Principal,
        roles: Set<Role>,
        attributes: Map<String, Any>
    ): Int {
        val subject = Subject(
            NEW,
            principal,
            roles,
            attributes
        )
        val savedSubject = subjectRepository.save(subject)
        return savedSubject.id
    }



    override fun find(principal: Principal): Subject? =
        subjectRepository.findByPrincipal(principal.name)

}
