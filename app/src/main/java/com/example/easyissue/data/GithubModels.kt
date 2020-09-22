package com.example.easyissue.data

import androidx.annotation.Keep

@Keep
data class Projects(
    val name: String,
    val hasIssues: Boolean,
    val id: Int,
    val nodeId: String,
    val openIssues: Int,
    val openIssuesCount: Int,
    val owner: Owner,
    val permissions: Permissions,
    val `private`: Boolean
)

@Keep
data class Permissions(
    val admin: Boolean,
    val pull: Boolean,
    val push: Boolean
)

@Keep
data class Owner(
    val avatarUrl: String,
    val login : String,
    val id: Int,
    val url: String
)

@Keep
data class User(
    val avatarUrl: String,
    val bio: String,
    val blog: String,
    val company: Any,
    val createdAt: String,
    val email: String,
    val eventsUrl: String,
    val followers: Int,
    val followersUrl: String,
    val following: Int,
    val followingUrl: String,
    val gistsUrl: String,
    val gravatarId: String,
    val hireable: Any,
    val htmlUrl: String,
    val id: Int,
    val location: String,
    val login: String,
    val name: String,
    val nodeId: String,
    val organizationsUrl: String,
    val publicGists: Int,
    val publicRepos: Int,
    val receivedEventsUrl: String,
    val reposUrl: String,
    val siteAdmin: Boolean,
    val starredUrl: String,
    val subscriptionsUrl: String,
    val twitterUsername: Any,
    val type: String,
    val updatedAt: String,
    val url: String
)