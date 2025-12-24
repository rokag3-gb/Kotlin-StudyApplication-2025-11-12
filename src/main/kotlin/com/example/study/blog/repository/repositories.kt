package com.example.study.blog.repository

import com.example.study.blog.entity.Wordcount
import org.springframework.data.repository.CrudRepository

interface WorkRepository : CrudRepository<Wordcount, String> {
    fun findTop10ByOrderByCntDesc(): List<Wordcount>
}