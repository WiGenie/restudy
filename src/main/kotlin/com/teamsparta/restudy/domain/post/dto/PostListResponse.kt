package com.teamsparta.restudy.domain.post.dto

import java.time.LocalDateTime

data class PostListResponse(
    val id: Long,
    val title: String,
    val nickname: String,
    val createdAt: LocalDateTime,
)
