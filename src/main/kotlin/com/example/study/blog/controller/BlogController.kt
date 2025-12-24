package com.example.study.blog.controller

import com.example.study.blog.dto.BlogSearchDto
import com.example.study.blog.service.BlogService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/blog")
@RestController
class BlogController(
    val blogService: BlogService
) {
    @GetMapping("")
    fun search(@RequestBody @Valid blogSearchDto: BlogSearchDto): String? {
        val result = blogService.searchKakao(blogSearchDto)

        return result
    }
}