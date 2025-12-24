package com.example.study.blog.service

import com.example.study.blog.dto.BlogSearchDto
import com.example.study.blog.entity.Wordcount
import com.example.study.blog.repository.WorkRepository
import com.example.study.core.exception.InvalidInputException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class BlogService (
    val wordRepository: WorkRepository
) {
    @Value("\${REST_API_KEY}")
    lateinit var restApiKey: String

    fun searchKakao(blogSearchDto: BlogSearchDto): String? {
        // BlogSearchDto에 validation 추가하면서 필요없어진 코드
        /*
        val msgList = mutableListOf<ExceptionMsg>()

        if (blogSearchDto.query.trim().isEmpty()) {
            msgList.add(ExceptionMsg.EMPTY_QUERY)
        }

        if (blogSearchDto.sort.trim() !in arrayOf("accuracy", "recency")) {
            msgList.add(ExceptionMsg.NOT_IN_SORT)
        }

        when {
            blogSearchDto.page < 1 -> msgList.add(ExceptionMsg.LESS_THAN_MIN)
            blogSearchDto.page > 50 -> msgList.add(ExceptionMsg.MORE_THAN_MAX)
        }

        // [응용] Size가 1 미만이거나 300 초과인지 체크
        if (blogSearchDto.size < 1)
            msgList.add(ExceptionMsg.SIZE_LESS_THAN_MIN)
        if (blogSearchDto.size > 300)
            msgList.add(ExceptionMsg.SIZE_MORE_THAN_MAX)

        if (msgList.isNotEmpty()) {
            val message = msgList.joinToString { it.msg }
            throw InvalidInputException(message)
        }
        */

        val webClient = WebClient
            .builder()
            .baseUrl("https://dapi.kakao.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

        val response = webClient
            .get()
            .uri { it.path("/v2/search/blog")
                .queryParam("query", blogSearchDto.query)
                .queryParam("sort", blogSearchDto.sort)
                .queryParam("page", blogSearchDto.page)
                .queryParam("size", blogSearchDto.size)
                .build()
            }
            .header("Authorization", "KakaoAK $restApiKey")
            .retrieve()
            .bodyToMono<String>()

        val result = response.block()

        val lowQuery: String = blogSearchDto.query.lowercase()
        val word: Wordcount = wordRepository.findById(lowQuery).orElse(Wordcount(lowQuery))
        word.cnt++

        wordRepository.save(word)

        return result
    }

    fun searchWordRank(): List<Wordcount> {
        return wordRepository.findTop10ByOrderByCntDesc()
    }
}

// BlogSearchDto에 validation 추가하면서 필요없어진 코드
//private enum class ExceptionMsg(val msg: String) {
//    EMPTY_QUERY("query parameter required"),
//    NOT_IN_SORT("sort parameter one of accuracy and recency"),
//    LESS_THAN_MIN("page is less than min"),
//    MORE_THAN_MAX("page is more than max"),
//    SIZE_LESS_THAN_MIN("size is less than min"),
//    SIZE_MORE_THAN_MAX("size is more than max"),
//}