package com.teamsparta.restudy.domain.comment.service

import com.teamsparta.restudy.domain.comment.dto.CommentRequest
import com.teamsparta.restudy.domain.comment.dto.CommentResponse
import com.teamsparta.restudy.domain.comment.model.Comment
import com.teamsparta.restudy.domain.comment.model.CommentStatus
import com.teamsparta.restudy.domain.comment.repository.CommentRepository
import com.teamsparta.restudy.domain.member.repository.MemberRepository
import com.teamsparta.restudy.domain.post.repository.PostRepository
import com.teamsparta.restudy.util.exception.ModelNotFoundException
import com.teamsparta.restudy.util.jwt.MemberPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) {

//    fun customComments(
//        postId: Long,
//    ):CommentResponse{
//
//    }

    @Transactional
    fun createComment(
        postId: Long,
        memberPrincipal: MemberPrincipal,
        request: CommentRequest,
    ) {
        val post = getPostInfo(postId)
        val member = getMemberInfo(memberPrincipal.id)

        commentRepository.save(
            Comment(
                post = post,
                member = member,
                content = request.content,
                status = CommentStatus.POSTED,
            )
        )
    }

    @Transactional
    fun updateComment(
        postId: Long,
        commentId: Long,
        memberPrincipal: MemberPrincipal,
        request: CommentRequest,
    ) {
        val comment = getCommentInfo(commentId)
        val member = getMemberInfo(memberPrincipal.id)

        if (comment.member != member) {
            throw IllegalArgumentException("작성자만 수정이 가능해요.")
        } else {
            comment.content = request.content
            commentRepository.save(comment)
        }
    }

    @Transactional
    fun deleteComment(
        postId: Long,
        commentId: Long,
        memberPrincipal: MemberPrincipal,
    ) {
        val comment = getCommentInfo(commentId)
        val member = getMemberInfo(memberPrincipal.id)

        if (comment.member != member) {
            throw IllegalArgumentException("작성자만 삭제가 가능해요.")
        } else {
            comment.status = CommentStatus.DELETED
            commentRepository.save(comment)
        }
    }

    private fun getPostInfo(postId: Long) =
        postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("postId")

    private fun getMemberInfo(memberId: Long) =
        memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("memberId")

    private fun getCommentInfo(commentId: Long) =
        commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("commentId")
}