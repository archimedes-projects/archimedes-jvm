package io.archimedesfw.security.auth.password

internal interface PasswordEncoder {

    /**
     *
     * @param rawPassword password in clear text
     * @return encoded password
     */
    fun encode(rawPassword: String): String

    /**
     *
     * @return true if [rawPassword] matches with [encodedPassword] for this password encoder, false in other case
     */
    fun matches(rawPassword: String, encodedPassword: String): Boolean

}
