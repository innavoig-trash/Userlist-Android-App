package com.example.userlist.data.models

import com.google.gson.annotations.SerializedName

data class ReqresAllUserResponse(

	@field:SerializedName("per_page")
	val perPage: Int? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem>? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null
)