package com.example.easyissue.data

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ProjectSearchModels(
    val name: String,
    val hasIssues: Boolean,
    val id: Int,
    val nodeId: String,
    val openIssues: Int,
    val openIssuesCount: Int,
    @Json( name = "owner" )
    val owner: Owner,
    @Json( name = "permissions" )
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
    @Json( name = "avatar_url" )
    val avatarUrl: String,
    @Json( name = "login" )
    val login : String,
    val id: Int,
    val url: String
)

@Keep
data class User(
    @Json( name = "avatar_url")
    val avatarUrl: String,
    val bio: String,
    val blog: String,
    val company: Any,
    @Json( name = "created_at")
    val createdAt: String,
    val email: String,
    @Json( name = "events_url")
    val eventsUrl: String,
    val followers: Int,
    @Json( name = "followers_url")
    val followersUrl: String,
    val following: Int,
    @Json( name = "following_url")
    val followingUrl: String,
    @Json( name = "gists_url")
    val gistsUrl: String,
    @Json( name = "gravatar_id")
    val gravatarId: String,
    val hireable: Any,
    @Json( name = "html_url")
    val htmlUrl: String,
    val id: Int,
    val location: String,
    val login: String,
    val name: String,
    @Json( name = "node_id")
    val nodeId: String,
    @Json( name = "organizations_url")
    val organizationsUrl: String,
    @Json( name = "public_gists")
    val publicGists: Int,
    @Json( name = "public_repos")
    val publicRepos: Int,
    @Json( name = "received_events_url")
    val receivedEventsUrl: String,
    @Json( name = "repos_url")
    val reposUrl: String,
    @Json( name = "site_admin")
    val siteAdmin: Boolean,
    @Json( name = "starred_url")
    val starredUrl: String,
    @Json( name = "subscriptions_url")
    val subscriptionsUrl: String,
    @Json( name = "twitter_username")
    val twitterUsername: Any,
    val type: String,
    @Json( name = "updated_at")
    val updatedAt: String,
    val url: String
)