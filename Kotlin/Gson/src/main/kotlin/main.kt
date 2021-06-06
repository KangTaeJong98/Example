import com.google.gson.*
import java.lang.reflect.Modifier

val gson: Gson = GsonBuilder()
    .setPrettyPrinting()
    .serializeNulls()
    .excludeFieldsWithModifiers(Modifier.TRANSIENT)
    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES)
    .create()

fun main() {
    serialize()
    deserialize()
    collection()
}

fun serialize() {
    val user = User(
        LoginForm("rkdxowhd98", null),
        Profile("강태종")
    )

    gson.toJson(user).also {
        println(it)
    }
}

fun deserialize() {
    val json = "{\"loginForm\":{\"id\":\"rkdxowhd98\",\"password\":\"123123\"},\"profile\":{\"name\":null}}"

    gson.fromJson(json, User::class.java).also {
        println(it)
    }
}

fun collection() {
    val json = gson.toJson(listOf(
        User(LoginForm("rkdxowhd98@naver.com", "123123"), Profile("Naver")),
        User(LoginForm("rkdxowhd98@kakao.com", "123123"), Profile("Kakao"))
    ))

    gson.fromJson<List<User>>(json, List::class.java).also {
        println(it)
    }
}