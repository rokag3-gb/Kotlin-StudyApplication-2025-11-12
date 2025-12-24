package com.example.study.blog.dto

import com.example.kakaoapi.core.annotation.ValidEnum
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

// DTO 만들 때에는 꼭 data class로 만들자.
data class BlogSearchDto (
    @field:NotBlank(message = "query parameter required")
    @JsonProperty("query")
    private val _query: String?,

    @field:NotBlank(message = "sort parameter required")
    @field:ValidEnum(enumClass = EnumSort::class, message = "sort parameter one of ACCURACY and RECENCY")
    @JsonProperty("sort")
    private val _sort: String?,

    @field:NotNull(message = "page parameter required")
    @field:Max(50, message = "page is more than max")
    @field:Min(1, message = "page is less than min")
    @JsonProperty("page")
    private val _page: Int?,

    @field:NotNull(message = "size parameter required")
    @field:Max(300, message = "size is more than max")
    @field:Min(1, message = "size is less than min")
    @JsonProperty("size")
    private val _size: Int?
) {
    val query: String
        get() = _query!!
    val sort: String
        get() = _sort!!
    val page: Int
        get() = _page!!
    val size: Int
        get() = _size!!

    private enum class EnumSort {
        ACCURACY,
        RECENCY
    }
}