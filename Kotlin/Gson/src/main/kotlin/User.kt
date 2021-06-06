
data class User(
    val loginForm: LoginForm? = null,
    val profile: Profile? = null
) {
    @Transient
    private val secret: String = "Secret"

    private val justForNamingPolicy = "policy"
}