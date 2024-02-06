package com.teamsparta.restudy.domain.post.dto

import com.teamsparta.restudy.domain.comment.dto.CommentResponse
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val nickname: String,
    val content: String,
    val createdAt: LocalDateTime,
    val comments: List<CommentResponse>
)