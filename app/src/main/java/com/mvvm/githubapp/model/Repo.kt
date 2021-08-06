package com.mvvm.githubapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.mvvm.githubapp.utils.CommonUtils
import java.io.Serializable
import java.util.*

@Entity
@JsonClass(generateAdapter = true)
data class Repo (
    @field:Json(name = "id") @PrimaryKey val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "full_name") val fullName: String,
    @field:Json(name = "html_url") val htmlUrl: String? = null,
    @field:Json(name = "description") val description: String? = null,
    @field:Json(name = "language") val language: String? = null,
    @field:Json(name = "created_at") val createdAt: String? = null,
    @field:Json(name = "updated_at") val updatedAt: String? = null,
    @field:Json(name = "owner") val owner: User? = null,
    @field:Json(name = "stargazers_count") val stargazersCount: Int = 0,
    @field:Json(name = "watchers_count") val watchersCount: Int = 0,
    @field:Json(name = "forks_count") val forksCount: Int = 0,
    @field:Json(name = "open_issues_count") val openIssuesCount: Int = 0,
): Serializable {
    val forks : String
        get() {
            return forksCount.toString()
        }

    val watchers: String
        get() {
            return watchersCount.toString()
        }

    val issues: String
        get() {
            return openIssuesCount.toString()
        }

    val createdDate: String
        get() {
            return CommonUtils.getNewsTimeStr(Date(CommonUtils.convertDateToLong(createdAt)))
        }

    val updatedDate: String
        get() {
            return CommonUtils.getNewsTimeStr(Date(CommonUtils.convertDateToLong(updatedAt)))
        }
}
