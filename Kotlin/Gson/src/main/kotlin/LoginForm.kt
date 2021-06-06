import com.google.gson.annotations.SerializedName

data class LoginForm(
    @SerializedName(value = "id", alternate = ["clientId", "userId"])
    val id: String? = null,
    @SerializedName(value = "password", alternate = ["clientPassword", "userPassword"])
    val password: String? = null
)