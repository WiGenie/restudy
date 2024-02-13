package com.teamsparta.restudy.domain.post.repository

import com.teamsparta.restudy.domain.post.model.Post
import com.teamsparta.restudy.domain.post.model.PostStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface PostRepository : JpaRepository<Post, Long>, CustomPostRepository {

    fun findAllByStatus(status: PostStatus, pageable: Pageable): Page<Post>

    fun findAllByStatus(status: PostStatus): List<Post>

    fun findByCreatedAtBeforeAndStatus(dateTime: LocalDateTime, status: PostStatus)
}