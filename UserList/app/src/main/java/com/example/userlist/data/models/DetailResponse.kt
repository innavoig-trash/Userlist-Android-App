package com.example.userlist.data.models

import com.google.gson.annotations.SerializedName

data class DetailResponse(
	@field:SerializedName("data")
	val data: DataItem? = null
)