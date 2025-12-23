package com.example.study.blog.service

import com.example.study.blog.dto.BlogDto
import org.springframework.stereotype.Service

@Service
class BlogService {
    fun searchKakao(blogDto: BlogDto): String? {
        println(blogDto.toString())
        return "SearchKakao"
    }
}