package com.teamsparta.restudy.domain.comment.service

import com.teamsparta.restudy.domain.comment.dto.CommentRequest
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
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) : CommentService {

//    fun customComments(
//        postId: Long,
//    ):CommentResponse{
//
//    }
//    챌린지 - 댓글 목록 페이징 조회
//    챌린지 - 댓글 목록 조건(오름, 내림차순, n개만 출력 등)으로 페이징 조회

    @Transactional
    override fun createComment(
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
    override fun updateComment(
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
    override fun deleteComment(
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