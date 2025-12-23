package com.example.study.blog.dto

// DTO 만들 때에는 꼭 data class로 만들자.
data class BlogDto (
    val query: String,
    val sort: String,
    val page: Int,
    val size: Int,
)