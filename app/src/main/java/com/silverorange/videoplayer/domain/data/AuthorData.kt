package com.silverorange.videoplayer.domain.data

import com.google.gson.annotations.SerializedName

data class AuthorData (
  @SerializedName("id"   ) var id   : String? = null,
  @SerializedName("name" ) var name : String? = null
)