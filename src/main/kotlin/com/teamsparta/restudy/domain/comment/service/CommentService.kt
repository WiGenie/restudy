package com.teamsparta.restudy.domain.comment.service

import com.teamsparta.restudy.domain.comment.dto.CommentRequest
import com.teamsparta.restudy.util.jwt.MemberPrincipal

interface CommentService {

    /**댓글 작성*/
    fun createComment(
        postId: Long,
        memberPrincipal: MemberPrincipal,
        request: CommentRequest,
    )

    /**댓글 수정*/
    fun updateComment(
        postId: Long,
        commentId: Long,
        memberPrincipal: MemberPrincipal,
        request: CommentRequest,
    )

    /**댓글 삭제*/
    fun deleteComment(
        postId: Long,
        commentId: Long,
        memberPrincipal: MemberPrincipal,
    )

}
