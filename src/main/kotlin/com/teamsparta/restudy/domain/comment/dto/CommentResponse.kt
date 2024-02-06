package com.teamsparta.restudy.domain.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val nickname: String,
    val content: String,
    val createdAt: LocalDateTime,
)
