package edu.kaist.cs.teamfinder
import com.google.gson.annotations.SerializedName

data class User(
    val email: String?,
    val password: String?,
    val fullname: String?,
    val photo_url: String?,
)

data class Engineer(
    val name: String,
    val imageResourceId: Int
)


data class Project(
    @SerializedName("p_name")
    val projectName: String,

    @SerializedName("p_description")
    val projectDescription: String,

    @SerializedName("p_require")
    val projectRequire: String,

    @SerializedName("p_type")
    val projectType: String,

    @SerializedName("fullname")
    val fullname: String,

    @SerializedName("front")
    val front: String,

    @SerializedName("back")
    val back: String,

    @SerializedName("max")
    val max: Int,

    @SerializedName("current")
    val current: Int,
)

data class ProjectResponse(
    @SerializedName("p_id")
    val projectId: Int,

    @SerializedName("p_name")
    val projectName: String,

    @SerializedName("p_description")
    val projectDescription: String,

    @SerializedName("p_require")
    val projectRequire: String,

    @SerializedName("p_type")
    val projectType: String,

    @SerializedName("fullname")
    val fullname: String,

    @SerializedName("front")
    val front: String,

    @SerializedName("back")
    val back: String,

    @SerializedName("max")
    val max: Int,

    @SerializedName("current")
    val current: Int,
)

data class Feed(
    @SerializedName("posting_id")
    val postId: Int,

    @SerializedName("author")
    val name: String,

    @SerializedName("posting_title")
    val title: String,

    val imageResourceId: Int,

    @SerializedName("posting_content")
    val content: String,

    val date: String,

    @SerializedName("posting_like")
    val like: Int,

    @SerializedName("posting_comment")
    val comment: Int,

    @SerializedName("posting_shared")
    val shared: Int
)

data class UserResponse(
    val results: List<User>
)