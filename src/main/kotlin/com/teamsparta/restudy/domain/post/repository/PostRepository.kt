package com.teamsparta.restudy.domain.post.repository

import com.teamsparta.restudy.domain.post.model.Post
import com.teamsparta.restudy.domain.post.model.PostStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {

    fun findAllByStatus(status: PostStatus, pageable: Pageable): Page<Post>

    fun findAllByStatus(status: PostStatus): List<Post>
}