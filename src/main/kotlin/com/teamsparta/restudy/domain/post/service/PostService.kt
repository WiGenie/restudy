package com.teamsparta.restudy.domain.post.service

import com.teamsparta.restudy.domain.post.dto.PostListResponse
import com.teamsparta.restudy.domain.post.dto.PostRequest
import com.teamsparta.restudy.domain.post.dto.PostResponse
import com.teamsparta.restudy.domain.post.model.PostStatus
import com.teamsparta.restudy.util.jwt.MemberPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostService {

    /**정렬 기준에 의한 글 목록을 페이징*/
    fun getPaginatedPostList(pageable: Pageable, postStatus: PostStatus): Page<PostListResponse>?

    /**게시글을 조회*/
    fun getPost(postId: Long): PostResponse

    /**게시글 검색*/
    fun searchPostList(title: String): List<PostListResponse>

    /**게시글 작성*/
    fun createPost(request: PostRequest, memberPrincipal: MemberPrincipal)

    /**게시글 수정*/
    fun updatePost(postId: Long, request: PostRequest, memberPrincipal: MemberPrincipal)

    /**게시글 삭제*/
    fun deletePost(postId: Long, memberPrincipal: MemberPrincipal)
}