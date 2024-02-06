package com.teamsparta.restudy.domain.comment.repository

import com.teamsparta.restudy.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {

    fun findAllByPostId(postId: Long): List<Comment>
}