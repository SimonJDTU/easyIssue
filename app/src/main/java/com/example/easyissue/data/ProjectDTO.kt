package com.example.easyissue.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
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
    @SerializedName("updated_at")
    val updatedAt: String
) : Parcelable

@Keep
@Parcelize
data class Permissions(
    val admin: Boolean,
    val pull: Boolean,
    val push: Boolean
)  : Parcelable

@Keep
@Parcelize
data class Owner(
    val avatarUrl: String,
    val login : String,
    val id: Int,
    val url: String
)  : Parcelable