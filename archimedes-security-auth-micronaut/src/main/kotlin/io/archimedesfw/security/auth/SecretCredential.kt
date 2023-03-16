package io.archimedesfw.security.auth

interface Credential

interface SecretCredential: Credential {

    val secret: String

}
