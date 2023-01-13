package com.silverorange.videoplayer.domain.data

import com.google.gson.annotations.SerializedName

data class VideoData(
    @SerializedName("id") var id: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("hlsURL") var hlsURL: String? = null,
    @SerializedName("fullURL") var fullURL: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("publishedAt") var publishedAt: String? = null,
    @SerializedName("author") var author: AuthorData? = AuthorData()
)