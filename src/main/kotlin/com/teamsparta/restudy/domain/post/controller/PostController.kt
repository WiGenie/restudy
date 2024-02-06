package com.teamsparta.restudy.domain.post.controller

import com.teamsparta.restudy.domain.post.dto.PostListResponse
import com.teamsparta.restudy.domain.post.dto.PostRequest
import com.teamsparta.restudy.domain.post.dto.PostResponse
import com.teamsparta.restudy.domain.post.service.PostService
import com.teamsparta.restudy.util.jwt.MemberPrincipal
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {

    @GetMapping
    fun getPostList(): ResponseEntity<Page<PostListResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostList())
    }

    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable postId: Long,
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPost(postId))
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    fun createPost(
        @RequestBody postRequest: PostRequest,
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal,
    ): ResponseEntity<String> {
        postService.createPost(postRequest, memberPrincipal)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("게시글이 작성되었습니다.")
    }

    @PatchMapping("/{postId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody postRequest: PostRequest,
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal,
    ): ResponseEntity<String> {
        postService.updatePost(postId, postRequest, memberPrincipal)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("게시글이 수정되었습니다.")
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    fun deletePost(
        @PathVariable postId: Long,
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal,
    ): ResponseEntity<String> {
        postService.deletePost(postId, memberPrincipal)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("게시글이 삭제되었습니다.")
    }

}