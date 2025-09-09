package com.gcvd.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.time.LocalDateTime
import java.util.*

@RedisHash("guestbook")
data class GuestBook(
    val author: String,
    val content: String,
    @Id
    val id: String = UUID.randomUUID().toString(),
    val createdAt: LocalDateTime = LocalDateTime.now()
)