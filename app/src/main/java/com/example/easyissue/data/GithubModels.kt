package com.example.easyissue.data

import androidx.annotation.Keep

@Keep
data class Project(
    val name: String,
    val hasIssues: Boolean,
    val id: Int,
    val nodeId: String,
    val openIssues: Int,
    val openIssuesCount: Int,
    val owner: Owner,
    val permissions: Permissions,
    val `private`: Boolean,
    val language: String?,
    val updatedAt: String
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