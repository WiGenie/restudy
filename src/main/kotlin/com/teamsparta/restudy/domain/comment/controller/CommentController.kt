package com.teamsparta.restudy.domain.comment.controller

import com.teamsparta.restudy.domain.comment.dto.CommentRequest
import com.teamsparta.restudy.domain.comment.service.CommentServiceImpl
import com.teamsparta.restudy.util.jwt.MemberPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts/{postId}")
class CommentController(
    private val commentServiceImpl: CommentServiceImpl
) {


    @PostMapping
    fun createComment(
        @PathVariable postId: Long,
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal,
        @RequestBody commentRequest: CommentRequest,
    ): ResponseEntity<String> {
        commentServiceImpl.createComment(postId, memberPrincipal, commentRequest)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("댓글이 작성되었습니다.")
    }

    @PatchMapping("/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal,
        @RequestBody commentRequest: CommentRequest,
    ): ResponseEntity<String> {
        commentServiceImpl.updateComment(postId, commentId, memberPrincipal, commentRequest)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("댓글이 수정되었습니다.")
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal,
    ): ResponseEntity<String> {
        commentServiceImpl.deleteComment(postId, commentId, memberPrincipal)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("댓글이 수정되었습니다.")
    }

}