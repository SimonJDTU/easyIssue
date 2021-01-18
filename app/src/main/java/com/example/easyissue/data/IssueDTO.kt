package com.example.easyissue.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IssueDTO(
    @SerializedName("author_association")
    val authorAssociation: String,
    val body: String,
    val comments: Int,
    @SerializedName("comments_url")
    val commentsUrl: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("events_url")
    val eventsUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    val id: Int,
    @SerializedName("labels_url")
    val labelsUrl: String,
    val locked: Boolean,
    @SerializedName("node_id")
    val nodeId: String,
    val number: Int,
    @SerializedName("repository_url")
    val repositoryUrl: String,
    val state: String,
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val url: String,
    val userDTO: UserDTO
) : Parcelable