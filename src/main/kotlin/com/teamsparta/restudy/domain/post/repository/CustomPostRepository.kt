package com.teamsparta.restudy.domain.post.repository

import com.teamsparta.restudy.domain.post.dto.PostListResponse
import com.teamsparta.restudy.domain.post.model.Post
import com.teamsparta.restudy.domain.post.model.PostStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomPostRepository {

    fun searchPostListByTitle(title: String): List<Post>

    fun findByPageableAndStatus(pageable: Pageable, postStatus: PostStatus): Page<PostListResponse>

}